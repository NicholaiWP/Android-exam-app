package com.example.nicholai.examdiaryapp.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import com.example.nicholai.examdiaryapp.R;


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

        //theme toggle code
        if(getView() != null){
            ToggleButton toggleButton = getView().findViewById(R.id.themeToggler);
            //listen to toggle button state and change theme accordingly
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getView().getContext()).edit();
                    editor.putBoolean(DARK_STATE,isChecked);
                    editor.apply();
                }

            });
        }


    }
     // Method used to save theme state
    public static boolean IsDarkState(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(DARK_STATE, false);
    }

}
