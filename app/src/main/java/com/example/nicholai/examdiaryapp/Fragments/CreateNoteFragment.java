package com.example.nicholai.examdiaryapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nicholai.examdiaryapp.Classes.TextNote;
import com.example.nicholai.examdiaryapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNoteFragment extends Fragment {


    private String title, bodyText;

    public CreateNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_create_note, container, false);

      EditText  titleText  = v.findViewById(R.id.editTitle);
       EditText editBodyText = v.findViewById(R.id.editBodyText);

       title = titleText.getText().toString();
       bodyText = editBodyText.getText().toString();


        if (getView() != null) {
            Button createButton = getView().findViewById(R.id.createNote);
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


        return v;
    }

}
