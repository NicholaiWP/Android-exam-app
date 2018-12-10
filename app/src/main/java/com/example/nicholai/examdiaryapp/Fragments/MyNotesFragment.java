package com.example.nicholai.examdiaryapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicholai.examdiaryapp.Note;
import com.example.nicholai.examdiaryapp.NoteAdapter;
import com.example.nicholai.examdiaryapp.R;
import com.example.nicholai.examdiaryapp.Singleton.NoteManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child(NoteManager.NOTE_PATH);

        // myRef.setValue(null); to erase database

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //Recycler view will have a fixed size, so increase or decrease doesnt have an influence on it
        recyclerView.setHasFixedSize(true);
        //recycleview design layout
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),  LinearLayoutManager.HORIZONTAL, false));
        adapter = new NoteAdapter(view.getContext(), NoteManager.getInstance().notes);
        recyclerView.setAdapter(adapter);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Note note = postSnapshot.getValue(Note.class);
                    adapter.addItem(note);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("test", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        myRef.addListenerForSingleValueEvent(postListener);

        return view;

    }

}
