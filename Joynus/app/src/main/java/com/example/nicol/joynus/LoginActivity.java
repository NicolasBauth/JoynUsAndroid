package com.example.nicol.joynus;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

import controller.UserController;
import dao.UserDAO;
import dtomodels.userDTO.UserCredentialsDTO;
import taskmodels.AuthenticateUserPackage;
import utility.ResponseCodeChecker;

public class LoginActivity extends AppCompatActivity
{
    private Button loginButton;
    private Button registerButton;
    private UserDAO userDAO;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Intent loginIntent;
    private Intent registerIntent;
    private static Context contextOfApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        contextOfApplication = getApplicationContext();
        userDAO = new UserDAO();
        loginButton = (Button) findViewById(R.id.LoginButton);
        registerButton = (Button) findViewById(R.id.CreateAccountButton);
        usernameEditText = (EditText)findViewById(R.id.LoginEmailInput);
        passwordEditText = (EditText)findViewById(R.id.LoginPasswordInput);
        registerButton.setOnClickListener(registerButtonOnClickListener());
        loginButton.setOnClickListener(loginButtonOnClickListener());

    }
    public View.OnClickListener registerButtonOnClickListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        };
        return listener;
    }
    public View.OnClickListener loginButtonOnClickListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //loginIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                //startActivity(loginIntent);
                String usernameToSend = usernameEditText.getText().toString();
                String passwordToSend = passwordEditText.getText().toString();
                if(!passwordToSend.equals("") && !usernameToSend.equals(""))
                {
                    authenticateUser(usernameToSend,passwordToSend);
                    loginButton.setEnabled(false);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,getString(R.string.login_username_and_password_missing),Toast.LENGTH_LONG).show();
                }
            }
        };
        return listener;
    }
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    public void authenticateUser(String username,String password)
    {
        UserCredentialsDTO userCredentials = new UserCredentialsDTO();
        userCredentials.setUsername(username);
        userCredentials.setPassword(password);
        AuthenticateUserPackage packageToFill = new AuthenticateUserPackage();
        packageToFill.setUserCredentials(userCredentials);
        packageToFill.setSender(this);
        userDAO.authenticateUser(packageToFill);
    }

    public void notifyAuthenticatedUser(AuthenticateUserPackage response)
    {
        if(!ResponseCodeChecker.checkWhetherTaskSucceeded(response.getResponseCode()))
        {
            ViewStaticMethods.displayMessage(ResponseCodeChecker.getResponseCodeErrorMessage(response.getResponseCode()));
        }
        else
        {
            ViewStaticMethods.displayMessage("Authentification réussie pour " +response.getUserResponse().getUsername()+","+response.getUserResponse().getAge());
        }
    }

    public void notifyAuthenticationFailed(int responseCode)
    {
        loginButton.setEnabled(true);
        String message = ResponseCodeChecker.getResponseCodeErrorMessage(responseCode);
        String messageToDisplay = getString(R.string.authentication_failed) +" : "+message;
        ViewStaticMethods.displayMessage(messageToDisplay);
    }



}
