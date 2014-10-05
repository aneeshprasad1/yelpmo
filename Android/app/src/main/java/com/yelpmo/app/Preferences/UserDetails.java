package com.yelpmo.app.Preferences;

/**
 * Created by Corey on 10/4/14.
 */
public class UserDetails {

    private static String SIGNED_IN = "is_signed_in";
    private static String HAS_LOGIN = "has_login";

    public static boolean isSignedIn() {
        return PreferenceManager.getBoolean(SIGNED_IN, false);
    }
    public static boolean hasLogIn() {
        return PreferenceManager.getBoolean(HAS_LOGIN, false);
    }

}
