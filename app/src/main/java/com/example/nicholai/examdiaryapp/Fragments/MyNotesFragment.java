package com.example.nicholai.examdiaryapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nicholai.examdiaryapp.NoteAdapter;
import com.example.nicholai.examdiaryapp.R;
import com.example.nicholai.examdiaryapp.Singleton.NoteManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyNotesFragment extends Fragment {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private View view;

    public MyNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflates the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_notes, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //Recycler view will have a fixed size, so increase or decrease doesnt have an influence on it
        recyclerView.setHasFixedSize(true);
        //recycleview design layout
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new NoteAdapter(view.getContext(), NoteManager.getInstance().notes);
        recyclerView.setAdapter(adapter);

        return view;

    }

}
