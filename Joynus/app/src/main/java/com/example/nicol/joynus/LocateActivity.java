package com.example.nicol.joynus;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class LocateActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap displayedMap;
    private Double selectedPositionLatitude;
    private Double selectedPositionLongitude;
    private String displayedAddress;
    private double mapCenterLongitude;
    private double mapCenterLatitude;
    private Button locateButton;
    private Geocoder geo;
    private Marker lastMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);
        locateButton = (Button) findViewById(R.id.locateEventMapButton);
        mapCenterLongitude = 4.859927;
        mapCenterLatitude = 50.466649;
        displayedAddress = getString(R.string.locate_event_no_address);
        geo = new Geocoder(LocateActivity.this.getApplicationContext(), Locale.getDefault());
        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedPositionLatitude!=null && selectedPositionLongitude!=null)
                {
                    Intent backToCreateEventIntent = new Intent();
                    backToCreateEventIntent.putExtra("chosenLatitude",selectedPositionLatitude);
                    backToCreateEventIntent.putExtra("chosenLongitude",selectedPositionLongitude);
                    backToCreateEventIntent.putExtra("chosenAddress",displayedAddress);
                    setResult(RESULT_OK, backToCreateEventIntent);
                    finish();
                }
                else
                {
                    ViewStaticMethods.displayMessage(getString(R.string.locate_event_error_no_location_selected));
                }
            }
        });
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap map) {

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mapCenterLatitude, mapCenterLongitude), 12));
        displayedMap = map;
        displayedMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selectedPositionLatitude = latLng.latitude;
                selectedPositionLongitude = latLng.longitude;
                try
                {
                    List<Address> addresses = geo.getFromLocation(selectedPositionLatitude, selectedPositionLongitude, 1);
                    if(addresses.isEmpty())
                    {
                        displayedAddress = getString(R.string.locate_event_no_address);
                    }
                    else
                    {
                        if(addresses.size() > 0)
                        {
                            displayedAddress ="";
                            displayedAddress += addresses.get(0).getAddressLine(0);
                            displayedAddress +=",";
                            displayedAddress += addresses.get(0).getPostalCode();
                            displayedAddress += " ";
                            displayedAddress += addresses.get(0).getLocality();
                        }
                        else
                        {
                            displayedAddress = getString(R.string.locate_event_no_address);
                        }
                    }
                    if(lastMarker != null)
                    {
                        lastMarker.remove();
                    }
                    lastMarker = displayedMap.addMarker(new MarkerOptions()
                            .position(new LatLng(selectedPositionLatitude, selectedPositionLongitude))
                            .title(displayedAddress));
                }
                catch(Exception e)
                {
                    displayedAddress = getString(R.string.locate_event_no_address);
                    if(lastMarker != null)
                    {
                        lastMarker.remove();
                    }
                    lastMarker = displayedMap.addMarker(new MarkerOptions()
                            .position(new LatLng(selectedPositionLatitude, selectedPositionLongitude))
                            .title(displayedAddress));
                }
            }
        });

    }
}
