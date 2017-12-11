package utility;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.nicol.joynus.LoginActivity;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
    public static int basicGetMethodSetupAndDataFetching(String urlToQuery, Object modelObject)
    {
        try
        {
            int responseCode = 0;
            HttpURLConnection connection = HttpMethodSetups.basicGetMethodSetup(urlToQuery);
            if(connection == null)
            {
                return 0;
            }
            connection.connect();

            /*if(!ResponseCodeChecker.checkWhetherTaskSucceeded(responseCode))
            {
                connection.disconnect();
                return responseCode;
            }*/
            String jsonResponseString = JsonParser.jsonStringFromConnection(connection);
            responseCode = connection.getResponseCode();
            connection.disconnect();
            if(jsonResponseString == null)
            {
                return 0;
            }
             modelObject = JsonParser.getJavaObjectFromJsonString(jsonResponseString, modelObject);
            if(modelObject == null)
            {
                return 0;
            }
             return responseCode;
        }
        catch(Exception e)
        {
            return 0;
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
    public static int postMethodSetupAndPosting(String urlToQuery,String jsonToPost,Object modelObject,boolean hasDataToBeRetrieved)
    {
        try
        {
            Class classOfObject = modelObject.getClass();
            HttpURLConnection connection = basicPostMethodSetup(urlToQuery,hasDataToBeRetrieved);
            if (connection == null)
            {
                return 0;
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
            }
            connection.disconnect();
            return responseCode;
        }
        catch(Exception e)
        {
            return 0;
        }
    }
}
