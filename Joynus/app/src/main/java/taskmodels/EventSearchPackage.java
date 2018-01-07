package taskmodels;


import com.example.nicol.joynus.SearchEventActivity;

import java.util.ArrayList;

import dtomodels.eventDTO.EventScanDTO;
import dtomodels.eventDTO.EventSearchDTO;
import model.Category;
import model.Event;

public class EventSearchPackage
{
    private int responseCode;
    private EventSearchDTO scanForm;
    private ArrayList<Event> scanResult;
    private ArrayList<Category> categoriesFilters;
    private SearchEventActivity sender;


    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public EventSearchDTO getScanForm() {
        return scanForm;
    }

    public void setScanForm(EventSearchDTO scanForm) {
        this.scanForm = scanForm;
    }

    public ArrayList<Event> getScanResult() {
        return scanResult;
    }

    public void setScanResult(ArrayList<Event> scanResult) {
        this.scanResult = scanResult;
    }

    public SearchEventActivity getSender() {
        return sender;
    }

    public void setSender(SearchEventActivity sender) {
        this.sender = sender;
    }

    public ArrayList<Category> getCategoriesFilters() {
        return categoriesFilters;
    }

    public void setCategoriesFilters(ArrayList<Category> categoriesFilters) {
        this.categoriesFilters = categoriesFilters;
    }
}
