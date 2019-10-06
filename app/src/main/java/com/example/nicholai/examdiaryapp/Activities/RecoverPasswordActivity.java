package com.example.nicholai.examdiaryapp.Activities;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholai.examdiaryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class RecoverPasswordActivity extends AppCompatActivity {

    private EditText emailText;
    private FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        //Find ids of buttons and email input field
        emailText = findViewById(R.id.edt_reset_email);
        Button resetPasswordButton = findViewById(R.id.btn_reset_password);
        Button backButton = findViewById(R.id.btn_back);

        //get firebase authentication instance
        fireAuth = FirebaseAuth.getInstance();

        //listen to reset button clicks
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get user's email input
                String email = emailText.getText().toString().trim();

                //check to see if string/email entered by user is empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //this uses firebase authentication to send an e-mail to the e-mail matching what the user types, if the user is registered within firebase
                fireAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RecoverPasswordActivity.this, "Email sent! Check email to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RecoverPasswordActivity.this, "Failed to send reset password email!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //listen to back button, if it is pressed, finish/exit activity and return to launcher (Login screen)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

