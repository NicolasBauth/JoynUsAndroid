package dtomodels.eventDTO;


import java.util.ArrayList;
import java.util.Date;

public class EventScanDTO
{
    private long id;
    private String title;
    private String address;
    private Date date;
    private ArrayList<String> categoriesNames;
    private ArrayList<String> tagsNames;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getCategoriesNames() {
        return categoriesNames;
    }

    public void setCategoriesNames(ArrayList<String> categoriesNames) {
        this.categoriesNames = categoriesNames;
    }

    public ArrayList<String> getTagsNames() {
        return tagsNames;
    }

    public void setTagsNames(ArrayList<String> tagsNames) {
        this.tagsNames = tagsNames;
    }
}
