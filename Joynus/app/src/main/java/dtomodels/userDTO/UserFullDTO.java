package dtomodels.userDTO;


import java.util.ArrayList;
import java.util.Date;

public class UserFullDTO
{
    private String firstName;
    private String lastName;
    private Date Birthdate;
    private ArrayList<String> followersName;
    private ArrayList<String> followingNames;
    private ArrayList<String> createdEvents;
    private ArrayList<String> joinedEvents;
    private ArrayList<String> interests;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(Date birthdate) {
        Birthdate = birthdate;
    }

    public ArrayList<String> getFollowersName() {
        return followersName;
    }

    public void setFollowersName(ArrayList<String> followersName) {
        this.followersName = followersName;
    }

    public ArrayList<String> getFollowingNames() {
        return followingNames;
    }

    public void setFollowingNames(ArrayList<String> followingNames) {
        this.followingNames = followingNames;
    }

    public ArrayList<String> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(ArrayList<String> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public ArrayList<String> getJoinedEvents() {
        return joinedEvents;
    }

    public void setJoinedEvents(ArrayList<String> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }
}
