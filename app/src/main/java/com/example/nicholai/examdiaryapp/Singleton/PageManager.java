package com.example.nicholai.examdiaryapp.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nicholai.examdiaryapp.DiaryPage;
import com.example.nicholai.examdiaryapp.Interfaces.IPageChangedAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Class used for storing and accessing note data across fragments
 */
public class PageManager {
    private static final PageManager ourInstance = new PageManager();

    private IPageChangedAdapter myListener;
    //this string is used by the database's child reference. Every diary note is listed under the name of this string in
    //the database

//stores pages created by the user. This is kept in memory as the singleton uses a static approach to access other classes/activities.
    public ArrayList<DiaryPage> pages = new ArrayList<>();
    private DatabaseReference myRef2;
    private ChildEventListener pageListener;

    public static PageManager getInstance() {
        return ourInstance;
    }

    private PageManager() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                String uid = firebaseAuth.getUid();
                if (uid == null)
                    return;

                cleanUpListener();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();

                myRef2 = database.getReference().child(uid).child("pages");

               pages.clear();

                pageListener = myRef2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        DiaryPage page = dataSnapshot.getValue(DiaryPage.class);
                        if(page != null){
                            pages.add(page);
                            myListener.onPagesChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private void cleanUpListener() {
        if (myRef2 != null){
            myRef2.removeEventListener(pageListener);

            myRef2 = null;
            pageListener = null;
        }
    }

    public void setSomeEventListener (IPageChangedAdapter listener) {
        myListener = listener;
    }

}
