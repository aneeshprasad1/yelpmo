package com.yelpmo.app.Preferences;

/**
 * Created by Corey on 10/4/14.
 */
public class UserDetails {

    private static String SIGNED_IN = "is_signed_in";

    public static boolean isSignedIn() {
        return PreferenceManager.getBoolean(SIGNED_IN, false);
    }

}
