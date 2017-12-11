package com.example.nicol.joynus;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

import controller.UserController;

public class LoginActivity extends AppCompatActivity
{
    private Button loginButton;
    private Button registerButton;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Intent loginIntent;
    private Intent registerIntent;
    private UserController userController;
    private static Context contextOfApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        contextOfApplication = getApplicationContext();
        loginButton = (Button) findViewById(R.id.LoginButton);
        registerButton = (Button) findViewById(R.id.CreateAccountButton);
        usernameEditText = (EditText)findViewById(R.id.LoginEmailInput);
        passwordEditText = (EditText)findViewById(R.id.LoginPasswordInput);
        registerButton.setOnClickListener(registerButtonOnClickListener());
        loginButton.setOnClickListener(loginButtonOnClickListener());
        userController = new UserController();

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
                    userController.authenticateUser(usernameToSend,passwordToSend);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Veuillez renseigner un nom d'utilisateur et un mot de passe!",Toast.LENGTH_LONG).show();
                }
            }
        };
        return listener;
    }
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
}
