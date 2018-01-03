package dtomodels.categoryDTO;

import java.util.ArrayList;

public class UpdateUserInterestForm
{
    private String Username;
    private ArrayList<Integer> NewInterestsIds;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public ArrayList<Integer> getNewInterestsIds() {
        return NewInterestsIds;
    }

    public void setNewInterestsIds(ArrayList<Integer> newInterestsIds) {
        NewInterestsIds = newInterestsIds;
    }
}
