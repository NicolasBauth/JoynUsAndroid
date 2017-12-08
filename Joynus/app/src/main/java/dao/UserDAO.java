package dao;


import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dtomodels.userDTO.TokenPackage;
import dtomodels.userDTO.UserCredentialsDTO;
import dtomodels.userDTO.UserProfileDTO;
import model.AuthenticatedUser;
import taskmodels.AuthenticateUserPackage;
import utility.ConnectionStringsManager;
import utility.JsonParser;
import utility.ResponseCodeChecker;

public class UserDAO
{
    private ConnectionStringsManager manager;
    public UserDAO()
    {
        manager = new ConnectionStringsManager();
    }
    public AuthenticatedUser authenticateUser(AuthenticateUserPackage packageToFill)
    {
        new AuthenticateUser().execute(packageToFill);
        return null;
    }
    private class AuthenticateUser extends AsyncTask<AuthenticateUserPackage,Void,AuthenticateUserPackage>
    {
        private Integer responseCode;

        protected void onPreExecute()
        {
            responseCode = 0;
        }
        protected AuthenticateUserPackage doInBackground(AuthenticateUserPackage... authenticateUserPackage)
        {

            try
            {

                URL url = new URL(manager.getTokenConnectionString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
                connection.setDoOutput(true);
                //setDoOutput allows to send a request body
                connection.setDoInput(true);
                OutputStream outputStream = connection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                connection.connect();
                String urlEncodedStringToSend ="grant_type=password&";
                urlEncodedStringToSend +="username=";
                urlEncodedStringToSend +=authenticateUserPackage[0].getUserCredentials().getUsername();
                urlEncodedStringToSend +="&password=";
                urlEncodedStringToSend +=authenticateUserPackage[0].getUserCredentials().getPassword();
                writer.write(urlEncodedStringToSend);
                writer.flush();
                responseCode = connection.getResponseCode();
                writer.close();
                outputStream.close();
                authenticateUserPackage[0].setResponseCode(responseCode);
                if(!ResponseCodeChecker.checkWhetherTaskSucceeded(responseCode))
                {
                    connection.disconnect();
                    return authenticateUserPackage[0];
                }
                String jsonResponseString = JsonParser.jsonStringFromConnection(connection);
                connection.disconnect();
                TokenPackage tokenPackageResult = new TokenPackage();
                tokenPackageResult = (TokenPackage) JsonParser.getJavaObjectFromJsonString(jsonResponseString,tokenPackageResult);
                authenticateUserPackage[0].getUserResponse().setAccessToken(tokenPackageResult.getAccess_token());
                authenticateUserPackage[0].getUserResponse().setUsername(tokenPackageResult.getUserName());
                Log.i("UserDAOTag","authenticateUser.doInBackground s'est terminé sans problème");
                return authenticateUserPackage[0];
            }
            catch(Exception e)
            {
                Log.i("UserDAOTag","L'exception suivante a été rencontrée dans authenticateUser:  "+e.getMessage());
                authenticateUserPackage[0].setResponseCode(0);
                return authenticateUserPackage[0];
            }


        }
        protected void onPostExecute(AuthenticateUserPackage response)
        {
            if(ResponseCodeChecker.checkWhetherTaskSucceeded(response.getResponseCode()))
            {
                new LoadProfile().execute(response);
            }

        }
    }
    private class LoadProfile extends AsyncTask<AuthenticateUserPackage,Void,AuthenticateUserPackage>
    {
        protected AuthenticateUserPackage doInBackground(AuthenticateUserPackage... packageToFill)
        {
            try
            {
                URL url = new URL(manager.getApiUserProfilesConnectionString()+"/UserProfileByUsername?username="+packageToFill[0].getUserResponse().getUsername());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type","application/json");
                connection.setRequestProperty("Authorization","Bearer "+packageToFill[0].getUserResponse().getAccessToken());
                connection.setDoOutput(true);
                packageToFill[0].setResponseCode(connection.getResponseCode());
                if(!ResponseCodeChecker.checkWhetherTaskSucceeded(packageToFill[0].getResponseCode()))
                {
                    connection.disconnect();
                    return packageToFill[0];
                }
                String jsonResponseString = JsonParser.jsonStringFromConnection(connection);
                connection.disconnect();
                UserProfileDTO responseDTO;

                return packageToFill[0];
            }
            catch(Exception e)
            {
                packageToFill[0].setResponseCode(0);
                return packageToFill[0];
            }
        }
        protected void onPostExecute(AuthenticateUserPackage response)
        {

        }
    }
}
