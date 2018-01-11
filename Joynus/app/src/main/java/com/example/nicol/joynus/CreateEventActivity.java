package com.example.nicol.joynus;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import adapters.EditInterestsGridViewAdapter;
import dao.EventDAO;
import dtomodels.eventDTO.EventCreationDTO;
import model.Category;
import service.CategoryService;
import taskmodels.CreateEventPackage;
import utility.ResponseCodeChecker;

public class CreateEventActivity extends BaseActivity {

    private EditText eventTitleInput;
    private Button locateEventButton;
    private Calendar chosenDate;
    private EditText editDate;
    private EditText editHour;
    private EditText descriptionInput;
    private GridView categoriesGridView;
    private EditInterestsGridViewAdapter adapter;
    private EditText facebookUrlInput;
    private TextView addressTextView;
    private Button createEventButton;
    private String eventTitle;
    private Double eventLatitude;
    private Double eventLongitude;
    private Context context = this;
    private String dateFormat = "dd.MM.yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.FRENCH);
    private Date eventDateTime;
    private String eventDescription;
    private ArrayList<Category> eventCategories;
    private String eventFacebookLink;
    private String eventAddress;
    private EventDAO eventDAO;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        eventDAO = new EventDAO();
        eventTitleInput = (EditText)findViewById(R.id.CreateEventTitleInput);
        locateEventButton = (Button)findViewById(R.id.CreateEventPlaceInput);
        editDate = (EditText)findViewById(R.id.CreateEventDateInput);
        editHour = (EditText)findViewById(R.id.CreateEventHourInput);
        descriptionInput = (EditText)findViewById(R.id.CreateEventDescriptionInput);
        categoriesGridView = (GridView)findViewById(R.id.CreateEventCategoriesGridView);
        facebookUrlInput = (EditText)findViewById(R.id.CreateEventFacebookLinkInput);
        addressTextView = (TextView)findViewById(R.id.CreateEventAddressCheckFromParent);
        createEventButton = (Button)findViewById(R.id.CreateEventCreateButton);
        adapter = new EditInterestsGridViewAdapter(CreateEventActivity.this, CategoryService.getAllCategoriesArrayList());
        categoriesGridView.setAdapter(adapter);
        createEventButton.setOnClickListener(createEventListener());
        chosenDate = Calendar.getInstance();
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        editDate.setText(dateString);

        dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                chosenDate.set(Calendar.YEAR,year);
                chosenDate.set(Calendar.MONTH,monthOfYear);
                chosenDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                editDate.setText(sdf.format(chosenDate.getTime()));
            }
        };

        editDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(context,dateSetListener, chosenDate.get(Calendar.YEAR), chosenDate.get(Calendar.MONTH), chosenDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1)
            {
                chosenDate.set(Calendar.HOUR_OF_DAY,i);
                chosenDate.set(Calendar.MINUTE,i1);
                String newText = chosenDate.get(Calendar.HOUR_OF_DAY)+"h"+chosenDate.get(Calendar.MINUTE);
                editHour.setText(newText);
            }
        };
        editHour.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new TimePickerDialog(context,timeSetListener,chosenDate.get(Calendar.HOUR_OF_DAY),chosenDate.get(Calendar.MINUTE), true).show();
            }
        });

        locateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLocateActivityIntent = new Intent(CreateEventActivity.this,LocateActivity.class);
                startActivityForResult(goToLocateActivityIntent,1);
            }
        });
    }
    public View.OnClickListener createEventListener()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createEventButton.setEnabled(false);
                createEvent();
            }
        };
        return listener;
    }
    public void createEvent()
    {
        eventTitle = eventTitleInput.getText().toString();
        eventCategories = adapter.getCheckedCategories();
        eventDescription = descriptionInput.getText().toString();
        int eventYear = chosenDate.get(Calendar.YEAR);
        int eventMonth = chosenDate.get(Calendar.MONTH);
        int day = chosenDate.get(Calendar.DAY_OF_MONTH);
        int hour = chosenDate.get(Calendar.HOUR_OF_DAY);
        int minute = chosenDate.get(Calendar.MINUTE);
        eventDateTime = new Date(eventYear-1900,eventMonth,day,hour,minute);
        eventFacebookLink = facebookUrlInput.getText().toString();
        if(isFormCorrect())
        {
            EventCreationDTO formToSend = new EventCreationDTO();
            formToSend.setAddress(eventAddress);
            ArrayList<Long> categoriesId = new ArrayList<Long>();
            for(Category eventCategory : eventCategories)
            {
                categoriesId.add((long)eventCategory.getDbId());
            }
            formToSend.setCategoriesId(categoriesId);
            formToSend.setCreatorUsername(LoginActivity.getCurrentApplicationUser().getUsername());
            formToSend.setDate(eventDateTime);
            formToSend.setDescription(eventDescription);
            formToSend.setFacebookUrl(eventFacebookLink);
            formToSend.setLatitude(eventLatitude);
            formToSend.setLongitude(eventLongitude);
            ArrayList<Long> tagsId = new ArrayList<Long>();
            tagsId.add((long)1);
            formToSend.setTagsId(tagsId);
            formToSend.setTitle(eventTitle);
            CreateEventPackage createEventPackage = new CreateEventPackage();
            createEventPackage.setForm(formToSend);
            createEventPackage.setSender(CreateEventActivity.this);
            eventDAO.createEvent(createEventPackage);
        }
    }
    public boolean isFormCorrect()
    {
        if(titleCheck())
        {
            if(longitudeAndLatitudeCheck())
            {
                if(eventDateTimeCheck())
                {
                    if(descriptionCheck())
                    {
                        if(categoriesCheck())
                        {
                            if(facebookLinkCheck())
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        createEventButton.setEnabled(true);
        return false;
    }

    public boolean titleCheck()
    {
        if(eventTitle.length()<4 || eventTitle.length()>80)
        {
            ViewStaticMethods.displayMessage(getString(R.string.create_event_error_title_length));
            return false;
        }
        return true;
    }

    public boolean longitudeAndLatitudeCheck()
    {
        if(eventLongitude == null||eventLatitude == null ||eventLongitude == 0||eventLatitude == 0)
        {
            ViewStaticMethods.displayMessage(getString(R.string.create_event_error_not_located));
            return false;
        }
        return true;
    }

    public boolean eventDateTimeCheck()
    {
        Date now = new Date();
        if(!now.before(eventDateTime))
        {
            ViewStaticMethods.displayMessage(getString(R.string.create_event_error_past_date));
            return false;
        }
        if((eventDateTime.getYear() - now.getYear()) > 3)
        {
            ViewStaticMethods.displayMessage(getString(R.string.create_event_error_future_date));
            return false;
        }

        return true;
    }
    public boolean descriptionCheck()
    {
        if(eventDescription.equals(""))
        {
            return true;
        }
        if(eventDescription.length()<10 || eventDescription.length()>1500)
        {
            ViewStaticMethods.displayMessage(getString(R.string.create_event_error_description_length));
            return false;
        }
        return true;
    }

    public boolean categoriesCheck()
    {
        if(eventCategories.size() == 0)
        {
            ViewStaticMethods.displayMessage(getString(R.string.create_event_error_no_category));
            return false;
        }
        return true;
    }

    public boolean facebookLinkCheck()
    {
        if(eventFacebookLink.equals(""))
        {
            return true;
        }
        String regex = "((http|https)://)?(www[.])?facebook.com/.+";
        if(!eventFacebookLink.matches(regex))
        {
            ViewStaticMethods.displayMessage(getString(R.string.create_event_error_bad_facebook_link));
            return false;
        }
        return true;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                 eventLatitude = data.getDoubleExtra("chosenLatitude",0);
                 eventLongitude = data.getDoubleExtra("chosenLongitude",0);
                 eventAddress = data.getStringExtra("chosenAddress");
                 addressTextView.setText(eventAddress);
            }
            else
            {
                ViewStaticMethods.displayMessage(getString(R.string.create_event_warning_not_located));
            }
        }
    }

    public void notifyCreationSuccess()
    {
        ViewStaticMethods.displayMessage(getString(R.string.create_event_success));
        finish();
    }

    public void notifyCreationFailure(int responseCode)
    {
        String message = ResponseCodeChecker.getResponseCodeErrorMessage(responseCode);
        ViewStaticMethods.displayMessage(message);
        createEventButton.setEnabled(true);
    }

}
