package com.superevilmegateam.intelliguide.utils;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.superevilmegateam.intelliguide.MainActivity;

import java.io.IOException;
import java.util.List;

import javax.security.auth.callback.Callback;

/**
 * Created by Lukasz on 2015-06-17.
 */
public class LocationManager {

    static LocationManager instance;
    GoogleApiClient googleApiClient;
    Context cntx;
    Location lastLocation;

    public static LocationManager getInstance(Context cntx) {
        if(instance == null) {
            instance = new LocationManager();
        }
        instance.cntx = cntx;
        return instance;
    }


    public LatLng getMyCurrentLocation(){
        LatLng resultLocation;

        android.location.LocationManager locationManager = (android.location.LocationManager) cntx.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(provider);

        if(location==null && MainActivity.curLocation!=null)
            location = MainActivity.curLocation;

        if (location!=null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            resultLocation = latLng;

        } else
            resultLocation = new LatLng(0,0);

        return resultLocation;
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(cntx);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng((int) (location.getLatitude() * 1E6),
                    (int) (location.getLongitude() * 1E6));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
            return p1;
        }
    }
}





