package dtomodels.userDTO;


public class EventParticipationForm
{
    private String Username;
    private long EventId;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public long getEventId() {
        return EventId;
    }

    public void setEventId(long eventId) {
        this.EventId = eventId;
    }
}
