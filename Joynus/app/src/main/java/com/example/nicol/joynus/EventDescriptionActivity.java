package com.example.nicol.joynus;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import adapters.InterestsGridViewAdapter;
import dao.EventDAO;
import dao.UserDAO;
import dtomodels.userDTO.EventParticipationForm;
import model.Event;
import model.User;
import service.EventService;
import taskmodels.AuthenticateUserPackage;
import taskmodels.DeleteEventPackage;
import taskmodels.UserParticipationPackage;
import utility.ResponseCodeChecker;

public class EventDescriptionActivity extends BaseActivity {

    private Event eventToDisplay;
    private UserDAO userDAO;
    private EventDAO eventDAO;
    private TextView titleTextView;
    private TextView addressTextView;
    private TextView dateTextView;
    private TextView descriptionTextView;
    private GridView categoriesGridView;
    private TextView usersParticipatingTextView;
    private TextView creatorTextView;
    private Button subscribeButton;
    private Button facebookButton;
    private Button creatorProfileButton;
    private Button deleteEventButton;
    private InterestsGridViewAdapter adapter;
    private boolean isUserParticipating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        eventToDisplay = getIntent().getExtras().getParcelable("eventToDisplay");
        isUserParticipating = getIntent().getExtras().getBoolean("isUserParticipating");
        titleTextView = (TextView) findViewById(R.id.event_description_title_textView);
        addressTextView = (TextView) findViewById(R.id.event_description_address_textView);
        dateTextView = (TextView) findViewById(R.id.event_description_date_textView);
        descriptionTextView = (TextView) findViewById(R.id.event_description_description_textView);
        categoriesGridView = (GridView) findViewById(R.id.event_description_categories_gridView);
        usersParticipatingTextView = (TextView) findViewById(R.id.event_description_participants_textView);
        creatorTextView = (TextView) findViewById(R.id.event_description_creator_textView);
        subscribeButton = (Button) findViewById(R.id.event_description_subscribe_button);
        facebookButton = (Button) findViewById(R.id.event_description_facebook_button);
        creatorProfileButton = (Button) findViewById(R.id.event_description_creator_profile_button);
        deleteEventButton = (Button) findViewById(R.id.event_description_deleteEvent_Button);
        userDAO = new UserDAO();
        eventDAO = new EventDAO();
        adapter = new InterestsGridViewAdapter(EventDescriptionActivity.this,eventToDisplay.getCategories());
        categoriesGridView.setAdapter(adapter);
        titleTextView.setText(eventToDisplay.getTitle());
        addressTextView.setText(eventToDisplay.getAddress());
        dateTextView.setText(EventService.createDateStringFromDate(eventToDisplay.getDate()));
        descriptionTextView.setText(eventToDisplay.getDescription());
        String participantsString = eventToDisplay.getParticipantsCount() +" " +getString(R.string.event_description_person_participating);
        usersParticipatingTextView.setText(participantsString);
        String creatorString = getString(R.string.event_description_created_by)+" "+eventToDisplay.getCreatorFirstname()+" "+eventToDisplay.getCreatorLastname();
        creatorTextView.setText(creatorString);
        subscribeButton.setText(isUserParticipating?R.string.event_description_unsubscribe:R.string.event_description_subscribe);
        subscribeButton.setOnClickListener(subscribeButtonListener());
        facebookButton.setOnClickListener(facebookButtonListener());
        creatorProfileButton.setOnClickListener(creatorProfileButtonListener());
        if(!eventToDisplay.getCreatorUsername().equals(LoginActivity.getCurrentApplicationUser().getUsername()))
        {
            deleteEventButton.setEnabled(false);
            deleteEventButton.setVisibility(View.GONE);
        }
        else {
            deleteEventButton.setOnClickListener(deleteEventButtonListener());
        }
    }

    public View.OnClickListener subscribeButtonListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                subscribeButton.setEnabled(false);
                UserParticipationPackage userParticipationPackage = new UserParticipationPackage();
                userParticipationPackage.setIsUserParticipating(isUserParticipating);
                EventParticipationForm formToSend = new EventParticipationForm();
                formToSend.setEventId(eventToDisplay.getDbId());
                formToSend.setUsername(LoginActivity.getCurrentApplicationUser().getUsername());
                userParticipationPackage.setFormToSend(formToSend);
                userParticipationPackage.setEventDescriptionActivitySender(EventDescriptionActivity.this);
                eventDAO.subscribeOrUnsubscribe(userParticipationPackage);
            }
        };
        return listener;
    }
    public View.OnClickListener facebookButtonListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(eventToDisplay.getUrlFacebook() == null)
                {
                    ViewStaticMethods.displayMessage(getString(R.string.event_description_no_facebook_event));
                }
                else
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(eventToDisplay.getUrlFacebook()));
                    startActivity(browserIntent);
                }
            }
        };
        return listener;
    }
    public View.OnClickListener creatorProfileButtonListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(eventToDisplay.getCreatorUsername().equals(LoginActivity.getCurrentApplicationUser().getUsername()))
                {
                    Intent navigationIntent = new Intent(EventDescriptionActivity.this, ProfileActivity.class);
                    navigationIntent.putExtra("profileToDisplay",LoginActivity.getCurrentApplicationUser());
                    startActivity(navigationIntent);
                }
                else
                {
                    creatorProfileButton.setEnabled(false);
                    AuthenticateUserPackage profilePackage = new AuthenticateUserPackage();
                    User userToComplete = new User();
                    userToComplete.setUsername(eventToDisplay.getCreatorUsername());
                    profilePackage.setUserResponse(userToComplete);
                    profilePackage.setEventDescriptionActivitySender(EventDescriptionActivity.this);
                    userDAO.loadProfile(profilePackage);
                }
            }
        };
        return listener;
    }
    public View.OnClickListener deleteEventButtonListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteEventButton.setEnabled(false);
                DeleteEventPackage deleteEventPackage = new DeleteEventPackage();
                deleteEventPackage.setSender(EventDescriptionActivity.this);
                deleteEventPackage.setIdOfEventToSuppress(eventToDisplay.getDbId());
                eventDAO.deleteEvent(deleteEventPackage);
            }
        };
        return listener;
    }

    public void notifyCreatorProfileLoadSuccess(User creatorProfile)
    {
        Intent goToCreatorProfileIntent = new Intent(EventDescriptionActivity.this,ProfileActivity.class);
        goToCreatorProfileIntent.putExtra("profileToDisplay",creatorProfile);
        startActivity(goToCreatorProfileIntent);
    }
    public void notifyCreatorProfileFailure(int responseCode)
    {
        ViewStaticMethods.displayMessage(ResponseCodeChecker.getResponseCodeErrorMessage(responseCode));
        creatorProfileButton.setEnabled(true);
    }
    public void notifySubscribeOrUnsubscribeSuccess(UserParticipationPackage response)
    {
        isUserParticipating = response.getIsUserParticipating();
        if(isUserParticipating)
        {
            subscribeButton.setText(R.string.event_description_unsubscribe);
            eventToDisplay.setParticipantsCount(eventToDisplay.getParticipantsCount()+1);
        }
        else
        {
            subscribeButton.setText(R.string.event_description_subscribe);
            eventToDisplay.setParticipantsCount(eventToDisplay.getParticipantsCount()-1);
        }
        String participantsString = eventToDisplay.getParticipantsCount() +" " +getString(R.string.event_description_person_participating);
        usersParticipatingTextView.setText(participantsString);
        subscribeButton.setEnabled(true);
    }
    public void notifySubscribeOrUnsubscibeFailure(int responseCode)
    {
        ViewStaticMethods.displayMessage(ResponseCodeChecker.getResponseCodeErrorMessage(responseCode));
        subscribeButton.setEnabled(true);
    }

    public void notifyDeleteSuccess()
    {
        ViewStaticMethods.displayMessage(getString(R.string.event_description_event_deleted_successfully));
        finish();
    }
    public void notifyDeleteFailure(int responseCode)
    {
        ViewStaticMethods.displayMessage(ResponseCodeChecker.getResponseCodeErrorMessage(responseCode));
        deleteEventButton.setEnabled(true);
    }

}
