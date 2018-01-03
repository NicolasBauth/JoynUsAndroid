package taskmodels;


import com.example.nicol.joynus.ProfileActivity;

import java.util.ArrayList;

import dtomodels.eventDTO.EventShortDTO;
import model.Event;

public class EventListingPackage
{
    private int responseCode;
    private ProfileActivity sender;
    private ArrayList<Event> eventListing;
    private String username;
    private String destinationUrl;

    public EventListingPackage()
    {

    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public ProfileActivity getSender() {
        return sender;
    }

    public void setSender(ProfileActivity sender) {
        this.sender = sender;
    }

    public ArrayList<Event> getEventListing() {
        return eventListing;
    }

    public void setEventListing(ArrayList<Event> eventListing) {
        this.eventListing = eventListing;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }
}
