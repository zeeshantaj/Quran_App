package com.example.quran_application.Translation;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {
    private static final String PREF_NAME = "my_preferences";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public MySharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveValue(String key, int value) {
        editor.putInt(key, value);
        editor.apply(); // Use apply() for asynchronous saving or commit() for synchronous saving
    }

    public int getValue(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void overrideValue(String key, int newValue) {
        editor.putInt(key, newValue);
        editor.apply(); // Use apply() for asynchronous saving or commit() for synchronous saving
    }
}
