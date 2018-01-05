package service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dtomodels.eventDTO.EventPresentationDTO;
import dtomodels.eventDTO.EventShortDTO;
import model.Category;
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
    public static Event eventPresentationDTOToEvent(EventPresentationDTO eventToParse)
    {
        Event parsedEvent = new Event();
        parsedEvent.setDate(eventToParse.getDate());
        parsedEvent.setAddress(eventToParse.getAddress());
        parsedEvent.setTitle(eventToParse.getTitle());
        parsedEvent.setDbId(eventToParse.getId());
        parsedEvent.setCreatorFirstname(eventToParse.getCreatorFirstName());
        parsedEvent.setCreatorLastname(eventToParse.getCreatorLastName());
        parsedEvent.setCreatorUsername(eventToParse.getCreatorUsername());
        parsedEvent.setDescription(eventToParse.getDescription());
        parsedEvent.setLatitude(eventToParse.getLatitude());
        parsedEvent.setLongitude(eventToParse.getLongitude());
        parsedEvent.setParticipantsCount(eventToParse.getUserCount());
        parsedEvent.setUrlFacebook(eventToParse.getUrlFacebook());
        ArrayList<Category> eventCategories = new ArrayList<Category>();
        for (String categoryName : eventToParse.getCategoriesNames())
        {
            eventCategories.add(new Category(categoryName));
        }
        parsedEvent.setCategories(eventCategories);
        return parsedEvent;
    }

    public static String createDateStringFromDate(Date date)
    {
        String formatedDate = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        formatedDate += String.format(Locale.FRANCE,"%02d",day);
        formatedDate +="/";
        formatedDate += String.format(Locale.FRANCE,"%02d",month);
        formatedDate +="/";
        formatedDate += year;
        formatedDate += " à ";
        formatedDate += String.format(Locale.FRANCE,"%02d",hour);
        formatedDate += "h";
        formatedDate += String.format(Locale.FRANCE,"%02d",minute);
        return formatedDate;
    }
}
