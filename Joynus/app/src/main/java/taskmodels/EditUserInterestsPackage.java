package taskmodels;


import com.example.nicol.joynus.EditInterestActivity;

import java.util.ArrayList;

import dtomodels.categoryDTO.UpdateUserInterestForm;
import model.Category;

public class EditUserInterestsPackage
{
    private int responseCode;
    private UpdateUserInterestForm formToSend;
    private EditInterestActivity sender;
    private ArrayList<Category> updatedInterests;

    public ArrayList<Category> getUpdatedInterests() {
        return updatedInterests;
    }

    public void setUpdatedInterests(ArrayList<Category> updatedInterests) {
        this.updatedInterests = updatedInterests;
    }

    public EditInterestActivity getSender() {
        return sender;
    }

    public void setSender(EditInterestActivity sender) {
        this.sender = sender;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public UpdateUserInterestForm getFormToSend() {
        return formToSend;
    }

    public void setFormToSend(UpdateUserInterestForm formToSend) {
        this.formToSend = formToSend;
    }


}
