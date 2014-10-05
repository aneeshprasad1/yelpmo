package com.yelpmo.app.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.yelpmo.app.Application.App;

import java.util.Set;

/**
 * Created by corey on 7/30/14.
 */
public class PreferenceManager {

    private static final String GENERAL_PREFERENCES = "YELPMO_GENERAL_PREFS";

    private static SharedPreferences getPreferences() {
        return App.getInstance().getSharedPreferences(GENERAL_PREFERENCES, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    public static void putBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static void putString(String key, String value) {
        getEditor().putString(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    public static void putInt(String key, int value) {
        getEditor().putInt(key, value).commit();
    }

    public static int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static void putFloat(String key, float value) {
        getEditor().putFloat(key, value).commit();
    }

    public static float getFloat(String key, float defaultValue) {
        return getPreferences().getFloat(key, defaultValue);
    }

    public static float getFloat(String key) {
        return getFloat(key, 0);
    }

    public static long getLong(String key, long defaultValue) {
        return getPreferences().getLong(key, defaultValue);
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static void putLong(String key, long value) {
        getEditor().putLong(key, value).commit();
    }

    public static void putStringSet(String key, Set<String> value) {
        getEditor().putStringSet(key, value).commit();
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValues) {
        return getPreferences().getStringSet(key, defaultValues);
    }

    public static Set<String> getStringSet(String key) {
        return getPreferences().getStringSet(key, null);
    }

}
