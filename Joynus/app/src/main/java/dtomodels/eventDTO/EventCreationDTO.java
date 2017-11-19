package dtomodels.eventDTO;


import java.util.ArrayList;
import java.util.Date;

public class EventCreationDTO
{
    private String title;
    private double longitude;
    private double latitude;
    private String address;
    private Date date;
    private String description;
    private ArrayList<Long> categoriesId;
    private String facebookUrl;
    private ArrayList<Long> tagsId;
    private String creatorUsername;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoriesId(ArrayList<Long> categoriesId) {
        this.categoriesId = categoriesId;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public void setTagsId(ArrayList<Long> tagsId) {
        this.tagsId = tagsId;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public String getTitle() {

        return title;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Long> getCategoriesId() {
        return categoriesId;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public ArrayList<Long> getTagsId() {
        return tagsId;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }
}
