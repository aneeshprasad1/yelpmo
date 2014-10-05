package com.yelpmo.app.Preferences;

/**
 * Created by Corey on 10/4/14.
 */
public class UserDetails {

    private static String SIGNED_IN = "is_signed_in";
    private static String HAS_LOGIN = "has_login";
    private static String VENMO_TOKEN = "venmo_token";
    private static String HAS_VENMO = "has_venmo";

    public static boolean isSignedIn() {
        return PreferenceManager.getBoolean(SIGNED_IN, false);
    }
    public static boolean hasLogIn() {
        return PreferenceManager.getBoolean(HAS_LOGIN, false);
    }
    public static boolean hasVenmo() { return PreferenceManager.getBoolean(HAS_VENMO, false);}
    public static String getVenmoToken() { return PreferenceManager.getString(VENMO_TOKEN);}

    public static void setSignedIn(boolean signedIn) {
        PreferenceManager.putBoolean(SIGNED_IN, signedIn);
    }

    public static void setHasLogIn(boolean hasLogIn) {
        PreferenceManager.putBoolean(HAS_LOGIN, hasLogIn);
    }

    public static void setVenmoToken(String token) {
        PreferenceManager.putString(VENMO_TOKEN, token);
    }

    public static void setHasVenmo(boolean hasVenmo) {
        PreferenceManager.putBoolean(HAS_VENMO, hasVenmo);
    }

}
