package com.example.nicholai.examdiaryapp.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.snackbar.ContentViewCallback;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.nicholai.examdiaryapp.Classes.NoteAdapter;
import com.example.nicholai.examdiaryapp.Classes.TextNote;
import com.example.nicholai.examdiaryapp.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyNotesFragment extends ListFragment {


    public static ArrayList<TextNote> notes = new ArrayList<>();

    public MyNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_notes, container, false);

        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

      //  ArrayAdapter<TextNote> adapter = new ArrayAdapter<TextNote>(getActivity(), android.R.layout.activity_list_item, notes);
      //  setListAdapter(adapter);
    }
}
