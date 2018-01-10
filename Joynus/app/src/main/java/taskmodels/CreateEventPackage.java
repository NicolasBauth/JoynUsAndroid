package taskmodels;


import com.example.nicol.joynus.CreateEventActivity;

import dtomodels.eventDTO.EventCreationDTO;

public class CreateEventPackage
{
    public int responseCode;
    public EventCreationDTO form;
    public CreateEventActivity sender;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public EventCreationDTO getForm() {
        return form;
    }

    public void setForm(EventCreationDTO form) {
        this.form = form;
    }

    public CreateEventActivity getSender() {
        return sender;
    }

    public void setSender(CreateEventActivity sender) {
        this.sender = sender;
    }
}
