package com.example.nicol.joynus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import adapters.InterestsGridViewAdapter;
import dao.EventDAO;
import model.User;
import taskmodels.EventListingPackage;
import utility.ResponseCodeChecker;

public class ProfileActivity extends BaseActivity {

    private InterestsGridViewAdapter interestsGridAdapter;
    private User userToDisplay;
    private GridView userInterestsGrid;
    private TextView presentationStringView;
    private Button editInterestsButton;
    private Button createdEventsButton;
    private Button participatingEventsButton;
    private EventDAO eventDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userToDisplay = getIntent().getExtras().getParcelable("profileToDisplay");
        userInterestsGrid = (GridView) findViewById(R.id.userInterestsGridView);
        presentationStringView = (TextView) findViewById(R.id.presentation);
        editInterestsButton = (Button) findViewById(R.id.editInterestsButton);
        createdEventsButton = (Button) findViewById(R.id.createdEventsButton);
        participatingEventsButton = (Button) findViewById(R.id.profileParticipatingEventsButton);
        eventDAO = new EventDAO();
        if(!userToDisplay.getUsername().equals(LoginActivity.getCurrentApplicationUser().getUsername()))
        {
            editInterestsButton.setEnabled(false);
            editInterestsButton.setVisibility(View.GONE);
        }
        else {
            editInterestsButton.setOnClickListener(editInterestsListener());
        }
        createdEventsButton.setOnClickListener(createdEventsListener());
        participatingEventsButton.setOnClickListener(participatingEventsListener());
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        String presentationString = userToDisplay.getFirstname()+", "+ userToDisplay.getAge()+" ans";
        presentationStringView.setText(presentationString);
        interestsGridAdapter = new InterestsGridViewAdapter(this, userToDisplay.getInterests());
        userInterestsGrid.setAdapter(interestsGridAdapter);
    }

    public View.OnClickListener editInterestsListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                    Intent intent = new Intent(ProfileActivity.this, EditInterestActivity.class);
                    startActivity(intent);

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
                EventListingPackage packageToFill = new EventListingPackage();
                packageToFill.setUsername(userToDisplay.getUsername());
                packageToFill.setSender(ProfileActivity.this);
                eventDAO.getEventsCreatedByUser(packageToFill);
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
                EventListingPackage packageToFill = new EventListingPackage();
                packageToFill.setUsername(userToDisplay.getUsername());
                packageToFill.setSender(ProfileActivity.this);
                eventDAO.getEventsUserParticipates(packageToFill);
            }
        };
        return listener;
    }

    public void notifyEventsListingFailed(int responseCode)
    {
        ViewStaticMethods.displayMessage(ResponseCodeChecker.getResponseCodeErrorMessage(responseCode));
    }

    public void notifyEventsListingSuccess(EventListingPackage resultPackage)
    {
        if(resultPackage.getEventListing().size()== 0)
        {
            ViewStaticMethods.displayMessage(getString(R.string.profile_no_events_found));
        }
        else
        {
            Intent intent = new Intent(ProfileActivity.this,EventListActivity.class);
            intent.putParcelableArrayListExtra("eventsToDisplay", resultPackage.getEventListing());
            startActivity(intent);
        }
    }



}
