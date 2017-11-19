package model;


import java.util.ArrayList;
import java.util.Date;

public class Event
{
    private long dbId;
    private double latitude;
    private double longitude;
    private String title;
    private String description;
    private String address;
    private String urlFacebook;
    private Date date;
    private ArrayList<Category> categories;
    private String creatorUsername;
    private String creatorFirstname;
    private String creatorLastname;
    private int participantsCount;

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrlFacebook() {
        return urlFacebook;
    }

    public void setUrlFacebook(String urlFacebook) {
        this.urlFacebook = urlFacebook;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public String getCreatorFirstname() {
        return creatorFirstname;
    }

    public void setCreatorFirstname(String creatorFirstname) {
        this.creatorFirstname = creatorFirstname;
    }

    public String getCreatorLastname() {
        return creatorLastname;
    }

    public void setCreatorLastname(String creatorLastname) {
        this.creatorLastname = creatorLastname;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }
}
