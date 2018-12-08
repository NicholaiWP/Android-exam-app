package com.example.nicholai.examdiaryapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.nicholai.examdiaryapp.Note;
import com.example.nicholai.examdiaryapp.NoteAdapter;
import com.example.nicholai.examdiaryapp.R;
import com.example.nicholai.examdiaryapp.Singleton.NoteManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNoteFragment extends Fragment {

    private View view;
    private NoteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_create_note, container, false);

        adapter = new NoteAdapter(view.getContext(), NoteManager.getInstance().notes);
        Button button = (Button)view.findViewById(R.id.createNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("notes");
                EditText editTitle = view.findViewById(R.id.editTitle);
                EditText editBody = view.findViewById(R.id.editBodyText);
                adapter.addItem(new Note(editTitle.getText().toString(), editBody.getText().toString()));
                Toast.makeText(view.getContext(),"Note created", Toast.LENGTH_SHORT).show();

                //Database stuff
                /*
                myRef.push().setValue(NoteManager.getInstance().notes);
                adapter.notifyDataSetChanged();
                */

            }
        });
        return view;
    }

}
