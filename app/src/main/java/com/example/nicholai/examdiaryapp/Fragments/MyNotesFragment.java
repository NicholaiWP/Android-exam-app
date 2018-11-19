package com.example.nicholai.examdiaryapp.Fragments;


import android.os.Bundle;
import android.support.design.snackbar.ContentViewCallback;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nicholai.examdiaryapp.Classes.NoteAdapter;
import com.example.nicholai.examdiaryapp.Classes.TextNote;
import com.example.nicholai.examdiaryapp.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyNotesFragment extends ListFragment {

    public ArrayList<TextNote> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<TextNote> notes) {
        this.notes = notes;
    }

    private ArrayList<TextNote> notes = new ArrayList<>();

    public MyNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(getContext() != null && getView() != null){
            notes.add(new TextNote("lol", "lol"));
            ArrayAdapter<TextNote> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_checked, notes);

            ListView listView = getListView();
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            setListAdapter(adapter);

            NoteAdapter nAdapter = new NoteAdapter(getContext(), 0,notes);
            listView.setAdapter(nAdapter);
        }

        return inflater.inflate(R.layout.fragment_my_notes, container, false);


    }



    public void AddNote(){
        notes.add(new TextNote("", ""));
    }

}
