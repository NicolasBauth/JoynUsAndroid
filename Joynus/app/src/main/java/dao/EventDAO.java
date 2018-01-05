package dao;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.nicol.joynus.LoginActivity;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import dtomodels.eventDTO.EventPresentationDTO;
import dtomodels.eventDTO.EventShortDTO;
import model.Event;
import service.EventService;
import taskmodels.DeleteEventPackage;
import taskmodels.EventDetailsPackage;
import taskmodels.EventListingPackage;
import taskmodels.HttpReturnPackage;
import taskmodels.UserParticipationPackage;
import utility.ConnectionStringsManager;
import utility.HttpMethodSetups;
import utility.JsonParser;
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

    public void getEventDetails(EventDetailsPackage eventDetailsPackage)
    {
        new GetEventDetails().execute(eventDetailsPackage);
    }
    public void getIsUserParticipatingToEvent(UserParticipationPackage userParticipationPackage)
    {
        new GetIsUserParticipatingToEvent().execute(userParticipationPackage);
    }
    public void subscribeOrUnsubscribe(UserParticipationPackage participationPackage)
    {
        new SubscribeOrUnsubscribe().execute(participationPackage);
    }
    public void deleteEvent(DeleteEventPackage deleteEventPackage)
    {
        new DeleteEvent().execute(deleteEventPackage);
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
    private class GetEventDetails extends AsyncTask<EventDetailsPackage,Void,EventDetailsPackage>
    {
        protected EventDetailsPackage doInBackground(EventDetailsPackage... eventDetailsPackages)
        {
            try
            {
                String urlToQuery = manager.getApiEventsConnectionString()+"/GetEventById?id="+eventDetailsPackages[0].getEventId();
                EventPresentationDTO responseModel = new EventPresentationDTO();
                HttpReturnPackage resultPackage = HttpMethodSetups.basicGetMethodSetupAndDataFetching(urlToQuery,responseModel,false);
                eventDetailsPackages[0].setResponseCode(resultPackage.getRequestResponseCode());
                Event parsedEventResult = EventService.eventPresentationDTOToEvent((EventPresentationDTO) resultPackage.getObjectResult());
                eventDetailsPackages[0].setResponse(parsedEventResult);
                return eventDetailsPackages[0];
            }
            catch(Exception e)
            {
                eventDetailsPackages[0].setResponseCode(0);
                return eventDetailsPackages[0];
            }
        }
        protected void onPostExecute(EventDetailsPackage response)
        {
            if(ResponseCodeChecker.checkWhetherTaskSucceeded(response.getResponseCode()))
            {
                response.getSender().notifyEventDetailsSuccess(response.getResponse());
            }
            else
            {
                response.getSender().notifyEventDetailsFailure(response.getResponseCode());
            }
        }


    }
    private class GetIsUserParticipatingToEvent extends AsyncTask<UserParticipationPackage,Void, UserParticipationPackage>
    {
        protected UserParticipationPackage doInBackground(UserParticipationPackage... userParticipationPackage)
        {
            try
            {
                String urlToQuery = manager.getApiEventsConnectionString()+"/IsUserParticipatingToEvent";
                String jsonToPost = JsonParser.objectToJson(userParticipationPackage[0].getFormToSend());
                HttpURLConnection connection = HttpMethodSetups.basicPutMethodSetup(urlToQuery,true,true);
                connection.connect();
                OutputStream outputStream = connection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                writer.write(jsonToPost);
                writer.flush();
                userParticipationPackage[0].setResponseCode(connection.getResponseCode());
                writer.close();
                outputStream.close();
                if(ResponseCodeChecker.checkWhetherTaskSucceeded(connection.getResponseCode()))
                {
                    String jsonResponseString = JsonParser.jsonStringFromConnection(connection);
                    if(jsonResponseString.equals("true"))
                    {
                        userParticipationPackage[0].setIsUserParticipating(true);
                    }
                    else
                    {
                        userParticipationPackage[0].setIsUserParticipating(false);
                    }
                }

                return userParticipationPackage[0];
            }
            catch(Exception e)
            {
                userParticipationPackage[0].setResponseCode(0);
                return userParticipationPackage[0];
            }
        }
        protected void onPostExecute(UserParticipationPackage response)
        {
            if(ResponseCodeChecker.checkWhetherTaskSucceeded(response.getResponseCode()))
            {
                response.getEventListActivitySender().notifyIsUserParticipatingToEventSuccess(response);
            }
            else
            {
                response.getEventListActivitySender().notifyIsUserParticipatingToEventFailure(response.getResponseCode());
            }
        }
    }
    private class SubscribeOrUnsubscribe extends AsyncTask<UserParticipationPackage,Void, UserParticipationPackage>
    {
        protected UserParticipationPackage doInBackground(UserParticipationPackage... userParticipationPackage)
        {
            try
            {
                String urlToQuery = manager.getApiEventsConnectionString();
                urlToQuery += userParticipationPackage[0].getIsUserParticipating()?"/CancelParticipationToEvent":"/ParticipateToEvent";
                HttpReturnPackage resultPackage = HttpMethodSetups.postOrPutMethodSetupAndPosting(urlToQuery,userParticipationPackage[0].getFormToSend(),null,true,false);
                userParticipationPackage[0].setResponseCode(resultPackage.getRequestResponseCode());
                return userParticipationPackage[0];
            }
            catch(Exception e)
            {
                userParticipationPackage[0].setResponseCode(0);
                return userParticipationPackage[0];
            }
        }
        protected void onPostExecute(UserParticipationPackage response)
        {
            if(ResponseCodeChecker.checkWhetherTaskSucceeded(response.getResponseCode()))
            {
                response.setIsUserParticipating(!response.getIsUserParticipating());
                response.getEventDescriptionActivitySender().notifySubscribeOrUnsubscribeSuccess(response);
            }
            else
            {
                response.getEventDescriptionActivitySender().notifySubscribeOrUnsubscibeFailure(response.getResponseCode());
            }
        }
    }
    private class DeleteEvent extends AsyncTask<DeleteEventPackage,Void, DeleteEventPackage>
    {
        protected DeleteEventPackage doInBackground(DeleteEventPackage... deleteEventPackage)
        {
            try {
                String urlToQuery = manager.getApiEventsConnectionString() + "/DeleteEvent?id=" + deleteEventPackage[0].getIdOfEventToSuppress();
                HttpURLConnection connection = HttpMethodSetups.basicDeleteMethodSetup(urlToQuery, true);
                connection.connect();
                deleteEventPackage[0].setResponseCode(connection.getResponseCode());
                connection.disconnect();
                return deleteEventPackage[0];
            }
            catch(Exception e)
            {
                deleteEventPackage[0].setResponseCode(0);
                return deleteEventPackage[0];
            }
        }
        protected void onPostExecute(DeleteEventPackage response)
        {
            if(ResponseCodeChecker.checkWhetherTaskSucceeded(response.getResponseCode()))
            {
                response.getSender().notifyDeleteSuccess();
            }
            else
            {
                response.getSender().notifyDeleteFailure(response.getResponseCode());
            }
        }
    }



}
