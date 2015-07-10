package com.superevilmegateam.intelliguide.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.superevilmegateam.intelliguide.database.items.Place;
import com.superevilmegateam.intelliguide.fragments.PhotoGridFragment;
import com.superevilmegateam.intelliguide.fragments.PlaceDetailsDescriptionFragment;
import com.superevilmegateam.intelliguide.fragments.MapFragment;
import com.superevilmegateam.intelliguide.fragments.PlaceDetailsReviewsFragment;

import java.util.ArrayList;


/**
 * Created by Lukasz on 2015-03-03.
 */


public class PlaceDetailsPagerAdapter extends FragmentPagerAdapter {

    private Fragment cntx;
    private View.OnClickListener onClickListener;
    private PlaceDetailsDescriptionFragment descriptionFragment;
    private PlaceDetailsReviewsFragment reviewsFragment;
    private PhotoGridFragment galleryFragment;
    private MapFragment mapFragment;
    private Place place;

    public PlaceDetailsPagerAdapter(Fragment cntx, Place place) {
        super(cntx.getChildFragmentManager());
        this.cntx = cntx;
        this.place = place;
        descriptionFragment = PlaceDetailsDescriptionFragment.getInstance(place.getDescription());
        reviewsFragment = PlaceDetailsReviewsFragment.getInstance(place);
        galleryFragment = PhotoGridFragment.newInstance(place.getGallery());
        ArrayList<Place> places = new ArrayList<Place>();
        places.add(place);
        mapFragment = MapFragment.newInstance(places,true);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0: return descriptionFragment;
            case 1: return reviewsFragment;
            case 2: return galleryFragment;
            case 3: return mapFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}