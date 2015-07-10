package com.superevilmegateam.intelliguide.database;

import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.superevilmegateam.intelliguide.database.items.Category;
import com.superevilmegateam.intelliguide.database.items.MyUser;
import com.superevilmegateam.intelliguide.database.items.Place;
import com.superevilmegateam.intelliguide.database.items.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a singleton class used to access Parse database.
 * To get instance of this class use getInstance()
 *
 * @author Jan Badura
 */
public class Database {

    private static Database instance = null;
    private List<Place> places;
    private List<Review> reviews;
    private List<Category> categories = new ArrayList<>();

    protected Database() {
    }

    /**
     * Use this method instead of constructor.
     *
     * @return instance of Database class
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * This method returns list of all Categories stored on Parse.
     * Uses CategoriesRequestListener to return data.
     *
     * @param listener
     */
    public void getCategories(final CategoriesRequestListener listener) {
        if (categories.isEmpty()) {
            ParseQuery<Category> query = ParseQuery.getQuery(Category.class);
            query.findInBackground(new FindCallback<Category>() {
                @Override
                public void done(List<Category> cat, ParseException error) {
                    if (error == null) {
                        categories = cat;

                        for (Category category : categories) {
                            ParseFile fileObject = (ParseFile) category.get("icon");
                            try {
                                category.setIconBmp(BitmapFactory.decodeByteArray(fileObject.getData(), 0, fileObject.getData().length));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        listener.onCategoriesReady(cat);
                    } else {
                        error.printStackTrace();
                    }
                }
            });
        } else {
            listener.onCategoriesReady(categories);
        }
    }

    /**
     * This method returns list of all Places stored on Parse.
     * Uses PlaceRequestListener to return data.
     *
     * @param verified
     * @param listener
     */
    public void getPlaces(LatLng location,Integer distance, boolean showMyPlaces, boolean verified, final PlacesRequestListener listener) {
        ParseQuery<Place> query = ParseQuery.getQuery(Place.class);
        if(location!=null) query.whereWithinKilometers("location", new ParseGeoPoint(location.latitude, location.longitude),distance);
        findPlaces(verified,showMyPlaces,query,listener);
    }

    /**
     * This method returns list of Places that match given categories.
     * Uses PlaceRequestListener to return data.
     *
     * @param categories - list of categories used to filter places, if null or empty then no filter
     */
    public void getPlacesByCategories(List<String> categories, LatLng location, Integer distance, boolean showMyPlaces, boolean verified, final PlacesRequestListener listener) {

        if (categories == null || categories.size() == 0) {
            getPlaces(location,distance, showMyPlaces, verified,listener);
        } else {
            ParseQuery<Category> catQuery = ParseQuery.getQuery(Category.class);
            catQuery.whereContainedIn("name", categories);

            ParseQuery<Place> query = ParseQuery.getQuery(Place.class);
            query.whereMatchesQuery("category", catQuery);
            if(location!=null) query.whereWithinKilometers("location", new ParseGeoPoint(location.latitude, location.longitude),distance);
            findPlaces(verified,showMyPlaces,query,listener);
        }
    }

    /**
     * This method returns list of all Reviews for a certain Place stored on Parse.
     *
     * @param place - a Place used to filter Reviews
     * @return reviews - list of Reviews for a given place
     */
    private List<Review> getReviewsSync(Place place) {
        List<Review> reviews = new ArrayList<>();
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        query.whereEqualTo("aboutPlace", place);
        try {
            reviews = new ArrayList<>(query.find());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }




    /**
     * A callback interface used for asynchronous requests for list of Category.
     */
    public interface CategoriesRequestListener {
        /**
         * Method allows to process fetched list of Category.
         * @param categories
         */
        void onCategoriesReady(List<Category> categories);
    }


    /**
     * A callback interface used for asynchronous requests for list of Place.
     */
    public interface PlacesRequestListener {
        /**
         * Method allows to process fetched list of Place.
         * @param places
         */
        void onPlacesReady(List<Place> places);
    }


    /**
     * A callback interface used for new ParseObject creation requests.
     */
    public interface NewObjectListener {
        /**
         * Method notifies when save event has been successfully completed.
         */
        void onSaveComplete();
    }

    /**
     * Method adds new Place to database using given name, description and category. Uses NewObjectListener to notify progress.
     * @param name
     * @param description
     * @param category
     * @param listener
     */
    public void addNewPlace(String name, String description, Category category, LatLng position, final NewObjectListener listener) {
        Place place = new Place();
        place.setLocation(position);
        updatePlace(place, name, description, category, listener);
    }

    public void saveModifiedPlace(Place place, String name, String description, Category category, final NewObjectListener listener) {
        updatePlace(place,name,description,category,listener);
    }

    protected void updatePlace(Place place, String name, String description, Category category, final NewObjectListener listener){
        place.put("name", name);
        place.put("category", category);
        place.put("description", description);
        place.put("verified", false);
        place.put("creator", MyUser.getCurrentUser());
        place.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    listener.onSaveComplete();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Method adds new Review to database. Uses NewObjectListener to notify progress.
     * @param review
     */
    public void addReview(Review review, final NewObjectListener listener) {
        review.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null) {
                    listener.onSaveComplete();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * A callback interface used for new ParseObject update requests.
     */
    public interface ObjectUpdateListener {
        /**
         * Method notifies when update event has been successfully completed.
         */
        void onUpdateComplete();
    }


    /**
     * Method negates verified field of a given Place and updates object in database.
     * Uses ObjectUpdateListener to notify progress.
     * @param place
     * @param listener
     */
    public void changeVerificationStatus(Place place, final ObjectUpdateListener listener) {
        place.put("verified",!place.getBoolean("verified"));
        place.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null) {
                    listener.onUpdateComplete();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void signUpUser(String name, String email, String password, final SignInListener listener) {
        final MyUser user = new MyUser();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        user.put("role","reader");
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null) {
                    ParseQuery<ParseRole> roleQuery = ParseQuery.getQuery(ParseRole.class);
                    roleQuery.whereEqualTo("name","Czytelnik");
                    roleQuery.findInBackground(new FindCallback<ParseRole>() {
                        @Override
                        public void done(List<ParseRole> parseRoles, ParseException e) {
                            if(e==null) {
                                parseRoles.get(0).getUsers().add(user);
                                parseRoles.get(0).saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        listener.onSuccess();
                                    }
                                });
                            } else {
                                listener.onFailure();
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    listener.onFailure();
                    e.printStackTrace();
                }
            }
        });
    }

    public void signIn(String username, String password, final SignInListener listener) {
        ParseUser.logInInBackground(username,password,new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if(e==null) {
                    listener.onSuccess();
                } else {
                    e.printStackTrace();
                    listener.onFailure();
                }
            }
        });
    }

    public void logOut() {
        ParseUser.logOut();
    }

    public interface SignInListener {
        void onSuccess();
        void onFailure();
    }

    protected void findPlaces(boolean verified, boolean showMyPlaces, ParseQuery<Place> query,final PlacesRequestListener listener){
        if(verified)
            query.whereEqualTo("verified",verified);
        if(showMyPlaces)
            query.whereEqualTo("creator",MyUser.getCurrentUser());
        query.findInBackground(new FindCallback<Place>() {
            @Override
            public void done(List<Place> pl, ParseException error) {
                if (error == null) {
                    places = pl;
                    for (Place place : places) {
                        place.setReviews(getReviewsSync(place));
                        try {
                            ParseFile fileObject = (ParseFile) place.get("image");
                            place.setPreviewPhoto(fileObject.getUrl());
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    listener.onPlacesReady(places);
                } else {
                    error.printStackTrace();
                }
            }
        });
    }
}
