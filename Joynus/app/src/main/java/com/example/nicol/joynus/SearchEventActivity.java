package com.example.nicol.joynus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import adapters.EditInterestsGridViewAdapter;
import dao.EventDAO;
import dtomodels.eventDTO.EventSearchDTO;
import model.User;
import service.CategoryService;
import taskmodels.EventSearchPackage;
import utility.ResponseCodeChecker;

public class SearchEventActivity extends BaseActivity {

    private GridView filtersGridView;
    private EditInterestsGridViewAdapter adapter;
    private Button launchScanButton;
    private Button locateCenterButton;
    private SeekBar selectRadiusSeekBar;
    private TextView searchEventRadiusTextView;
    private Integer scanRadius;
    private Double centerLatitude;
    private Double centerLongitude;
    private EventDAO eventDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);
        eventDAO = new EventDAO();
        filtersGridView = (GridView) findViewById(R.id.SearchEventCategoriesGridView);
        launchScanButton = (Button) findViewById(R.id.SearchEventLaunchSearchButton);
        locateCenterButton = (Button) findViewById(R.id.SearchEventLocateButton);
        selectRadiusSeekBar = (SeekBar) findViewById(R.id.SearchEventRadiusSeekBar);
        searchEventRadiusTextView = (TextView) findViewById(R.id.SearchEventRadiusTextView);
        selectRadiusSeekBar = (SeekBar) findViewById(R.id.SearchEventRadiusSeekBar);
        adapter = new EditInterestsGridViewAdapter(SearchEventActivity.this, CategoryService.getAllCategoriesArrayList());
        //!!!! Fix le bug de l'adaptateur
        filtersGridView.setAdapter(adapter);
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                String newText = getString(R.string.search_event_radius);
                newText +=" ";
                newText += progress;
                searchEventRadiusTextView.setText(newText);
                scanRadius = selectRadiusSeekBar.getProgress();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        selectRadiusSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        locateCenterButton.setOnClickListener(locateCenterClickListener());
        launchScanButton.setOnClickListener(scanButtonClickListener());
    }
    public View.OnClickListener locateCenterClickListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent goToLocateActivityIntent = new Intent(SearchEventActivity.this,LocateActivity.class);
                startActivityForResult(goToLocateActivityIntent,1);
            }
        };
        return listener;
    }
    public View.OnClickListener scanButtonClickListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(centerLatitude != null && centerLongitude != null && centerLatitude!= 0 && centerLongitude!=0)
                {
                    if(scanRadius == null || scanRadius == 0)
                    {
                        ViewStaticMethods.displayMessage(getString(R.string.search_event_no_radius_selected));
                    }
                    else
                    {
                        EventSearchDTO eventSearchDTO = new EventSearchDTO();
                        eventSearchDTO.setCenterLatitude(centerLatitude);
                        eventSearchDTO.setCenterLongitude(centerLongitude);
                        eventSearchDTO.setRadius(selectRadiusSeekBar.getProgress());
                        EventSearchPackage eventSearchPackage = new EventSearchPackage();
                        eventSearchPackage.setSender(SearchEventActivity.this);
                        eventSearchPackage.setCategoriesFilters(adapter.getCheckedCategories());
                        eventSearchPackage.setScanForm(eventSearchDTO);
                        launchScanButton.setEnabled(false);
                        eventDAO.getEventsAroundPoint(eventSearchPackage);
                    }
                }
                else
                {
                    ViewStaticMethods.displayMessage(getString(R.string.search_event_no_center_selected));
                }

            }
        };
        return listener;
    }

    public void notifyEventScanSuccess(EventSearchPackage result)
    {
        if(result.getScanResult().size() == 0)
        {
            ViewStaticMethods.displayMessage(getString(R.string.search_event_no_event_found));
        }
        else
        {
            Intent goToEventListIntent = new Intent(SearchEventActivity.this,EventListActivity.class);
            goToEventListIntent.putParcelableArrayListExtra("eventsToDisplay",result.getScanResult());
            startActivity(goToEventListIntent);
        }
        launchScanButton.setEnabled(true);
    }
    public void notifyEventScanFailure(int responseCode)
    {
        ViewStaticMethods.displayMessage(ResponseCodeChecker.getResponseCodeErrorMessage(responseCode));
        launchScanButton.setEnabled(true);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                centerLatitude = data.getDoubleExtra("chosenLatitude",0);
                centerLongitude = data.getDoubleExtra("chosenLongitude",0);
            }
            else
            {
                ViewStaticMethods.displayMessage(getString(R.string.create_event_warning_not_located));
            }
        }
    }


}
