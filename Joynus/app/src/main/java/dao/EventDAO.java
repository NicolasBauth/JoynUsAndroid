package dao;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.nicol.joynus.LoginActivity;

import java.net.URL;
import java.util.ArrayList;

import dtomodels.eventDTO.EventShortDTO;
import model.Event;
import service.EventService;
import taskmodels.EventListingPackage;
import taskmodels.HttpReturnPackage;
import utility.ConnectionStringsManager;
import utility.HttpMethodSetups;
import utility.ResponseCodeChecker;

public class EventDAO
{
    private ConnectionStringsManager manager;
    private SharedPreferences preferences;
    public EventDAO()
    {
        manager = new ConnectionStringsManager();
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.getContextOfApplication());
    }
    public void getEventsUserParticipates(EventListingPackage packageToFill)
    {
        packageToFill.setDestinationUrl(manager.getApiEventsConnectionString()+"/GetEventsUserParticipates?username="+packageToFill.getUsername());
        new GetEventListing().execute(packageToFill);
    }
    public void getEventsCreatedByUser(EventListingPackage packageToFill)
    {
        packageToFill.setDestinationUrl(manager.getApiEventsConnectionString()+"/GetEventsCreatedByUser?username="+packageToFill.getUsername());
        new GetEventListing().execute(packageToFill);
    }
    private class GetEventListing extends AsyncTask<EventListingPackage,Void,EventListingPackage>
    {
        protected EventListingPackage doInBackground(EventListingPackage... eventListingPackage)
        {
            try
            {
                EventShortDTO responseModel = new EventShortDTO();
                HttpReturnPackage resultPackage = HttpMethodSetups.basicGetMethodSetupAndDataFetching(eventListingPackage[0].getDestinationUrl(),responseModel,true);
                eventListingPackage[0].setResponseCode(resultPackage.getRequestResponseCode());
                ArrayList<Event> parsedEventsList = new ArrayList<Event>();
                ArrayList<EventShortDTO> foundEvents= (ArrayList<EventShortDTO>) resultPackage.getObjectResult();
                for(EventShortDTO foundEvent : foundEvents)
                {
                    parsedEventsList.add(EventService.eventShortDTOToEvent(foundEvent));
                }
                eventListingPackage[0].setEventListing(parsedEventsList);
                return eventListingPackage[0];
            }
            catch(Exception e)
            {
                eventListingPackage[0].setResponseCode(0);
                return eventListingPackage[0];
            }
        }
        protected void onPostExecute(EventListingPackage response)
        {
            if(ResponseCodeChecker.checkWhetherTaskSucceeded(response.getResponseCode()))
            {
                response.getSender().notifyEventsListingSuccess(response);
            }
            else
            {
                response.getSender().notifyEventsListingFailed(response.getResponseCode());
            }
        }

    }


}
