package dtomodels.eventDTO;


import java.util.ArrayList;
import java.util.Date;

public class EventPresentationDTO
{
    private long Id;
    private String Title;
    private int UserCount;
    private String Address;
    private Date Date;
    private ArrayList<String> CategoriesNames;
    private String CreatorFirstName;
    private String CreatorLastName;
    private String CreatorUsername;
    private String Description;
    private String UrlFacebook;
    private double Longitude;
    private double Latitude;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getUserCount() {
        return UserCount;
    }

    public void setUserCount(int userCount) {
        UserCount = userCount;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public ArrayList<String> getCategoriesNames() {
        return CategoriesNames;
    }

    public void setCategoriesNames(ArrayList<String> categoriesNames) {
        CategoriesNames = categoriesNames;
    }

    public String getCreatorFirstName() {
        return CreatorFirstName;
    }

    public void setCreatorFirstName(String creatorFirstName) {
        CreatorFirstName = creatorFirstName;
    }

    public String getCreatorLastName() {
        return CreatorLastName;
    }

    public void setCreatorLastName(String creatorLastName) {
        CreatorLastName = creatorLastName;
    }

    public String getCreatorUsername() {
        return CreatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        CreatorUsername = creatorUsername;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrlFacebook() {
        return UrlFacebook;
    }

    public void setUrlFacebook(String urlFacebook) {
        UrlFacebook = urlFacebook;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }
}
