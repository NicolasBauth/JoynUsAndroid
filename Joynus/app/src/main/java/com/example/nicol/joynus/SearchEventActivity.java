package com.example.nicol.joynus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import adapters.EditInterestsGridViewAdapter;
import model.User;

public class SearchEventActivity extends BaseActivity {

    private GridView filtersGridView;
    private EditInterestsGridViewAdapter adapter;
    private Button launchScanButton;
    private Button locateCenterButton;
    private SeekBar selectRadiusSeekBar;
    private TextView searchEventRadiusTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);
        filtersGridView = (GridView) findViewById(R.id.SearchEventCategoriesGridView);
        launchScanButton = (Button) findViewById(R.id.SearchEventLaunchSearchButton);
        locateCenterButton = (Button) findViewById(R.id.SearchEventLocateButton);
        selectRadiusSeekBar = (SeekBar) findViewById(R.id.SearchEventRadiusSeekBar);
        searchEventRadiusTextView = (TextView) findViewById(R.id.SearchEventRadiusTextView);
        adapter = new EditInterestsGridViewAdapter(this,LoginActivity.getCurrentApplicationUser().getInterests());
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
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        selectRadiusSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }


}
