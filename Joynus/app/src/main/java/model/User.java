package model;


import java.util.ArrayList;
import java.util.Date;

public class User
{
    private Date birthdate;
    private long dbId;
    private String firstname;
    private String lastname;
    private String username;
    private String profileImagePath;
    private ArrayList<Category> Interests;

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public ArrayList<Category> getInterests() {
        return Interests;
    }

    public void setInterests(ArrayList<Category> interests) {
        Interests = interests;
    }
    public int getAge()
    {
        Date now = new Date();
        int age = now.getYear() - birthdate.getYear();
        if(now.getMonth() < birthdate.getMonth() || (now.getMonth() == birthdate.getMonth() && now.getDay() < birthdate.getDay()))
        {
            age--;
        }
        return age;
    }
}
