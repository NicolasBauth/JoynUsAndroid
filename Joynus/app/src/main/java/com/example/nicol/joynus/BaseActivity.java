package com.example.nicol.joynus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        final DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        final NavigationView navigationView = (NavigationView) fullView.findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.goToProfileAction:
                        fullView.closeDrawers();
                        if(!(BaseActivity.this instanceof ProfileActivity))
                        {
                            navigateToActivityFromHamburgerMenu(ProfileActivity.class);
                            return true;
                        }
                        return false;

                    case R.id.goToCreateEventAction:
                        fullView.closeDrawers();
                        if(!(BaseActivity.this instanceof CreateEventActivity))
                        {
                            navigateToActivityFromHamburgerMenu(CreateEventActivity.class);
                            return true;
                        }
                    case R.id.goToSearchEventAction:
                        fullView.closeDrawers();
                        if(!(BaseActivity.this instanceof SearchEventActivity))
                        {
                            navigateToActivityFromHamburgerMenu(SearchEventActivity.class);
                            return true;
                        }
                    default:
                        return false;
                }
            }
        });

    }
    protected void navigateToActivityFromHamburgerMenu(Class destination)
    {

        Intent navigationIntent = new Intent(BaseActivity.this, destination);
        startActivity(navigationIntent);
        if(!(BaseActivity.this instanceof MainPageActivity))
        {
            finish();
        }
    }





}
