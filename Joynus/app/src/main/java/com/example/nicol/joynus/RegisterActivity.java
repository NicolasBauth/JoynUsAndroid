package com.example.nicol.joynus;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity
{
    private Button goBackButton;
    private Button registerButton;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        goBackButton = (Button) findViewById(R.id.RegisterGoBackButton);
        registerButton = (Button) findViewById(R.id.RegisterCreateAccountButton);
        goBackButton.setOnClickListener(goBackButtonOnClickListener());
    }
    public View.OnClickListener goBackButtonOnClickListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        };
        return listener;
    }
}
