package dao;


import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dtomodels.userDTO.UserCredentialsDTO;
import model.AuthenticatedUser;
import utility.ConnectionStringsManager;
import utility.JsonParser;

public class UserDAO
{
    private ConnectionStringsManager manager;
    public UserDAO()
    {
        manager = new ConnectionStringsManager();
    }
    public AuthenticatedUser authenticateUser(UserCredentialsDTO usercredentials)
    {
        new AuthenticateUser().execute(usercredentials);
        return null;
    }
    private class AuthenticateUser extends AsyncTask<UserCredentialsDTO,Void,Integer>
    {
        private Integer responseCode;

        protected void onPreExecute()
        {
            responseCode = 0;
        }
        protected Integer doInBackground(UserCredentialsDTO... userCredentials)
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
                urlEncodedStringToSend +=userCredentials[0].getUsername();
                urlEncodedStringToSend +="&password=";
                urlEncodedStringToSend +=userCredentials[0].getPassword();
                writer.write(urlEncodedStringToSend);
                writer.flush();
                responseCode = connection.getResponseCode();
                writer.close();
                outputStream.close();
                connection.disconnect();

            }
            catch(Exception e)
            {
                Log.i("UserDAOTag","L'exception suivante a été rencontrée dans authenticateUser:  "+e.getMessage());

            }
            return responseCode;
        }
        protected void onPostExecute(Integer responseCode)
        {
            Log.i("UserDAOTag","L'authentification de l'utilisateur est terminée. le code de réponse est "+responseCode);
        }
    }
}
