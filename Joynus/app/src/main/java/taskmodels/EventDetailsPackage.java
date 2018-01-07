package taskmodels;


import com.example.nicol.joynus.BaseActivity;
import com.example.nicol.joynus.EventDescriptionActivity;
import com.example.nicol.joynus.EventListActivity;

import model.Event;

public class EventDetailsPackage
{
    private int responseCode;
    private Event response;
    private long eventId;
    private EventListActivity sender;


    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Event getResponse() {
        return response;
    }

    public void setResponse(Event response) {
        this.response = response;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public EventListActivity  getSender() {
        return sender;
    }

    public void setSender(EventListActivity  sender) {
        this.sender = sender;
    }
}
