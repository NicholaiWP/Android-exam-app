package com.example.nicholai.examdiaryapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicholai.examdiaryapp.Activities.MainActivity;
import com.example.nicholai.examdiaryapp.DiaryPage;
import com.example.nicholai.examdiaryapp.PageAdapter;
import com.example.nicholai.examdiaryapp.R;
import com.example.nicholai.examdiaryapp.Singleton.PageManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPagesFragment extends Fragment {

    private RecyclerView recyclerView;
    private PageAdapter adapter;
    private MainActivity main;
    private View view;

    public MyPagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflates the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_pages, container, false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child(PageManager.PAGE_PATH);

        // myRef.setValue(null); to erase database

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //Recycler view will have a fixed size, so increase or decrease doesnt have an influence on it
        recyclerView.setHasFixedSize(true);
        //recycleview design layout
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),  LinearLayoutManager.HORIZONTAL, false));
        adapter = new PageAdapter(view.getContext(), PageManager.getInstance().pages);
        recyclerView.setAdapter(adapter);

        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    DiaryPage page = postSnapshot.getValue(DiaryPage.class);
                    adapter.addItem(page);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity){
            main = (MainActivity) context;
        }
    }

}
