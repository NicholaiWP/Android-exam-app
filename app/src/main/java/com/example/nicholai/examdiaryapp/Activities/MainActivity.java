package com.example.nicholai.examdiaryapp.Activities;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.Collections;
import java.util.Objects;

import com.example.nicholai.examdiaryapp.Fragments.CreateNoteFragment;
import com.example.nicholai.examdiaryapp.Fragments.MyNotesFragment;
import com.example.nicholai.examdiaryapp.Fragments.SettingsFragment;
import com.example.nicholai.examdiaryapp.Fragments.WelcomeFragment;
import com.example.nicholai.examdiaryapp.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //Layout for the navigation drawer
    private DrawerLayout drawerLayout;

    //reference to firebaseUI class
    FirebaseUIActivity fireUI;

    //empty as default
    private String fragmentTitle = "";

    //Pref result code
    private final int RESULT_CODE_PREFERENCES = 1;

    //sign_in constant
    private static final int RC_SIGN_IN = 123;

    //Ids of the different drawer fragments
    public static final int welcomeID = 1;
    public static final int showNotesID = 2;
    public static final int createNoteID = 3;

    private Toolbar toolbar;

    //variable to control the fragment switches
    int currentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isDarkTheme = SettingsFragment.IsDarkState(this);
        //Needs to update theme before setting content view to avoid content view being set first
        updateUI(isDarkTheme);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                new AuthUI.IdpConfig.EmailBuilder().build()))
                        .build(),
                RC_SIGN_IN);


        //check to see if the key 'fragment' exists, if it does retrieve the key
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("fragment")) {
                currentFragment = savedInstanceState.getInt("fragment");
            }

        }

        //Initializing NavigationView
        //Nav view
        NavigationView navigationView = findViewById(R.id.navigation_view);

        //toolbar variable
         toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                        //Checking if the item is in checked state or not, if not make it in checked state
                        if (menuItem.isChecked())
                            menuItem.setChecked(false);
                        else
                            menuItem.setChecked(true);

                        //Closing drawer on item click
                        drawerLayout.closeDrawers();

                        //Check to see which item was being clicked and perform appropriate action
                        Fragment fragment;

                        switch (menuItem.getItemId()) {

                            case R.id.StartScreen:
                            fragment = new WelcomeFragment();
                            fragmentTitle = getResources().getString(R.string.app_name);
                            currentFragment = welcomeID;
                                break;

                            case R.id.MyNotes:
                                fragment = new MyNotesFragment();
                                fragmentTitle = getResources().getString(R.string.My_notes);
                                currentFragment = showNotesID;
                                break;

                            case R.id.CreateNote:
                                fragment = new CreateNoteFragment();
                                currentFragment = createNoteID;
                                fragmentTitle = getResources().getString(R.string.Create_note);
                                break;

                            case R.id.SignOut:
                                //TODO add sign out functionality from authentication
                               fireUI = (FirebaseUIActivity) getApplicationContext();
                                fireUI.signOut();

                            default:
                                if(menuItem.getItemId() == R.id.SignOut){
                                    Toast.makeText(getApplicationContext(), "log user out", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Something is Wrong", Toast.LENGTH_SHORT).show();
                                }

                                return true;

                        }
                        try {
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frame, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            Objects.requireNonNull(getSupportActionBar()).setTitle(fragmentTitle); //set the title of the action bar

                        } catch (Exception e) {
                            Log.d("FragmentNULL", "onNavigationItemSelected; fragment might be null " + e);
                        }
                        return true;
                    }

                });  //end of navigation drawer code

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes
                super.onDrawerClosed(drawerView);
                //unused
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open
                super.onDrawerOpened(drawerView);
                //unused
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // doing the fragment transaction here - replacing frame with HomeFragment -
        //which is the startup fragment in the app.
        Log.d("currrentfragment","currentfragment : "+currentFragment);

        if(currentFragment == welcomeID){
            fragmentTransaction.replace(R.id.frame, new WelcomeFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.Welcome));

        }
       else  if(currentFragment == showNotesID){
            fragmentTransaction.replace(R.id.frame, new MyNotesFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.My_notes));


        }
        else if(currentFragment == createNoteID){
             fragmentTransaction.replace(R.id.frame, new CreateNoteFragment());
             Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.Create_note));

        }

        else{
             fragmentTransaction.replace(R.id.frame, new WelcomeFragment());
        }

        fragmentTransaction.commit(); //set starting fragment
    }

    //This method update text views, if needed later on, call in oncreate and onactivityresult
    public void updateUI(boolean darkTheme)
    {
        if(darkTheme){

            Log.d("ThemeChange", "updateUI: theme changed ");
            setTheme(R.style.AppThemeDark);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //save current fragment on device rotation
        outState.putInt("fragment",currentFragment);
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_CODE_PREFERENCES) //this code means we came back from settings
        {
            boolean isDarkTheme = SettingsFragment.IsDarkState(this);
            Log.d("checkOnActivityResult", "onActivityResult: ");
            updateUI(isDarkTheme);
            //Display snackbar or toast here to notify user about change

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
                Intent intent = new Intent(this, PreferenceActivity.class);
                startActivityForResult(intent,RESULT_CODE_PREFERENCES);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}


