package com.example.nicol.joynus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import model.User;

public class ProfileActivity extends BaseActivity {

    private User applicationUser;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        applicationUser = LoginActivity.getCurrentApplicationUser();
        setContentView(R.layout.activity_profile);
    }

}
