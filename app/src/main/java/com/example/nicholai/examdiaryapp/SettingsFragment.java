package com.example.nicholai.examdiaryapp;


import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {

    private static String DARK_STATE = "Dark";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add layout from 'prefs' in the xml directory folder
        addPreferencesFromResource(R.xml.prefs);
    }

    public static boolean IsDarkState(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(DARK_STATE, false);
    }

}
