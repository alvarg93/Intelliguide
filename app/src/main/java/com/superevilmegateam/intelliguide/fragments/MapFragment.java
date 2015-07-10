package com.superevilmegateam.intelliguide.fragments;


import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.superevilmegateam.intelliguide.MainActivity;
import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.database.items.Place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private View rootView;
    private GoogleMap map;
    private ArrayList<Place> places;
    private boolean singleMode = false;
    private HashMap<Marker, Place> markerPlaceMap;
    LatLngBounds.Builder builder;
    com.google.android.gms.maps.SupportMapFragment mapFragment;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(ArrayList<Place> places, boolean singleMode) {
        MapFragment fragment = new MapFragment();
        fragment.markerPlaceMap = new HashMap<>();
        fragment.places = places;
        fragment.singleMode = singleMode;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_place_details_map, container, false);


        mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_container, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        Log.e("MapFragment","MapREady!");

        builder = new LatLngBounds.Builder();
        for(int i=0;i<places.size();i++) {
            showLocation(places.get(i));
        }
        LatLngBounds bounds = builder.build();
        int padding = 120; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cu);

        map.setMyLocationEnabled(!singleMode);
        if(!singleMode)
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_container,
                                PlaceDetailsFragment.getInstance(markerPlaceMap.get(marker),((MainActivity)getActivity()).editMode))
                        .addToBackStack(null).commit();
                return true;
            }
        });



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showLocation(Place place) {
        LatLng location = place.getLocation();
        Log.e("Place", place.getName());
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
        Marker marker = map.addMarker(new MarkerOptions().position(location).title(place.getName()));
        markerPlaceMap.put(marker,place);
        builder.include(marker.getPosition());
    }


}
