package dtomodels.userDTO;


import java.util.ArrayList;
import java.util.Date;

public class UserProfileDTO
{
    private String FirstName;
    private String LastName;
    private Date BirthDate;
    private ArrayList<String> Interests;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public Date getBirthdate() {
        return BirthDate;
    }

    public void setBirthdate(Date birthdate) {
        this.BirthDate = birthdate;
    }

    public ArrayList<String> getInterests() {
        return Interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.Interests = interests;
    }
}
