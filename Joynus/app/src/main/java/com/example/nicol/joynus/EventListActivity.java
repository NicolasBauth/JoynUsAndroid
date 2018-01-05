package com.example.nicol.joynus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import adapters.EventListAdapter;
import dao.EventDAO;
import dtomodels.userDTO.EventParticipationForm;
import model.Event;
import taskmodels.EventDetailsPackage;
import taskmodels.UserParticipationPackage;
import utility.ResponseCodeChecker;

public class EventListActivity extends BaseActivity
{
    private Event selectedEvent;
    private ArrayList<Event> eventsToDisplay;
    private ListView eventsListView;
    private Button goToDescriptionButton;
    private EventListAdapter adapter;
    private EventDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        dao = new EventDAO();
        eventsToDisplay = getIntent().getExtras().getParcelableArrayList("eventsToDisplay");
        Collections.sort(eventsToDisplay);
        goToDescriptionButton = findViewById(R.id.eventListDetailButton);
        eventsListView = findViewById(R.id.event_list_listview);
        adapter = new EventListAdapter(eventsToDisplay,this);
        eventsListView.setAdapter(adapter);
        eventsListView.setOnItemClickListener(eventsListViewOnItemClickListener());
        goToDescriptionButton.setOnClickListener(detailsButtonListener());
    }

    public View.OnClickListener detailsButtonListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(selectedEvent == null)
                {
                    ViewStaticMethods.displayMessage(getString(R.string.event_list_no_selected_event));
                }
                else
                {
                    EventDetailsPackage packageToSend = new EventDetailsPackage();
                    packageToSend.setEventId(selectedEvent.getDbId());
                    packageToSend.setSender(EventListActivity.this);
                    dao.getEventDetails(packageToSend);
                }
            }
        };
        return listener;
    }
    public AdapterView.OnItemClickListener eventsListViewOnItemClickListener()
    {
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                selectedEvent = eventsToDisplay.get(position);
            }
        };
        return listener;
    }

    public void notifyEventDetailsSuccess(Event response)
    {

        selectedEvent = response;
        EventParticipationForm formToSend = new EventParticipationForm();
        formToSend.setEventId(selectedEvent.getDbId());
        formToSend.setUsername(LoginActivity.getCurrentApplicationUser().getUsername());
        UserParticipationPackage packageToSend = new UserParticipationPackage();
        packageToSend.setFormToSend(formToSend);
        packageToSend.setEventListActivitySender(EventListActivity.this);
        dao.getIsUserParticipatingToEvent(packageToSend);
    }
    public void notifyEventDetailsFailure(int responseCode)
    {
        ViewStaticMethods.displayMessage(getString(R.string.event_list_event_removed));
    }

    public void notifyIsUserParticipatingToEventSuccess(UserParticipationPackage response)
    {
        Intent goToDetailsIntent = new Intent(EventListActivity.this,EventDescriptionActivity.class);
        goToDetailsIntent.putExtra("eventToDisplay",selectedEvent);
        goToDetailsIntent.putExtra("isUserParticipating",response.getIsUserParticipating());
        startActivity(goToDetailsIntent);
    }
    public void notifyIsUserParticipatingToEventFailure(int responseCode)
    {
        ViewStaticMethods.displayMessage(ResponseCodeChecker.getResponseCodeErrorMessage(responseCode));
    }
}
