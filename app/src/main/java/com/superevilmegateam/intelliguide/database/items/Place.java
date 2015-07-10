package com.superevilmegateam.intelliguide.database.items;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.superevilmegateam.intelliguide.database.GalleryJsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store ParseObject of type Place
 * @author Jan Badura
 */
@ParseClassName("Place")
public class Place extends ParseObject {

    private List<Review> reviews;
    private String previewPhoto;

    public String getName(){
        return getString("name");
    }
    public void setName(String name){
        put("name", name);
    }

    public String getDescription(){
        return getString("description");
    }
    public void setDescription(String description){
        put("description", description);
    }

    public boolean isVerified(){
        return getBoolean("verified");
    }
    public void setVerified(boolean verified){
        put("verified", verified);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getPreviewPhoto() {
        return previewPhoto;
    }

    public void setPreviewPhoto(String previewPhoto) {
        this.previewPhoto = previewPhoto;
    }
    public LatLng getLocation() {
        ParseGeoPoint pGP = getParseGeoPoint("location");
        LatLng latLng = new LatLng(pGP.getLatitude(),pGP.getLongitude());
        return latLng;
    }

    public void setLocation(LatLng position) {
        ParseGeoPoint pGP = new ParseGeoPoint(position.latitude,position.longitude);
        put("location",pGP);
    }

    public List<com.superevilmegateam.intelliguide.database.items.GalleryObject> getGallery() {
        try {
            List<com.superevilmegateam.intelliguide.database.items.GalleryObject> list = new GalleryJsonParser().getGallery(this.getJSONArray("gallery"));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<com.superevilmegateam.intelliguide.database.items.GalleryObject>();
        }
    }
}
