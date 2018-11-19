package com.example.nicholai.examdiaryapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nicholai.examdiaryapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNoteFragment extends Fragment {


    private Button createButton;


    public CreateNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getView() != null) {
            createButton = getView().findViewById(R.id.createNote);
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }


        return inflater.inflate(R.layout.fragment_create_note, container, false);
    }

}
