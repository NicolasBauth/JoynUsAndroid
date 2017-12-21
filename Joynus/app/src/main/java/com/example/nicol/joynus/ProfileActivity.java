package com.example.nicol.joynus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import adapters.InterestsGridViewAdapter;
import model.User;

public class ProfileActivity extends BaseActivity {

    private InterestsGridViewAdapter interestsGridAdapter;
    private User applicationUser;
    private GridView userInterestsGrid;
    private TextView presentationStringView;
    private Button editInterestsButton;
    private Button createdEventsButton;
    private Button participatingEventsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        applicationUser = LoginActivity.getCurrentApplicationUser();
        userInterestsGrid = (GridView) findViewById(R.id.userInterestsGridView);
        presentationStringView = (TextView) findViewById(R.id.presentation);
        editInterestsButton = (Button) findViewById(R.id.editInterestsButton);
        createdEventsButton = (Button) findViewById(R.id.createdEventsButton);
        participatingEventsButton = (Button) findViewById(R.id.profileParticipatingEventsButton);
        String presentationString = applicationUser.getFirstname()+", "+applicationUser.getAge()+" ans";
        presentationStringView.setText(presentationString);
        editInterestsButton.setOnClickListener(editInterestsListener());
        createdEventsButton.setOnClickListener(createdEventsListener());
        participatingEventsButton.setOnClickListener(participatingEventsListener());
        interestsGridAdapter = new InterestsGridViewAdapter(this,applicationUser.getInterests());
        userInterestsGrid.setAdapter(interestsGridAdapter);
    }

    public View.OnClickListener editInterestsListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        };
        return listener;
    }
    public View.OnClickListener createdEventsListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        };
        return listener;
    }
    public View.OnClickListener participatingEventsListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        };
        return listener;
    }

}
