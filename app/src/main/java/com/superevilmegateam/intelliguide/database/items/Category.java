package com.superevilmegateam.intelliguide.database.items;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;

/**
 * This class is used to store ParseObject of type Category
 * @author Jan Badura
 */
@ParseClassName("Category")
public class Category extends ParseObject {

    private boolean selected = false;
    private Bitmap iconBmp;

    public String getName(){
        return getString("name");
    }
    public void setName(String name){
        put("name", name);
    }

    public ParseFile getIcon(){
        return getParseFile("icon");
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setIconBmp(Bitmap iconBmp) {
        this.iconBmp = iconBmp;
    }

    public Bitmap getIconBmp() {
        return iconBmp;
    }
}
