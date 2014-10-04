package com.yelpmo.app.Application;

import android.app.Application;

/**
 * Created by Corey on 10/4/14.
 */
public class App extends Application {

    private static Application appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    public static Application getInstance() {
        return appInstance;
    }

}