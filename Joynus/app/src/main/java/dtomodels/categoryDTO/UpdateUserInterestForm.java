package dtomodels.categoryDTO;

import java.util.ArrayList;

public class UpdateUserInterestForm
{
    private String username;
    private ArrayList<Integer> newInterestsId;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNewInterestsId(ArrayList<Integer> newInterestsId) {
        this.newInterestsId = newInterestsId;
    }

    public String getUsername() {

        return username;
    }

    public ArrayList<Integer> getNewInterestsId() {
        return newInterestsId;
    }


}
