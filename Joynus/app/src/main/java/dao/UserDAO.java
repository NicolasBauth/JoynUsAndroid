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
import java.util.ArrayList;

import dtomodels.categoryDTO.UpdateUserInterestForm;
import dtomodels.userDTO.TokenPackage;
import dtomodels.userDTO.UserProfileDTO;
import model.Category;
import model.User;
import service.UserService;
import taskmodels.AuthenticateUserPackage;
import taskmodels.EditUserInterestsPackage;
import taskmodels.HttpReturnPackage;
import taskmodels.RegisterUserPackage;
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

    public void registerUser(RegisterUserPackage packageToSend)
    {
        new RegisterUser().execute(packageToSend);
    }
    public void editUserInterests(EditUserInterestsPackage packageToSend)
    {
        new EditInterests().execute(packageToSend);
    }
    public void authenticateUser(AuthenticateUserPackage packageToFill)
    {
        new AuthenticateUser().execute(packageToFill);
    }
    public void loadProfile(AuthenticateUserPackage profilePackage)
    {
        new LoadProfile().execute(profilePackage);
    }
    private class RegisterUser extends AsyncTask<RegisterUserPackage,Void,RegisterUserPackage>
    {
        protected RegisterUserPackage doInBackground(RegisterUserPackage... registerUserPackage)
        {
            try
            {
                String urlToQuery = manager.getAccountConnectionString()+"/Register";
                HttpReturnPackage postResponse = HttpMethodSetups.postOrPutMethodSetupAndPosting(urlToQuery,registerUserPackage[0].getFormToSend(),null,false,true,false);
                registerUserPackage[0].setResponseCode(postResponse.getRequestResponseCode());
                return registerUserPackage[0];
            }
            catch(Exception e)
            {
                registerUserPackage[0].setResponseCode(0);
                return registerUserPackage[0];
            }
        }
        protected void onPostExecute(RegisterUserPackage responsePackage)
        {
            if(ResponseCodeChecker.checkWhetherTaskSucceeded(responsePackage.getResponseCode()))
            {
                responsePackage.getSender().notifyRegisterTaskDone(responsePackage.getResponseCode());
            }
            else
            {
                responsePackage.getSender().notifyRegisterTaskFailed(responsePackage.getResponseCode());
            }
        }

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
                tokenPackageResult = (TokenPackage) JsonParser.getJavaObjectFromJsonString(jsonResponseString,tokenPackageResult,false);
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
            else
            {
                response.getSender().notifyAuthenticationFailed(response.getResponseCode());
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
                HttpReturnPackage resultPackage = HttpMethodSetups.basicGetMethodSetupAndDataFetching(urlToQuery,modelToFill,false);
                packageToFill[0].setResponseCode(resultPackage.getRequestResponseCode());
                if(ResponseCodeChecker.checkWhetherTaskSucceeded(resultPackage.getRequestResponseCode()))
                {
                    UserService.UserFromUserProfileDTO((UserProfileDTO)resultPackage.getObjectResult(),packageToFill[0].getUserResponse());
                }

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
            if(ResponseCodeChecker.checkWhetherTaskSucceeded(response.getResponseCode()))
            {
                if(!(response.getSender() == null)) {
                    response.getSender().notifyAuthenticatedUser(response);
                }
                else
                {
                    response.getEventDescriptionActivitySender().notifyCreatorProfileLoadSuccess(response.getUserResponse());
                }
            }
            else
            {
                if(!(response.getSender() == null)) {
                    response.getSender().notifyAuthenticationFailed(response.getResponseCode());
                }
                else
                {
                    response.getEventDescriptionActivitySender().notifyCreatorProfileFailure(response.getResponseCode());
                }
            }
        }
    }
    private class EditInterests extends AsyncTask<EditUserInterestsPackage,Void,EditUserInterestsPackage>
    {
        protected EditUserInterestsPackage doInBackground(EditUserInterestsPackage ... packageToSend)
        {
            try
            {
                String urlToQuery = manager.getApiUserProfilesConnectionString()+"/UpdateUserInterests";
                UserProfileDTO response = new UserProfileDTO();
                HttpReturnPackage resultPackage = HttpMethodSetups.postOrPutMethodSetupAndPosting(urlToQuery,packageToSend[0].getFormToSend(),response,true,false,false);
                packageToSend[0].setResponseCode(resultPackage.getRequestResponseCode());
                if(ResponseCodeChecker.checkWhetherTaskSucceeded(resultPackage.getRequestResponseCode()))
                {
                    response = (UserProfileDTO) resultPackage.getObjectResult();
                    ArrayList<Category> updatedUserInterests = new ArrayList<Category>();
                    for(String categoryName : response.getInterests())
                    {
                        updatedUserInterests.add(new Category(categoryName));
                    }
                    packageToSend[0].setUpdatedInterests(updatedUserInterests);
                }
                return packageToSend[0];
            }
            catch(Exception e)
            {
                packageToSend[0].setResponseCode(0);
                return packageToSend[0];
            }
        }
        protected void onPostExecute(EditUserInterestsPackage response)
        {
            if(ResponseCodeChecker.checkWhetherTaskSucceeded(response.getResponseCode()))
            {
                response.getSender().notifyEditInterestSuccess(response.getUpdatedInterests());
            }
            else
            {
                response.getSender().notifyEditInterestFailure(response.getResponseCode());
            }
        }
    }
}
