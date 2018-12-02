package com.example.nicholai.examdiaryapp.Activities;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nicholai.examdiaryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


/**
 * This class was made with help from following youtube tutorial (part 1 and 2) - of how to create custom login screen
 * https://www.youtube.com/watch?v=mF5MWLsb4cg
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth fireAuth;
    private EditText editTextEmail, editTextPassword;
    private ProgressBar loadProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fireAuth = FirebaseAuth.getInstance();

/* //For using firebase's standard UI.
        FirebaseAuth auth = FirebaseAuth.getInstance();
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                new AuthUI.IdpConfig.EmailBuilder().build()))
                        .build(),
                RC_SIGN_IN);
*/

        loadProgressBar = findViewById(R.id.progressBar);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        findViewById(R.id.registerUser).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.forgotPassword).setOnClickListener(this);
    }

    /**
     * Method used for the button to register a new user
     */
        private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

            //check is the email address entered by the user matches the one registered in firebase authentication
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid Email");
            editTextEmail.requestFocus();
            return;
        }

        //Check if the user entered a password
         if(password.isEmpty()){
            editTextEmail.setError("Password is required");
             editTextEmail.requestFocus();
             return;
        }


        if(password.length() < 6){
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        loadProgressBar.setVisibility(View.VISIBLE);

        fireAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loadProgressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    //clear all activities on top of the stack
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else{
                    //Check to see if user is already registered
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        }

        private void goToPasswordReset(){
            Intent intent = new Intent(this, RecoverPasswordActivity.class);
            startActivity(intent);
        }


    /**
     * Method used for the button to login a user
     */
    private void userLogin(){

            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if(email.isEmpty()){
                editTextEmail.setError("Email is required");
                editTextEmail.requestFocus();
                return;
            }

            //check is the email address entered by the user matches the one registered in firebase authentication
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.setError("Enter a valid Email");
                editTextEmail.requestFocus();
                return;
            }
            //Check is password is empty
            if(password.isEmpty()){
                editTextEmail.setError("Password is required");
                editTextEmail.requestFocus();
                return;
            }

            //Check if length of password is less than 6
            if(password.length() < 6){
                editTextPassword.setError("Minimum length of password should be 6");
                editTextPassword.requestFocus();
                return;
            }

            //Hide progress bar until the button is pressed
            loadProgressBar.setVisibility(View.VISIBLE);

            //Here I'm using firebase's sing-in with email and password method which takes in two strings; an email and password.
            fireAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                //When a user signs in, the method below validates whether the task was successful or not.
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    loadProgressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        //if user signs out manually, I don't want the previous text to still be there
                        Toast.makeText(getApplicationContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        //clear all activities on top of the stack
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        editTextPassword.getText().clear();
                        editTextEmail.getText().clear();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerUser:
                registerUser();
                break;
            case R.id.login:
               userLogin();
                break;
            case R.id.forgotPassword:
                goToPasswordReset();
                break;
        }
    }
}
