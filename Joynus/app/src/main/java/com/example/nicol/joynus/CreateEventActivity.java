package com.example.nicol.joynus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Calendar;

import adapters.EditInterestsGridViewAdapter;

public class CreateEventActivity extends BaseActivity {

    private EditText eventTitleInput;
    private Button locateEventButton;
    private Calendar selectedDate;
    private EditText editDate;
    private EditText editHour;
    private EditText Description;
    private GridView categoriesGridView;
    private EditInterestsGridViewAdapter adapter;
    private EditText facebookUrlInput;
    private TextView addressTextView;
    private Button createEventView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

    }

}
