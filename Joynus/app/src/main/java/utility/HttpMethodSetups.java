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
    public static HttpURLConnection basicGetMethodSetup(String urlToQuery) throws Exception
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
    public static HttpReturnPackage basicGetMethodSetupAndDataFetching(String urlToQuery, Object modelObject, boolean isResponseAnArray)
    {
        HttpReturnPackage packageToReturn = new HttpReturnPackage();
        try
        {
            HttpURLConnection connection = HttpMethodSetups.basicGetMethodSetup(urlToQuery);
            connection.connect();
            String jsonResponseString = JsonParser.jsonStringFromConnection(connection);
            packageToReturn.setRequestResponseCode(connection.getResponseCode());
            connection.disconnect();
            Object objectResult;
            objectResult = JsonParser.getJavaObjectFromJsonString(jsonResponseString, modelObject,isResponseAnArray);
            packageToReturn.setObjectResult(objectResult);
            return packageToReturn;
        }
        catch(Exception e)
        {
            packageToReturn.setRequestResponseCode(0);
            return packageToReturn;
        }
    }
    public static HttpURLConnection basicPostMethodSetup(String urlToQuery, boolean hasDataToBeRetrieved, boolean requiresAuthorization) throws Exception
    {
            URL url = new URL(urlToQuery);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            if(requiresAuthorization)
            {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.getContextOfApplication());
                String token = preferences.getString("token","");
                connection.setRequestProperty("Authorization", "Bearer " +token);
            }
            connection.setDoOutput(true);
            connection.setDoInput(hasDataToBeRetrieved);
            return connection;
    }
    public static HttpURLConnection basicPutMethodSetup(String urlToQuery, boolean hasDataToBeRetrieved, boolean requiresAuthorization) throws Exception
    {
        URL url = new URL(urlToQuery);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-type", "application/json");
        if(requiresAuthorization)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.getContextOfApplication());
            String token = preferences.getString("token","");
            connection.setRequestProperty("Authorization", "Bearer " +token);
        }
        connection.setDoOutput(true);
        connection.setDoInput(hasDataToBeRetrieved);
        return connection;
    }
    public static HttpReturnPackage postOrPutMethodSetupAndPosting(String urlToQuery,Object objectToSend, Object resultModelObject,boolean requiresAuthorization,boolean isPost)
    {
        HttpReturnPackage packageToReturn = new HttpReturnPackage();
        try
        {
            boolean hasDataToBeRetrieved = (resultModelObject != null);
            String jsonToPost = JsonParser.objectToJson(objectToSend);
            HttpURLConnection connection;
            if(isPost)
            {
                connection = basicPostMethodSetup(urlToQuery,hasDataToBeRetrieved,requiresAuthorization);
            }
            else
            {
                connection = basicPutMethodSetup(urlToQuery,hasDataToBeRetrieved,requiresAuthorization);
            }
            connection.connect();
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
                Class classOfObject = resultModelObject.getClass();
                Object result;
                result = JsonParser.getJavaObjectFromJsonString(jsonResponseString,resultModelObject,false);
                packageToReturn.setObjectResult(result);
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
