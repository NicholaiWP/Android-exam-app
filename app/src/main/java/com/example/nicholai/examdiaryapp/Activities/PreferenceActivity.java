package com.example.nicholai.examdiaryapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.nicholai.examdiaryapp.Activities.MainActivity;
import com.example.nicholai.examdiaryapp.Fragments.SettingsFragment;
import com.example.nicholai.examdiaryapp.R;

public class PreferenceActivity extends Activity {

    private static final int RESULT_CODE_PREFERENCES = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isDarkTheme = SettingsFragment.IsDarkState(this);
        //Needs to update theme before setting content view to avoid content view being set first
        updateUI(isDarkTheme);
        //The ID named "content" is defined by Android
        //this activity's layout is defined through the settings fragment -
        // and the activity has no layout xml
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    //This method update text views, if needed later on, call in oncreate and onactivityresult
    public void updateUI(boolean darkTheme)
    {
        if(darkTheme){
            setTheme(R.style.AppThemeDark);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_CODE_PREFERENCES) //this code means we came back from settings
        {
            boolean isDarkTheme = SettingsFragment.IsDarkState(this);
            updateUI(isDarkTheme);

        }
    }

}
