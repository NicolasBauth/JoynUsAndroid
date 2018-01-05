package taskmodels;


import com.example.nicol.joynus.EventDescriptionActivity;

public class DeleteEventPackage
{
    private int responseCode;
    private EventDescriptionActivity sender;
    private long idOfEventToSuppress;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public EventDescriptionActivity getSender() {
        return sender;
    }

    public void setSender(EventDescriptionActivity sender) {
        this.sender = sender;
    }

    public long getIdOfEventToSuppress() {
        return idOfEventToSuppress;
    }

    public void setIdOfEventToSuppress(long idOfEventToSuppress) {
        this.idOfEventToSuppress = idOfEventToSuppress;
    }
}
