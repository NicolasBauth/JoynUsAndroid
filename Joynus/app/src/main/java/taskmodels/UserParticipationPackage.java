package taskmodels;


import com.example.nicol.joynus.EventDescriptionActivity;
import com.example.nicol.joynus.EventListActivity;

import dtomodels.userDTO.EventParticipationForm;

public class UserParticipationPackage
{
    private EventParticipationForm formToSend;
    private Boolean isUserParticipating;
    private EventListActivity eventListActivitySender;
    private EventDescriptionActivity eventDescriptionActivitySender;
    private int responseCode;

    public EventDescriptionActivity getEventDescriptionActivitySender() {
        return eventDescriptionActivitySender;
    }

    public void setEventDescriptionActivitySender(EventDescriptionActivity eventDescriptionActivitySender) {
        this.eventDescriptionActivitySender = eventDescriptionActivitySender;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public EventParticipationForm getFormToSend() {
        return formToSend;
    }

    public void setFormToSend(EventParticipationForm formToSend) {
        this.formToSend = formToSend;
    }

    public Boolean getIsUserParticipating() {
        return isUserParticipating;
    }

    public void setIsUserParticipating(Boolean isUserParticipating) {
        this.isUserParticipating = isUserParticipating;
    }

    public EventListActivity getEventListActivitySender() {
        return eventListActivitySender;
    }

    public void setEventListActivitySender(EventListActivity eventListActivitySender) {
        this.eventListActivitySender = eventListActivitySender;
    }
}
