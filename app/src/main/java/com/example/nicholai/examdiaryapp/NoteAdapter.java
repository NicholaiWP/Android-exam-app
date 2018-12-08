package com.example.nicholai.examdiaryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public NoteAdapter(Context context, ArrayList<Note> noteList) {
        this.context = context;
        NoteManager.getInstance().notes = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
         View view = inflater.inflate(R.layout.list_layout, null);

        return new NoteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder noteViewHolder, final int position) {
    //binds data to view holder, wont return anything
        final Note note = NoteManager.getInstance().notes.get(position);
        noteViewHolder.title.setText(note.getTitle());
        noteViewHolder.bodyText.setText(note.getNoteBody());
       // noteViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(note.getImage()));

    }

    @Override
    public int getItemCount() {
        return NoteManager.getInstance().notes.size();
        //returns size of list
    }

    private void removeItem(int position){
        NoteManager.getInstance().notes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, NoteManager.getInstance().notes.size());
    }

    public void addItem(Note note){
        NoteManager.getInstance().notes.add(note);
        notifyItemInserted(NoteManager.getInstance().notes.size()-1);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

      //  ImageView imageView;
        TextView title;
        TextView bodyText;
        ImageView deleteButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.myTitle);
            bodyText = itemView.findViewById(R.id.body);
            deleteButton = itemView.findViewById(R.id.deleNoteButton);


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                    alertDialogBuilder.setMessage("Are you sure, You want to delete this note?");
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    removeItem(getAdapterPosition());
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
          //  imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
