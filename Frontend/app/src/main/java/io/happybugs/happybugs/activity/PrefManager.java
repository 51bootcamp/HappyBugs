package io.happybugs.happybugs.activity;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "IntroScreen";
    private static final String IS_FIRST_LAUNCH = "IsFirstLaunch";

    // int 0 is for making the prefernce file private
    private int MODE_PRIVATE = 0;

    PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // call in intro activity to set false after first launch
    public void setIsFirstLaunch(boolean isFirstLaunch) {
        editor.putBoolean(IS_FIRST_LAUNCH, isFirstLaunch);
        editor.commit();
    }

    //return set value, if no value is set then return the default value
    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true);
    }
}
