package com.example.nicholai.examdiaryapp.Activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nicholai.examdiaryapp.Singleton.PageManager;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import com.example.nicholai.examdiaryapp.Fragments.CreatePageFragment;
import com.example.nicholai.examdiaryapp.Fragments.MyPagesFragment;
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
    public static final int showPagesID = 2;
    public static final int createPagesID = 3;

    //variable to control the fragment switches
    int currentFragment = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //save theme code
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
        //Toolbar ref.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        //Checking if the item is in checked state or not, if not make it in checked state
                        if (!menuItem.isChecked())
                            menuItem.setChecked(true);
                        else
                            menuItem.setChecked(true);

                        //Closing drawer on item click
                        drawerLayout.closeDrawers();

                        //Check to see which item was being clicked and perform appropriate action
                        Fragment fragment;

                        switch (menuItem.getItemId()) {

                            case R.id.StartScreen:
                                //instantiate fragment
                                fragment = new WelcomeFragment();
                                //set fragment title
                                fragmentTitle = getResources().getString(R.string.app_name);
                                //check if current fragment equals selected fragment in the burger menu item
                                currentFragment = welcomeID;
                                break;

                            case R.id.MyNotes:
                                //instantiate fragment
                                fragment = new MyPagesFragment();
                                //set fragment title
                                fragmentTitle = getResources().getString(R.string.My_pages);
                                //check if current fragment equals selected fragment in the burger menu item
                                currentFragment = showPagesID;
                                break;

                            case R.id.CreateNote:
                                //instantiate fragment
                                fragment = new CreatePageFragment();
                                //check if current fragment equals selected fragment in the burger menu item
                                currentFragment = createPagesID;
                                //set fragment title
                                fragmentTitle = getResources().getString(R.string.Create_page);
                                break;

                            case R.id.SignOut:
                                //call sign out method, if user clicks log out and display an alert dialogue box, prompting the user for an answer.
                                //This is used because users might click on the log out button by mistake. So to make sure this doesn't happen -
                                //a dialogue alert box is displayed with  'Yes' and 'No' answers. If the user clicks 'No' the dialogue box closes, otherwise
                                //The user is logged out from his/her session.
                                signOutAlert();

                            default:
                                return true;

                        }
                        //Attempt to make a fragment transaction (switch to another screen in the app via burger menu items)
                        //The 'Frame' id is the id of the frame UI which is replaced with a fragment. All transactions are added to the back stack
                        //So that the stack can be popped (return to previous fragment/screen)
                        //title is also set here.
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

        //Check if current fragment equals the id of the fragment that I want to perform a fragment transaction on
        if (currentFragment == welcomeID) {
            Toast.makeText(getApplicationContext(), "Welcome back", Toast.LENGTH_SHORT).show();
            fragmentTransaction.replace(R.id.frame, new WelcomeFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.app_name));
            //Check if current fragment equals the id of the fragment that I want to perform a fragment transaction on
        } else if (currentFragment == showPagesID) {
            fragmentTransaction.replace(R.id.frame, new MyPagesFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.My_pages));
            //Check if current fragment equals the id of the fragment that I want to perform a fragment transaction on
        } else if (currentFragment == createPagesID) {
            fragmentTransaction.replace(R.id.frame, new CreatePageFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.Create_page));

        } else {
            fragmentTransaction.replace(R.id.frame, new WelcomeFragment());
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.app_name));
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
     * Sign user out using firebase authentication. I put this function here as this activity has the option to log out in its layout
     */
    private void signOut(){
        AuthUI.getInstance()
                .signOut(MainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth.getInstance().signOut();
                        PageManager.getInstance().pages.clear();
                        finish();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);

                    }
                });
    }

    /**
     * Display alert dialogue if user logs out.
     */
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


    /**
     * Save state of the current fragment
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //save current fragment on device rotation
        outState.putInt("fragment",currentFragment);
        super.onSaveInstanceState(outState);

    }

    /**
     * Create options menu
     * @param menu
     * @return
     */
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
            //check if dark state
            boolean isDarkTheme = SettingsFragment.IsDarkState(this);
            updateUI(isDarkTheme);

        }
    }

    /**
     * Check if user clicks on menu item
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //find it of menu item
        int id = item.getItemId();

        if (id == R.id.action_settings) {
                Intent intent = new Intent(this, PreferenceActivity.class);
                startActivityForResult(intent,RESULT_CODE_PREFERENCES);
            return true;
        }
        //Show toast, this will be replaced with a new fragment or activity at a later point with shop items
        else if(id == R.id.action_shop){
            Toast.makeText(getApplicationContext(), "Coming soon", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //if the stack of fragments are higher than 0, pop stack and go back to previously selected fragment.
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}


