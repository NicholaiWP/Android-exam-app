package com.example.nicholai.examdiaryapp.Classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicholai.examdiaryapp.R;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<TextNote> {

    private Context context;
    private int resourceLayout;

    public NoteAdapter(@NonNull Context context, int resourceLayout, ArrayList<TextNote> notes) {
        super(context,0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final TextNote notes = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter, parent, false);
        }

        TextView titleView = (TextView) convertView.findViewById(R.id.Title);
        TextView bodyTextView = (TextView) convertView.findViewById(R.id.TextBody);


        if (notes == null) throw new AssertionError();
        titleView.setText(notes.getNoteTitle());
        bodyTextView.setText(notes.getNoteBody());

        /*
        if(person.getName().equals("person 1") || person.getName().equals("person 2")){
            image.setImageResource(R.drawable.old_man);
        }
        if(person.getName().equals("worker")){
            image.setImageResource(R.drawable.worker);
        }
        if(person.getName().equals("Pensionist")){
            image.setImageResource(R.drawable.old_man);
        }

*/
        return convertView; }

}
