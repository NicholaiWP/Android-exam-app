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
import com.example.nicholai.examdiaryapp.R;
import com.example.nicholai.examdiaryapp.Singleton.NoteManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNoteFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_create_note, container, false);

        Button button = (Button)view.findViewById(R.id.createNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTitle = view.findViewById(R.id.editTitle);
                EditText editBody = view.findViewById(R.id.editBodyText);
                NoteManager.getInstance().addNote(new Note(editTitle.getText().toString(), editBody.getText().toString()));
                Toast.makeText(view.getContext(),"Note created", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
