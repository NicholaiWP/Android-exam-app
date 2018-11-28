package com.example.nicholai.examdiaryapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.nicholai.examdiaryapp.R;

public class SplashScreenActivity extends Activity {

    private static int splashTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivityIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
                finish();
            }
        }, splashTime);

    }
}
