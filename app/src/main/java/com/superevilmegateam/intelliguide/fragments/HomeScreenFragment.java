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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.google.android.gms.maps.model.LatLng;
import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.adapters.CategoriesListAdapter;
import com.superevilmegateam.intelliguide.database.Database;
import com.superevilmegateam.intelliguide.database.items.Category;
import com.superevilmegateam.intelliguide.utils.LocationManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment {

    private View rootView;
    private ListView categoriesList;
    private CategoriesListAdapter adapter;
    private List<Category> categories;
    private Button searchBtn;
    private Switch myLocSwitch;
    private EditText locEdit;
    private EditText distEdit;

    private OnSearchClickListener mListener;

    public HomeScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootView = inflater.inflate(R.layout.fragment_home_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
    }

    private void findViews() {
        categoriesList = (ListView) rootView.findViewById(R.id.main_screen_category_list);
        Database.getInstance().getCategories(new Database.CategoriesRequestListener() {
            @Override
            public void onCategoriesReady(List<Category> categories) {
                prepareList(categories);
            }
        });

        searchBtn = (Button) rootView.findViewById(R.id.main_screen_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Category> selectedCategories = new ArrayList<Category>();
                for(Category category : categories) {
                    if(category.isSelected()) selectedCategories.add(category);
                }
                LatLng location = null;
                Integer distance = null;
                if(myLocSwitch.isChecked()) {
                    location = LocationManager.getInstance(getActivity()).getMyCurrentLocation();
                    try {
                        distance = Integer.parseInt(distEdit.getText().toString());
                    } catch (Exception e) {
                        distance= 100;
                    }
                }
                else if(!locEdit.getText().toString().isEmpty()) {
                    location = LocationManager.getInstance(getActivity()).getLocationFromAddress(locEdit.getText().toString());
                    try {
                        distance = Integer.parseInt(distEdit.getText().toString());
                    } catch (Exception e) {
                        distance= 100;
                    }
                }

                mListener.onSearchClick(selectedCategories, location,distance);
            }
        });

        locEdit = (EditText) rootView.findViewById(R.id.main_screen_loc_edit);
        distEdit = (EditText) rootView.findViewById(R.id.main_screen_dist_edit);
        myLocSwitch = (Switch) rootView.findViewById(R.id.main_screen_loc_switch);
        myLocSwitch.setTextOff("Nie");
        myLocSwitch.setTextOn("Tak");

    }

    private void prepareList(List<Category> fetchedCategories) {
        categories = new ArrayList<>(fetchedCategories);
        adapter = new CategoriesListAdapter(getActivity(),categories);
        categoriesList.setAdapter(adapter);
        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categories.get(i).setSelected(!categories.get(i).isSelected());
                Switch check = (Switch)view.findViewById(R.id.category_item_switch);
                check.setChecked(!check.isChecked());
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSearchClickListener) activity;
        } catch (ClassCastException e) {
            Log.e("Fragment", "Could not cast to OnSearchClickListener");
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSearchClickListener {
        public void onSearchClick(List<Category> selectedCategories, @Nullable LatLng location, @Nullable Integer distance);
    }
}
