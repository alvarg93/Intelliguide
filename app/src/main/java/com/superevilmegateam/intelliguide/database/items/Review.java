package com.superevilmegateam.intelliguide.database.items;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * This class is used to store ParseObject of type Review
 * @author Jan Badura
 */
@ParseClassName("Review")
public class Review extends ParseObject {

    public String getContent(){
        return getString("content");
    }
    public void setContent(String content){
        put("content", content);
    }

    public Integer getStars() {
        return getInt("stars");
    }
    public void setStars(Integer stars){
        put("stars", stars);
    }
}