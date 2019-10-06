package com.example.nicholai.examdiaryapp;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * run before any activity
 */
public class MyApplication extends Application {

    /*
    private RefWatcher watcher;

    public static RefWatcher getRefWatcher(Context context){
        MyApplication application = (MyApplication) context.getApplicationContext();
        return  application.watcher;
    }
    */

    /**
     * This is for securing persistence between the app and firebase
     * and is run before activities. (declared in manifest file)
     */
    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize leak canary
      // watcher = LeakCanary.install(this);

        //
        FirebaseApp.initializeApp(this);
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        }
    }
}

