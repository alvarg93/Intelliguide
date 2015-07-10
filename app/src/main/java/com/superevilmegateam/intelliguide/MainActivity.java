package com.superevilmegateam.intelliguide;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.superevilmegateam.intelliguide.database.Database;
import com.superevilmegateam.intelliguide.database.items.Category;
import com.superevilmegateam.intelliguide.database.items.MyUser;
import com.superevilmegateam.intelliguide.database.items.Place;
import com.superevilmegateam.intelliguide.database.items.Review;
import com.superevilmegateam.intelliguide.fragments.AddReviewFragment;
import com.superevilmegateam.intelliguide.fragments.HomeScreenFragment;
import com.superevilmegateam.intelliguide.fragments.LoginFormFragment;
import com.superevilmegateam.intelliguide.fragments.LoginFragment;
import com.superevilmegateam.intelliguide.fragments.NewPlaceFragment;
import com.superevilmegateam.intelliguide.fragments.PlaceDetailsFragment;
import com.superevilmegateam.intelliguide.fragments.PlaceDetailsReviewsFragment;
import com.superevilmegateam.intelliguide.fragments.PlacesListingFragment;
import com.superevilmegateam.intelliguide.fragments.ProfileFragment;
import com.superevilmegateam.intelliguide.fragments.RegisterFormFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements HomeScreenFragment.OnSearchClickListener,
        PlacesListingFragment.PlaceSelectionListener,
        AddReviewFragment.AddReviewListener,
        PlaceDetailsReviewsFragment.ReviewListListener,
        ProfileFragment.ProfileFragmentListener,
        LoginFormFragment.OnLoginFormListener,
        RegisterFormFragment.OnRegisterFormListener,
        PlaceDetailsFragment.PlaceDetailsListener,
        LocationListener{

    Database database = Database.getInstance();
    private ActionBar actionBar;

    public static Location curLocation;

    private Place selectedPlace;
    public boolean editMode = false;
    private Place placeToEdit;
    private boolean exit = false;

    private static final int VIEW_MAIN = 0;
    private static final int VIEW_PLACES_LIST = 1;
    private static final int VIEW_PLACE_DETAILS = 2;
    private static final int VIEW_NEW_PLACE = 3;
    private static final int VIEW_ADD_REVIEW = 4;
    private static final int VIEW_LOGIN = 5;
    private static final int VIEW_PROFILE = 6;

    private static final String VIEW_MAIN_NAME = "view_main";
    private static final String VIEW_PLACES_LIST_NAME = "places_list";
    private static final String VIEW_PLACE_DETAILS_NAME = "place_details";
    private static final String VIEW_NEW_PLACE_NAME = "place_name";
    private static final String VIEW_ADD_REVIEW_NAME = "add_review";
    private static final String VIEW_LOGIN_NAME = "login";
    private static final String VIEW_PROFILE_NAME = "profile";

    private boolean moderatorModeOn = false;
    private boolean showMyPlaces = false;
    private MenuItem addIcon;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bcg));
        initUIL();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
                50, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        addIcon = menu.findItem(R.id.new_place);
        checkRole();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkRole();
        showHomeScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkRole();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.e("MENU", "" + id);
        switch (id) {
            case android.R.id.home:
                this.onBackPressed();
                break;
            case R.id.new_place:
                showView(VIEW_NEW_PLACE);
                break;
            case R.id.user:
                if (MyUser.getCurrentUser() == null) {
                    showView(VIEW_LOGIN);
                } else {
                    showView(VIEW_PROFILE);
                }
                break;
        }
        return true;
    }

    private void initUIL() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(configuration);
    }

    private void showHomeScreen() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(false);
        ft.replace(R.id.main_fragment_container, new HomeScreenFragment(), FragmentTagHelper.HOME_SCREEN);
        ft.commit();
    }

    private void showView(int viewId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (viewId) {
            case VIEW_MAIN:
                actionBar.setTitle(R.string.app_name);
                ft.replace(R.id.main_fragment_container, new HomeScreenFragment(),  FragmentTagHelper.HOME_SCREEN);
                ft.addToBackStack(VIEW_MAIN_NAME);
                break;
            case VIEW_PLACES_LIST:
                actionBar.setTitle("Atrakcje");
                ft.replace(R.id.main_fragment_container, PlacesListingFragment.getInstance(selectedCategories, queryLocation, queryDistance, showMyPlaces),  FragmentTagHelper.PLACES_LIST);
                ft.addToBackStack(VIEW_PLACES_LIST_NAME);
                actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            case VIEW_PLACE_DETAILS:
                actionBar.setTitle("Szczegóły");
                ft.replace(R.id.main_fragment_container, PlaceDetailsFragment.getInstance(selectedPlace, editMode), FragmentTagHelper.PLACE_DETAILS);
                ft.addToBackStack(VIEW_PLACE_DETAILS_NAME);
                actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            case VIEW_NEW_PLACE:
                actionBar.setTitle("Nowe miejsce");
                ft.replace(R.id.main_fragment_container, NewPlaceFragment.getInstance(placeToEdit),FragmentTagHelper.NEW_PLACE);
                placeToEdit = null;
                ft.addToBackStack(VIEW_NEW_PLACE_NAME);
                actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            case VIEW_ADD_REVIEW:
                break;
            case VIEW_LOGIN:
                actionBar.setTitle("Zaloguj się");
                ft.replace(R.id.main_fragment_container, new LoginFragment(),FragmentTagHelper.LOGIN);
                ft.addToBackStack(VIEW_LOGIN_NAME);
                actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            case VIEW_PROFILE:
                actionBar.setTitle("Twoje konto");
                ft.replace(R.id.main_fragment_container, new ProfileFragment(),FragmentTagHelper.PROFILE);
                ft.addToBackStack(VIEW_PROFILE_NAME);
                actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            default:
                ft.replace(R.id.main_fragment_container, new HomeScreenFragment(), FragmentTagHelper.HOME_SCREEN);
                ft.addToBackStack(VIEW_MAIN_NAME);
                actionBar.setDisplayHomeAsUpEnabled(false);

        }
        ft.commit();
    }

    private List<Category> selectedCategories = new ArrayList<>();

    @Override
    public void onPlaceSelected(Place selectedPlace, boolean showMyPlaces) {
        this.selectedPlace = selectedPlace;
        this.editMode = showMyPlaces;
        showView(VIEW_PLACE_DETAILS);
    }

    LatLng queryLocation;
    Integer queryDistance;
    @Override
    public void onSearchClick(List<Category> selectedCategories, @Nullable LatLng location, @Nullable Integer distance) {
        this.selectedCategories = selectedCategories;
        this.queryLocation = location;
        this.queryDistance = distance;
        showView(VIEW_PLACES_LIST);
    }

    @Override
    public void onBackPressed() {


        if (getSupportFragmentManager().getBackStackEntryCount() == 0 && !exit) {
            Toast.makeText(this, "Naciśnij ponownie aby zamknąć aplikację", Toast.LENGTH_SHORT).show();
            exit = true;
        } else {

            if (getSupportFragmentManager().getBackStackEntryCount() >= 2) {
                switch (getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 2).getName()) {
                    case VIEW_MAIN_NAME:
                        actionBar.setDisplayHomeAsUpEnabled(false);
                        actionBar.setTitle(R.string.app_name);
                        break;
                    case VIEW_PLACES_LIST_NAME:
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setTitle("Atrakcje");
                        break;
                    case VIEW_PLACE_DETAILS_NAME:
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setTitle("Szczegóły");
                        onBackPressed();
                        break;
                    case VIEW_NEW_PLACE_NAME:
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setTitle("Nowe miejsce");
                        break;
                    case VIEW_ADD_REVIEW_NAME:
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setTitle("Dodaj recenzję");
                        break;
                    case VIEW_LOGIN_NAME:
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setTitle("Zaloguj się");
                        break;
                    case VIEW_PROFILE_NAME:
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setTitle("Twój profil");
                        break;
                }
            } else {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setTitle(R.string.app_name);
            }

            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                super.onBackPressed();
            }

        }


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                exit = false;
            }
        }, 3000);
    }

    public boolean isModeratorModeOn() {
        return moderatorModeOn;
    }

    public void setModeratorModeOn(boolean moderatorModeOn) {
        this.moderatorModeOn = moderatorModeOn;
    }

    @Override
    public void onSubmitReview(final Place place, final Review review) {
        database.addReview(review, new Database.NewObjectListener() {
            @Override
            public void onSaveComplete() {
                place.getReviews().add(review);
                Toast.makeText(MainActivity.this, "Recenzja pomyślnie zapisana", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    @Override
    public void onAddReviewPress(Place place) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        actionBar.setTitle("Dodaj recenzję");
        ft.replace(R.id.main_fragment_container, AddReviewFragment.newInstance(place));
        ft.addToBackStack(VIEW_ADD_REVIEW_NAME);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ft.commit();
    }

    @Override
    public void onLogin(String username, String password) {
        database.signIn(username, password, new Database.SignInListener() {
            @Override
            public void onSuccess() {
                checkRole();
                onBackPressed();
            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, "Nieudane logowanie", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFacebookLogin() {

    }

    @Override
    public void onFacebookRegister() {

    }

    @Override
    public void onRegister(String username, String email, String pass) {
        database.signUpUser(username, email, pass, new Database.SignInListener() {
            @Override
            public void onSuccess() {
                checkRole();
                onBackPressed();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onLogoutPress() {
        database.logOut();
        onBackPressed();
    }

    @Override
    public void onMyPlacesPress() {
        showMyPlaces = true;
        showView(VIEW_PLACES_LIST);
        showMyPlaces = false;
    }

    private void checkRole() {
        if (MyUser.getCurrentUser() != null) {
            if (MyUser.getCurrentUser().getString("role").equals("moderator")) {
                setModeratorModeOn(true);
                if (addIcon != null)
                    addIcon.setVisible(true);
            } else {
                setModeratorModeOn(false);
                if (MyUser.getCurrentUser().getString("role").equals("redactor")) {

                    if (addIcon != null)
                        addIcon.setVisible(true);
                } else {

                    if (addIcon != null)
                        addIcon.setVisible(false);
                }
            }
        } else {
            setModeratorModeOn(false);

            if(addIcon!=null)
            addIcon.setVisible(false);
        }
    }

    @Override
    public void onEditBtnPress(Place place) {
        placeToEdit = place;
        showView(VIEW_NEW_PLACE);
    }

    @Override
    public void onLocationChanged(Location location) {
        curLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
