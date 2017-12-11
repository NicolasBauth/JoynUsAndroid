package dao;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.nicol.joynus.LoginActivity;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dtomodels.userDTO.TokenPackage;
import dtomodels.userDTO.UserProfileDTO;
import taskmodels.AuthenticateUserPackage;
import utility.ConnectionStringsManager;
import utility.HttpMethodSetups;
import utility.JsonParser;
import utility.ResponseCodeChecker;

public class UserDAO
{
    private ConnectionStringsManager manager;
    private SharedPreferences preferences;
    public UserDAO()
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.getContextOfApplication());
        manager = new ConnectionStringsManager();

    }
    public AuthenticateUserPackage authenticateUser(AuthenticateUserPackage packageToFill)
    {
        new AuthenticateUser().execute(packageToFill);
        return packageToFill;
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
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("token",tokenPackageResult.getAccess_token());
                editor.apply();
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
                String urlToQuery = manager.getApiUserProfilesConnectionString()+"/UserProfileByUsername?username="+packageToFill[0].getUserResponse().getUsername();
                UserProfileDTO modelToFill = new UserProfileDTO();
                int responseCode = HttpMethodSetups.basicGetMethodSetupAndDataFetching(urlToQuery,modelToFill);

                packageToFill[0].setResponseCode(responseCode);
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
