package com.example.nicol.joynus;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowEventOnMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private double eventLongitude;
    private double eventLatitude;
    private String eventTitle;
    private GoogleMap displayedMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_on_map);
        eventLongitude = getIntent().getExtras().getDouble("eventLongitude",0);
        eventLatitude = getIntent().getExtras().getDouble("eventLatitude",0);
        eventTitle = getIntent().getExtras().getString("eventTitle");
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.displayedMap = map;
        displayedMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        displayedMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(eventLatitude, eventLongitude), 12));
        displayedMap.addMarker(new MarkerOptions()
                .position(new LatLng(eventLatitude, eventLongitude))
                .title(eventTitle));
    }
    }
