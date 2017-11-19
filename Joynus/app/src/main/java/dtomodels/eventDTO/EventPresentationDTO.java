package dtomodels.eventDTO;


import java.util.ArrayList;
import java.util.Date;

public class EventPresentationDTO
{
    private long id;
    private String title;
    private int userCount;
    private String address;
    private Date date;
    private ArrayList<String> categoriesNames;
    private String creatorFirstName;
    private String creatorLastName;
    private String creatorUsername;
    private String description;
    private String facebookUrl;
    private double longitude;
    private double latitude;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategoriesNames(ArrayList<String> categoriesNames) {
        this.categoriesNames = categoriesNames;
    }

    public void setCreatorFirstName(String creatorFirstName) {
        this.creatorFirstName = creatorFirstName;
    }

    public void setCreatorLastName(String creatorLastName) {
        this.creatorLastName = creatorLastName;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCreatorFirstName() {

        return creatorFirstName;
    }

    public String getCreatorLastName() {
        return creatorLastName;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getDescription() {
        return description;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public long getId() {

        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getUserCount() {
        return userCount;
    }

    public String getAddress() {
        return address;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<String> getCategoriesNames() {
        return categoriesNames;
    }
}
