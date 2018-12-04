package com.example.nicholai.examdiaryapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nicholai.examdiaryapp.Note;
import com.example.nicholai.examdiaryapp.NoteAdapter;
import com.example.nicholai.examdiaryapp.R;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class MyNotesFragment extends Fragment {

    RecyclerView recyclerView;
    NoteAdapter adapter;
    ArrayList<Note> noteArrayList;

    public MyNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflates the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_my_notes, container, false);

        noteArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //Recycler view will have a fixed size, so increase or decrease doesnt have an influence on it
        recyclerView.setHasFixedSize(true);
        //recycleview design layout
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new NoteAdapter(view.getContext(), noteArrayList);
        recyclerView.setAdapter(adapter);

        noteArrayList.add(new Note("something", "something"));

        return view;

    }

}
