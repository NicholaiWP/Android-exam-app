package com.example.nicholai.examdiaryapp.Activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.Objects;

import com.example.nicholai.examdiaryapp.Fragments.CreateNoteFragment;
import com.example.nicholai.examdiaryapp.Fragments.MyNotesFragment;
import com.example.nicholai.examdiaryapp.Fragments.SettingsFragment;
import com.example.nicholai.examdiaryapp.Fragments.WelcomeFragment;
import com.example.nicholai.examdiaryapp.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //Layout for the navigation drawer
    private DrawerLayout drawerLayout;

    //empty as default
    private String fragmentTitle = "";

    //Pref result code
    private final int RESULT_CODE_PREFERENCES = 1;

    //Ids of the different drawer fragments
    public static final int welcomeID = 1;
    public static final int showNotesID = 2;
    public static final int createNoteID = 3;

    //Toolbar ref.
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
                                signOutAlert();

                            default:
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

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

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

        //calling sync state is necessay or else hamburger icon won't show up
        actionBarDrawerToggle.syncState();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // doing the fragment transaction here - replacing frame with navigation drawer fragment elements -

        if (currentFragment == welcomeID) {
            fragmentTransaction.replace(R.id.frame, new WelcomeFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.Welcome));

        } else if (currentFragment == showNotesID) {
            fragmentTransaction.replace(R.id.frame, new MyNotesFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.My_notes));


        } else if (currentFragment == createNoteID) {
            fragmentTransaction.replace(R.id.frame, new CreateNoteFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.Create_note));

        } else {
            fragmentTransaction.replace(R.id.frame, new WelcomeFragment());
        }

        fragmentTransaction.commit(); //set starting fragment
    }

    //This method update text views, if needed later on, call in oncreate and onactivityresult
    public void updateUI(boolean darkTheme) {
        if (darkTheme) {
            setTheme(R.style.AppThemeDark);
        }
    }

    /**
     * Sign user out using firebase authentication
     */
    private void signOut(){
        AuthUI.getInstance()
                .signOut(MainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                });
    }

    private void signOutAlert() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You want to sign out of 'Glimpse'?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    signOut();
                }});

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg0) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
            updateUI(isDarkTheme);

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

    @Override
    protected void onStop() {
        super.onStop();
        //Force user to log out when app is closed
        FirebaseAuth.getInstance().signOut();
    }
}


