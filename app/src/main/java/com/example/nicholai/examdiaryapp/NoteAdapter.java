package com.example.nicholai.examdiaryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * recyclerview.adapter
 * RecyclerView.Viewholder
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context;

    private ArrayList<Note> noteList;

    public NoteAdapter(Context context, ArrayList<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
    //binds data to view holder, wont return anything
        Note note = noteList.get(i);
        noteViewHolder.title.setText(note.getTitle());
        noteViewHolder.bodyText.setText(String.valueOf(note.getNoteBody()));
       // noteViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(note.getImage()));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
        //returns size of list
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

      //  ImageView imageView;
        TextView title;
        TextView bodyText;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.body);
            bodyText = itemView.findViewById(R.id.myTitle);
          //  imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
