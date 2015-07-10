package com.superevilmegateam.intelliguide.database.items;

/**
 * Class that stores information about photos.
 */
public class GalleryObject {

    private String name;
    private String type;
    private String url;

    public GalleryObject(String name, String type, String url){
        this.name = name;
        this.type = type;
        this.url = url;
    }


    public String getName(){
        return this.name;
    }
    public String getType(){
        return this.type;
    }
    public String getUrl(){
        return this.url;
    }

}
