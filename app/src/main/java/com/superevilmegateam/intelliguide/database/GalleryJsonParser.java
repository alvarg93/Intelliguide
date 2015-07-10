package com.superevilmegateam.intelliguide.database;

import com.superevilmegateam.intelliguide.database.items.GalleryObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple Class to parse JSON for gallery
 * @author Jan Badura
 */
public class GalleryJsonParser {

    public List<GalleryObject> getGallery(JSONArray json) throws JSONException {
        List<GalleryObject> list = new ArrayList<GalleryObject>();
        for(int i=0;i<json.length();i++){
            JSONObject e = json.getJSONObject(i);
            list.add(new GalleryObject(e.getString("__type"),e.getString("name"),e.getString("url")));
        }

        return list;
    }

}
