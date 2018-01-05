package adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicol.joynus.R;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.Event;
import service.EventService;

public class EventListAdapter extends BaseAdapter
{
    protected Activity sender;
    protected ArrayList<Event> eventsToDisplay;
    public EventListAdapter(ArrayList<Event> eventsToDisplay,Activity sender)
    {
        super();
        this.eventsToDisplay = eventsToDisplay;
        this.sender = sender;
    }
    @Override
    public int getCount()
    {
        return eventsToDisplay.size();
    }
    @Override
    public Event getItem(int position)
    {
        return eventsToDisplay.get(position);
    }
    @Override
    public long getItemId(int position)
    {
        return 0;
    }
    private class EventListViewHolder
    {
        private TextView titleView;
        private TextView addressView;
        private TextView dateView;

        public TextView getTitleView() {
            return titleView;
        }

        public void setTitleView(TextView titleView) {
            this.titleView = titleView;
        }

        public TextView getAddressView() {
            return addressView;
        }

        public void setAddressView(TextView addressView) {
            this.addressView = addressView;
        }

        public TextView getDateView() {
            return dateView;
        }

        public void setDateView(TextView dateView) {
            this.dateView = dateView;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        EventListViewHolder view;
        LayoutInflater inflater = sender.getLayoutInflater();
        if(convertView == null)
        {
            view = new EventListViewHolder();
            convertView = inflater.inflate(R.layout.event_list_row,null);
            view.setTitleView((TextView) convertView.findViewById(R.id.event_list_row_title));
            view.setAddressView((TextView) convertView.findViewById(R.id.event_list_row_address));
            view.setDateView((TextView)convertView.findViewById(R.id.event_list_row_date));
            convertView.setTag(view);
        }
        else
        {
            view = (EventListViewHolder) convertView.getTag();
        }
        view.getTitleView().setText(eventsToDisplay.get(position).getTitle());
        view.getAddressView().setText(eventsToDisplay.get(position).getAddress());
        view.getDateView().setText(EventService.createDateStringFromDate(eventsToDisplay.get(position).getDate()));
        return convertView;
    }


    public Activity getSender() {
        return sender;
    }

    public void setSender(Activity sender) {
        this.sender = sender;
    }

    public ArrayList<Event> getEventsToDisplay() {
        return eventsToDisplay;
    }

    public void setEventsToDisplay(ArrayList<Event> eventsToDisplay) {
        this.eventsToDisplay = eventsToDisplay;
    }
}
