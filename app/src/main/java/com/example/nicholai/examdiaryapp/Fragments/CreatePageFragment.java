package com.example.nicholai.examdiaryapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Toast;

import com.example.nicholai.examdiaryapp.Activities.MainActivity;
import com.example.nicholai.examdiaryapp.DiaryPage;
import com.example.nicholai.examdiaryapp.R;
import com.example.nicholai.examdiaryapp.Singleton.PageManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePageFragment extends Fragment {

    private View view;
    private EditText editTitle;
    private EditText editBody;
    private MainActivity main;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_create_page, container, false);

         database = FirebaseDatabase.getInstance();
         myRef = database.getReference().child(PageManager.PAGE_PATH);

         editTitle = view.findViewById(R.id.editTitle);
         editBody = view.findViewById(R.id.editBodyText);


        Button button = (Button)view.findViewById(R.id.createNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.push().setValue(new DiaryPage(editTitle.getText().toString(), editBody.getText().toString()));
                Toast.makeText(view.getContext(),"Page created", Toast.LENGTH_SHORT).show();
                //clear for previous text input
                editTitle.getText().clear();
                editBody.getText().clear();
            }
        });
        return view;
    }

    /**
     * Restore user input text if the user rotates the device
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
       // outState.putString("txtBody", editBody.getText().toString());
      //  outState.putString("txtTitle", editTitle.getText().toString());
    }
}
