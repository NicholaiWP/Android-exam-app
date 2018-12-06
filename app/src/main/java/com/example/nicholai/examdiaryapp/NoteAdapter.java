package com.example.nicholai.examdiaryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicholai.examdiaryapp.Singleton.NoteManager;

import java.util.ArrayList;

/**
 * recyclerview.adapter
 * RecyclerView.Viewholder
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context;

    private ArrayList<Note> noteList;

    private ImageView imgButtonDelete;


    private NoteViewHolder holder;

    public NoteAdapter(Context context, ArrayList<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
         View view = inflater.inflate(R.layout.list_layout, null);

        imgButtonDelete = view.findViewById(R.id.deleNoteButton);
        imgButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage("Are you sure, You want to delete this note?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                for (int i = 0; i < NoteManager.getInstance().notes.size(); i++) {
                                    deleteItem(i);
                                }
                            }});

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg0) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                //When clicked, a card is removed
            }
        });

        return new NoteViewHolder(view);
    }

    /**
     * Delete selected card from recycleview and update position
     * @param position
     */
    private void deleteItem(int position) {
        noteList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, noteList.size());
        holder.itemView.setVisibility(View.GONE);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
    //binds data to view holder, wont return anything
        holder = noteViewHolder;
        final Note note = noteList.get(i);
        noteViewHolder.title.setText(note.getTitle());
        noteViewHolder.bodyText.setText(note.getNoteBody());
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
            title = itemView.findViewById(R.id.myTitle);
            bodyText = itemView.findViewById(R.id.body);
          //  imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
