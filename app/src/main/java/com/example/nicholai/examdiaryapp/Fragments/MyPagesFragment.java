package com.example.nicholai.examdiaryapp.Fragments;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * This class inflates the layout showing all diary pages created by a user. The layout is here modified by the PageAdapter class which uses
 * RecyclerView functionality
 */
public class MyPagesFragment extends Fragment {

    private PageAdapter adapter;

    public MyPagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflates the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_pages, container, false);

        //get database reference
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child(PageManager.PAGE_PATH);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        //Recycler view will have a fixed size, so increase or decrease does not have an influence on it
        recyclerView.setHasFixedSize(true);
        //recyclerView design layout, make the recyclerView have a horizontal swipe direction when there's multiple entities of a diary page
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),  LinearLayoutManager.HORIZONTAL, false));
        adapter = new PageAdapter(view.getContext(), PageManager.getInstance().pages);
        //set recyclerView's adapter to my adapter
        recyclerView.setAdapter(adapter);

        //retrieve diary page data with Firebase's Real-time database using a snapshot
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    DiaryPage page = postSnapshot.getValue(DiaryPage.class);
                    adapter.addItem(page);
                }
                //notify adapter of changes
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
