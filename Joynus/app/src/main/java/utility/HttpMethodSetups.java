package utility;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.nicol.joynus.LoginActivity;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import taskmodels.HttpReturnPackage;

public class HttpMethodSetups
{
    public static HttpURLConnection basicGetMethodSetup(String urlToQuery)
    {
        try
        {
            URL url = new URL(urlToQuery);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.getContextOfApplication());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            String token = preferences.getString("token","");
            connection.setRequestProperty("Authorization", "Bearer " +token);
            connection.setDoInput(true);
            return connection;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public static HttpReturnPackage basicGetMethodSetupAndDataFetching(String urlToQuery, Object modelObject)
    {
        HttpReturnPackage packageToReturn = new HttpReturnPackage();
        try
        {
            packageToReturn.setRequestResponseCode(0);
            HttpURLConnection connection = HttpMethodSetups.basicGetMethodSetup(urlToQuery);
            if(connection == null)
            {
                return packageToReturn;
            }
            connection.connect();
            String jsonResponseString = JsonParser.jsonStringFromConnection(connection);
            packageToReturn.setRequestResponseCode(connection.getResponseCode());
            connection.disconnect();
            if(jsonResponseString == null)
            {
                packageToReturn.setRequestResponseCode(0);
                return packageToReturn;
            }
            Object objectResult;
            objectResult = JsonParser.getJavaObjectFromJsonString(jsonResponseString, modelObject);
            if(objectResult == null)
            {
                packageToReturn.setRequestResponseCode(0);
                return packageToReturn;
            }
            packageToReturn.setObjectResult(objectResult);
            return packageToReturn;
        }
        catch(Exception e)
        {
            packageToReturn.setRequestResponseCode(0);
            return packageToReturn;
        }
    }
    public static HttpURLConnection basicPostMethodSetup(String urlToQuery, boolean hasDataToBeRetrieved)
    {
        try
        {
            URL url = new URL(urlToQuery);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(hasDataToBeRetrieved);
            connection.connect();
            return connection;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public static HttpReturnPackage postMethodSetupAndPosting(String urlToQuery,String jsonToPost,Object modelObject,boolean hasDataToBeRetrieved)
    {
        HttpReturnPackage packageToReturn = new HttpReturnPackage();
        try
        {
            Class classOfObject = modelObject.getClass();
            HttpURLConnection connection = basicPostMethodSetup(urlToQuery,hasDataToBeRetrieved);
            if (connection == null)
            {
                packageToReturn.setRequestResponseCode(0);
                return packageToReturn;
            }
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(jsonToPost);
            writer.flush();
            int responseCode = connection.getResponseCode();
            writer.close();
            outputStream.close();
            if(hasDataToBeRetrieved && ResponseCodeChecker.checkWhetherTaskSucceeded(responseCode))
            {
                String jsonResponseString = JsonParser.jsonStringFromConnection(connection);
                modelObject = JsonParser.getJavaObjectFromJsonString(jsonResponseString,classOfObject);
                if(modelObject == null)
                {
                    packageToReturn.setRequestResponseCode(0);
                    return packageToReturn;
                }
                packageToReturn.setObjectResult(modelObject);
            }
            connection.disconnect();
            packageToReturn.setRequestResponseCode(responseCode);
            return packageToReturn;
        }
        catch(Exception e)
        {
            packageToReturn.setRequestResponseCode(0);
            return packageToReturn;
        }
    }
}
