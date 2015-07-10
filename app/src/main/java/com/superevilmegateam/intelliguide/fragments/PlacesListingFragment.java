package com.superevilmegateam.intelliguide.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import com.google.android.gms.maps.model.LatLng;
import com.superevilmegateam.intelliguide.MainActivity;
import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.adapters.PlacesListAdapter;
import com.superevilmegateam.intelliguide.database.Database;
import com.superevilmegateam.intelliguide.database.items.Category;
import com.superevilmegateam.intelliguide.database.items.Place;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PlacesListingFragment extends Fragment {

    private View rootView;
    private ListView placesList;
    private PlacesListAdapter adapter;
    private List<Place> places;
    private PlaceSelectionListener mListener;
    LatLng location;
    private View loadingBar;
    private boolean showMyPlaces = false;
    Integer distance = 0;
    private Button mapButton;

    private List<Category> categoryList;

    public PlacesListingFragment() {
        // Required empty public constructor
    }

    public static PlacesListingFragment getInstance(List<Category> categoryList, LatLng location, Integer distance, boolean showMyPlaces) {
        PlacesListingFragment fragment = new PlacesListingFragment();
        fragment.showMyPlaces = showMyPlaces;
        fragment.categoryList = categoryList;
        fragment.location = location;
        if(distance!=null)
            fragment.distance = distance;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_places_listing, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingBar = rootView.findViewById(R.id.places_listing_progress_bar);
        mapButton = (Button) rootView.findViewById(R.id.places_listing_map_btn);
        loadingBar.setVisibility(View.VISIBLE);
        loadPlacesList();
    }

    private void loadPlacesList() {
        List<String> categoriesStrings = new ArrayList<>();
        if(!showMyPlaces)
            for(Category category : categoryList) {
                categoriesStrings.add(category.getName());
            }

        Database.getInstance().getPlacesByCategories(categoriesStrings,location,distance,showMyPlaces,!((MainActivity)getActivity()).isModeratorModeOn(), new Database.PlacesRequestListener() {
            @Override
            public void onPlacesReady(List<Place> places) {
                PlacesListingFragment.this.places = new ArrayList<>(places);
                findViews();
                loadingBar.setVisibility(View.GONE);

            }
        });
    }

    private void findViews() {
        placesList = (ListView) rootView.findViewById(R.id.places_listing_list);
        prepareList();

    }

    private void prepareList() {
        adapter = new PlacesListAdapter(getActivity(),places);
        placesList.setAdapter(adapter);
        placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.onPlaceSelected(places.get(i), showMyPlaces);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_container,MapFragment.newInstance(new ArrayList<Place>(places),false))
                        .addToBackStack(null).commit();
            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (PlaceSelectionListener) activity;
        } catch (ClassCastException e) {
            Log.e("Fragment", "Could not cast to PlaceSelectionListener");
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface PlaceSelectionListener {
        void onPlaceSelected(Place place, boolean showMyPlaces);
    }
}
