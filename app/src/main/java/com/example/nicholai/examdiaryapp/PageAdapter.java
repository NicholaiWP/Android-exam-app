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
import com.example.nicholai.examdiaryapp.Singleton.PageManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {

    private Context context;

    public PageAdapter(Context context, ArrayList<DiaryPage> noteList) {
        this.context = context;
        PageManager.getInstance().pages = noteList;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
         View view = inflater.inflate(R.layout.list_layout, null);

        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PageViewHolder pageViewHolder, final int position) {
    //binds data to view holder, wont return anything
        final DiaryPage page = PageManager.getInstance().pages.get(position);
        pageViewHolder.title.setText(page.getTitle());
        pageViewHolder.bodyText.setText(page.getNoteBody());

        //Time
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new  SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());
        String strDate = df.format(date);
        pageViewHolder.timeView.setText(strDate);

    }

    @Override
    public int getItemCount() {
        return PageManager.getInstance().pages.size();
        //returns size of list
    }

    public void clear(){
        PageManager.getInstance().pages.clear();
        int sizeOfList = PageManager.getInstance().pages.size();
        notifyItemRangeRemoved(0, sizeOfList);
    }

    public void removeItem(int position){
        PageManager.getInstance().pages.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, PageManager.getInstance().pages.size());
    }


    public DiaryPage addItem(DiaryPage page){
        PageManager.getInstance().pages.add(page);
        notifyItemInserted(PageManager.getInstance().pages.size()-1);

        return page;
    }

    class PageViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView bodyText;
        private TextView timeView;
        private ImageView deleteButton;

        public PageViewHolder(@NonNull final View itemView) {
            super(itemView);
            timeView = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.myTitle);
            bodyText = itemView.findViewById(R.id.body);
            deleteButton = itemView.findViewById(R.id.deletePageButton);

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
