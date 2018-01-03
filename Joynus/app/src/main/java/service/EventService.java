package service;


import dtomodels.eventDTO.EventShortDTO;
import model.Event;

public class EventService
{
    public static Event eventShortDTOToEvent(EventShortDTO eventToParse)
    {
        Event parsedEvent = new Event();
        parsedEvent.setDate(eventToParse.getDate());
        parsedEvent.setAddress(eventToParse.getAddress());
        parsedEvent.setTitle(eventToParse.getTitle());
        parsedEvent.setDbId(eventToParse.getId());
        return parsedEvent;
    }
}
