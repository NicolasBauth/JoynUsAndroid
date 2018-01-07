package dtomodels.eventDTO;


import java.util.ArrayList;
import java.util.Date;

public class EventScanDTO
{
    private long Id;
    private String Title;
    private String Address;
    private Date Date;
    private ArrayList<String> CategoriesNames;
    private ArrayList<String> TagsNames;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        this.Date = date;
    }

    public ArrayList<String> getCategoriesNames() {
        return CategoriesNames;
    }

    public void setCategoriesNames(ArrayList<String> categoriesNames) {
        this.CategoriesNames = categoriesNames;
    }

    public ArrayList<String> getTagsNames() {
        return TagsNames;
    }

    public void setTagsNames(ArrayList<String> tagsNames) {
        this.TagsNames = tagsNames;
    }
}
