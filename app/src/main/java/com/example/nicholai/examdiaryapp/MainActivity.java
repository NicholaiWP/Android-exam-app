package com.example.nicholai.examdiaryapp;


import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Layout for the navigation drawer
    private DrawerLayout drawerLayout;

    //empty as default
    private String fragmentTitle = "";

    //Ids of the different drawer fragments
    public static final int welcomeID = 1;
    public static final int showNotesID = 2;
    public static final int createNoteID = 3;

    //variable to control the fragment switches
    int currentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //check to see if the key 'fragment' exists, if it does retrieve the key
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("fragment")) {
                currentFragment = savedInstanceState.getInt("fragment");
                Log.d("savedfragment", "fragment : " + currentFragment);
            }

        }

        //Initializing NavigationView
        //Nav view
        NavigationView navigationView = findViewById(R.id.navigation_view);

        //toolbar variable
        Toolbar toolbar = findViewById(R.id.toolbar);


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
                            fragmentTitle = getResources().getString(R.string.Welcome);
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


                            default:
                                Toast.makeText(getApplicationContext(), "Something is Wrong", Toast.LENGTH_SHORT).show();

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

                });  //end of nagivation drawer code

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = findViewById(R.id.drawer);

        //Showing how to override onDrawerClosed and onDrawerOpened
        //although in this app we actually dont do anything in there
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open
                super.onDrawerOpened(drawerView);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new SettingsFragment()).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


