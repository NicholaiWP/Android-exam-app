package com.example.nicholai.examdiaryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.nicholai.examdiaryapp.Fragments.SettingsFragment;
import com.example.nicholai.examdiaryapp.Singleton.NoteManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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

        //Time
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new  SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());
        String strDate = df.format(date);
        noteViewHolder.timeView.setText(strDate);

       // noteViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(note.getImage()));

    }

    @Override
    public int getItemCount() {
        return NoteManager.getInstance().notes.size();
        //returns size of list
    }

    public void clear(){
        NoteManager.getInstance().notes.clear();
        int sizeOfList = NoteManager.getInstance().notes.size();
        notifyItemRangeRemoved(0, sizeOfList);
    }

    public void removeItem(int position){
        NoteManager.getInstance().notes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, NoteManager.getInstance().notes.size());
    }

    public Note addItem(Note note){
        NoteManager.getInstance().notes.add(note);
        notifyItemInserted(NoteManager.getInstance().notes.size()-1);

        return note;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView bodyText;
        private TextView timeView;
        private ImageView deleteButton;

        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);
            timeView = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.myTitle);
            bodyText = itemView.findViewById(R.id.body);
            deleteButton = itemView.findViewById(R.id.deleNoteButton);

            //making sure text is visible in the notes after switching to dark theme
            if(SettingsFragment.IsDarkState(itemView.getContext())){
                title.setTextColor(Color.BLACK);
                timeView.setTextColor(Color.BLACK);
                bodyText.setTextColor(Color.BLACK);
            }


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

                }
            });
        }
    }
}
