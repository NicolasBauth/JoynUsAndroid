package com.example.nicol.joynus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

import adapters.EditInterestsGridViewAdapter;
import dao.UserDAO;
import dtomodels.categoryDTO.UpdateUserInterestForm;
import model.Category;
import model.User;
import service.CategoryService;
import taskmodels.EditUserInterestsPackage;
import utility.ResponseCodeChecker;

public class EditInterestActivity extends BaseActivity {

    private EditInterestsGridViewAdapter adapter;
    private User applicationUser;
    private GridView editInterestsGrid;
    private Button editInterestsButton;
    private Category allInterestsList;
    private UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_interest);
        userDAO = new UserDAO();
        editInterestsGrid = (GridView) findViewById(R.id.editInterestsGridView);
        editInterestsButton = (Button) findViewById(R.id.validateEditInterestsButton);
        editInterestsButton.setOnClickListener(editInterestsListener());
        this.applicationUser = LoginActivity.getCurrentApplicationUser();
        adapter = new EditInterestsGridViewAdapter(this, CategoryService.getAllCategoriesArrayList());
        editInterestsGrid.setAdapter(adapter);
    }

    public View.OnClickListener editInterestsListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editInterests();
                editInterestsButton.setEnabled(false);
            }
        };
        return listener;
    }

    public void notifyEditInterestSuccess(ArrayList<Category> updatedInterests)
    {
        LoginActivity.getCurrentApplicationUser().setInterests(updatedInterests);
        ViewStaticMethods.displayMessage(getString(R.string.edit_interest_success));
        finish();
    }
    public void notifyEditInterestFailure(int responseCode)
    {
        ViewStaticMethods.displayMessage(getString(R.string.edit_interest_failure)+" "+ ResponseCodeChecker.getResponseCodeErrorMessage(responseCode));
        editInterestsButton.setEnabled(true);
    }
    public void editInterests()
    {
        EditUserInterestsPackage packageToSend = new EditUserInterestsPackage();
        packageToSend.setSender(this);
        UpdateUserInterestForm formToSend = new UpdateUserInterestForm();
        formToSend.setUsername(LoginActivity.getCurrentApplicationUser().getUsername());
        formToSend.setNewInterestsIds(new ArrayList<Integer>());
        ArrayList<Category> selectedCategories = adapter.getCheckedCategories();
        for(Category category : selectedCategories)
        {
            formToSend.getNewInterestsIds().add(category.getDbId());
        }
        packageToSend.setFormToSend(formToSend);
        userDAO.editUserInterests(packageToSend);
    }
}
