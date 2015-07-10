package com.superevilmegateam.intelliguide;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;
import com.superevilmegateam.intelliguide.database.items.Category;
import com.superevilmegateam.intelliguide.database.items.MyUser;
import com.superevilmegateam.intelliguide.database.items.Place;
import com.superevilmegateam.intelliguide.database.items.Review;

/**
 * Created by JonnySnickers on 2015-04-19.
 */
public class IntelligideApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        ParseCrashReporting.enable(this);
        Parse.initialize(this, "jJoI8tp07slznNEdWKX6qK2Pf2dXWvLcqjDb09hv", "tt4tGs7mlMYK4g099i7yd8pDMEANXMt9qNbqT57C");
        ParseObject.registerSubclass(Place.class);
        ParseObject.registerSubclass(Category.class);
        ParseObject.registerSubclass(Review.class);
        ParseObject.registerSubclass(MyUser.class);
    }
}
