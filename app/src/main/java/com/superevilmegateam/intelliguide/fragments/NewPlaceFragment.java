package com.superevilmegateam.intelliguide.fragments;


import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.superevilmegateam.intelliguide.MainActivity;
import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.adapters.CategoriesExpandableListAdapter;
import com.superevilmegateam.intelliguide.database.Database;
import com.superevilmegateam.intelliguide.database.items.Category;
import com.superevilmegateam.intelliguide.database.items.Place;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class NewPlaceFragment extends Fragment implements OnMapReadyCallback {

    private View rootView;

    private ExpandableListView categoriesSelect;
    private EditText titleEdit;
    private EditText descEdit;
    private Button submitBtn;
    private List<Category> categories;
    private GoogleMap map;
    private CategoriesExpandableListAdapter adapter;
    private Place place;
    private LatLng location;
    public NewPlaceFragment() {
        // Required empty public constructor
    }

    public static NewPlaceFragment getInstance(Place place) {
        NewPlaceFragment fragment = new NewPlaceFragment();
        fragment.place = place;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_new_place, container, false);

        categoriesSelect = (ExpandableListView) rootView.findViewById(R.id.new_place_categories_list);
        adapter = new CategoriesExpandableListAdapter(getActivity(), new ArrayList<Category>());
        categoriesSelect.setAdapter(adapter);



        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.new_place_map_container, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        Log.e("NewPlace","MapREady!");


        if(place!=null && place.getLocation()!=null) {
            Log.e("Place",place.getLocation().latitude+" "+place.getLocation().longitude);
            this.location = place.getLocation();
            showLocation(this.location);
        } else {
            setMyCurrentLocation();
        }


        map.setMyLocationEnabled(true);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (place != null) place.setLocation(latLng);
                NewPlaceFragment.this.location = latLng;
                NewPlaceFragment.this.map.setMyLocationEnabled(true);
                NewPlaceFragment.this.map.clear();
                NewPlaceFragment.this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                NewPlaceFragment.this.map.addMarker(new MarkerOptions().position(latLng));
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
        loadValues();
    }

    private void findViews() {

        titleEdit = (EditText) rootView.findViewById(R.id.new_place_title);
        descEdit = (EditText) rootView.findViewById(R.id.new_place_desc);

        submitBtn = (Button) rootView.findViewById(R.id.new_place_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!titleEdit.getText().toString().isEmpty()
                        && !descEdit.getText().toString().isEmpty()
                        && adapter.getSelectedCategory() != null) {
                    if (place == null) {
                        Database.getInstance().addNewPlace(titleEdit.getText().toString(),
                                descEdit.getText().toString(), adapter.getSelectedCategory(), location, new Database.NewObjectListener() {
                                    @Override
                                    public void onSaveComplete() {
                                        Toast.makeText(getActivity(), "Dodano pomyślnie", Toast.LENGTH_LONG).show();
                                        ((MainActivity) getActivity()).onBackPressed();
                                    }
                                });
                    } else {
                        Database.getInstance().saveModifiedPlace(place, titleEdit.getText().toString(),
                                descEdit.getText().toString(), adapter.getSelectedCategory(), new Database.NewObjectListener() {
                                    @Override
                                    public void onSaveComplete() {
                                        Toast.makeText(getActivity(), "Dodano pomyślnie", Toast.LENGTH_LONG).show();
                                        ((MainActivity) getActivity()).onBackPressed();
                                    }
                                });
                    }
                } else {
                    Toast.makeText(getActivity(), "Wszystkie pola są wymagane", Toast.LENGTH_LONG).show();
                }
            }
        });

        Log.e("DUPA", "DUPA");

        Database.getInstance().getCategories(new Database.CategoriesRequestListener() {
            @Override
            public void onCategoriesReady(List<Category> categories) {
                NewPlaceFragment.this.categories = new ArrayList<Category>(categories);
                categoriesSelect = (ExpandableListView) rootView.findViewById(R.id.new_place_categories_list);
                adapter = new CategoriesExpandableListAdapter(getActivity(), new ArrayList<Category>(NewPlaceFragment.this.categories));
                categoriesSelect.setAdapter(adapter);
                categoriesSelect.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                        adapter.setSelectedCategory(NewPlaceFragment.this.categories.get(i2));
                        adapter.notifyDataSetChanged();
                        expandableListView.collapseGroup(0);
                        return true;
                    }
                });

            }
        });
    }

    private void loadValues() {
        if(place!=null) {
            titleEdit.setText(place.getName());
            descEdit.setText(place.getDescription());
            location = place.getLocation();
        }
    }

    private void setMyCurrentLocation(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(provider);

        if(location==null && MainActivity.curLocation!=null)
            location = MainActivity.curLocation;

        if (location!=null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            this.location = latLng;

        } else
            this.location = new LatLng(0,0);
        showLocation(this.location);
    }

    private void showLocation(LatLng location) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
        map.addMarker(new MarkerOptions().position(location));
    }


}
