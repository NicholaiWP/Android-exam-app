package com.example.nicholai.examdiaryapp.Fragments;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicholai.examdiaryapp.DiaryPage;
import com.example.nicholai.examdiaryapp.PageAdapter;
import com.example.nicholai.examdiaryapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * This class inflates the layout showing all diary pages created by a user. The layout is here modified by the PageAdapter class which uses
 * RecyclerView functionality
 */
public class MyPagesFragment extends Fragment  {

    private PageAdapter adapter;
    private DatabaseReference myRef2;
    private ChildEventListener pageListener;
    private ArrayList<DiaryPage> diaryPages = new ArrayList<>();

    public MyPagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflates the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_pages, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        //Recycler view will have a fixed size, so increase or decrease does not have an influence on it
        recyclerView.setHasFixedSize(true);
        //recyclerView design layout, make the recyclerView have a horizontal swipe direction when there's multiple entities of a diary page
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),  LinearLayoutManager.HORIZONTAL, false));
        adapter = new PageAdapter(view.getContext(), diaryPages);
        //set recyclerView's adapter to my adapter
        recyclerView.setAdapter(adapter);

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                String uid = firebaseAuth.getUid();
                if (uid == null)
                    return;

                cleanUpListener();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();

                myRef2 = database.getReference().child(uid).child("pages");

                pageListener = myRef2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        DiaryPage page = dataSnapshot.getValue(DiaryPage.class);
                        if(page != null){
                            adapter.addItem(page);
                            adapter.notifyDataSetChanged();
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


        return view;

    }

    private void cleanUpListener() {
        if (myRef2 != null){
            myRef2.removeEventListener(pageListener);

            myRef2 = null;
            pageListener = null;
        }
    }

}
