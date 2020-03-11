package com.example.nicholai.examdiaryapp.Fragments;


import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import com.example.nicholai.examdiaryapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {

    private static String DARK_STATE = "Dark";
    private Context mContext;

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
                    //save theme using shared preferences and register its state change
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
