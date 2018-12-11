package com.example.nicholai.examdiaryapp;

import android.app.Application;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    /**
     * This is for securing persistence between the app and firebase
     * and is run before activities. (declared in manifest file)
     */
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        }
    }
}

