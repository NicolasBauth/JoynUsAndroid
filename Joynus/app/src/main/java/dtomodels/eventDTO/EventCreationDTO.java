package dtomodels.eventDTO;


import java.util.ArrayList;
import java.util.Date;

public class EventCreationDTO
{
    private String Title;
    private double Longitude;
    private double Latitude;
    private String Address;
    private Date Date;
    private String Description;
    private ArrayList<Long> CategoriesId;
    private String FacebookUrl;
    private ArrayList<Long> TagsId;
    private String CreatorUsername;

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public void setDate(Date date) {
        this.Date = date;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public void setCategoriesId(ArrayList<Long> categoriesId) {
        this.CategoriesId = categoriesId;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.FacebookUrl = facebookUrl;
    }

    public void setTagsId(ArrayList<Long> tagsId) {
        this.TagsId = tagsId;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.CreatorUsername = creatorUsername;
    }

    public String getTitle() {

        return Title;
    }

    public double getLongitude() {
        return Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public String getAddress() {
        return Address;
    }

    public Date getDate() {
        return Date;
    }

    public String getDescription() {
        return Description;
    }

    public ArrayList<Long> getCategoriesId() {
        return CategoriesId;
    }

    public String getFacebookUrl() {
        return FacebookUrl;
    }

    public ArrayList<Long> getTagsId() {
        return TagsId;
    }

    public String getCreatorUsername() {
        return CreatorUsername;
    }
}
