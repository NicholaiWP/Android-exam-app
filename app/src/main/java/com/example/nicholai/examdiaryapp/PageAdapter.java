package com.example.nicholai.examdiaryapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.nicholai.examdiaryapp.Fragments.SettingsFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Class used to handle functionality of the recycle view and its data found in the layout of 'MyPagesFragment'.
 */
public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {

    private Context context;
    private ArrayList<DiaryPage> pages = new ArrayList<>();

    public PageAdapter(Context context, ArrayList<DiaryPage> noteList) {
        this.context = context;
        pages = noteList;
    }

    /**Empty constructor
     *
     */
    public PageAdapter(){

    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout, null);

        return new PageViewHolder(view);
    }

    /**
     * This method calls onBindViewHolder(ViewHolder, int) to update the RecyclerView.ViewHolder contents with the item at the given position
     * and also sets up some fields to be used by RecyclerView.
     * @param pageViewHolder holds reference to UI
     * @param position keeps track of a diary page in the view holder
     */
    @Override
    public void onBindViewHolder(@NonNull final PageViewHolder pageViewHolder, final int position) {
        //binds data to view holder
        final DiaryPage page = pages.get(position);
        pageViewHolder.title.setText(page.getTitle());
        pageViewHolder.bodyText.setText(page.getNoteBody());

        //Time
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());
        String strDate = df.format(date);
        pageViewHolder.timeView.setText(strDate);

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return returns the size of the collection used in the recycler view
     */
    @Override
    public int getItemCount() {
        return pages.size();
        //returns size of list
    }

    /**
     * Clears list of data
     */
    public void clear() {
        pages.clear();
        int sizeOfList = pages.size();
      //  Notify any registered observers that the itemCount items previously located at positionStart have been removed from the data set.
        notifyItemRangeRemoved(0, sizeOfList);
    }

    /**
     * I could not find an optimal solution to delete an item in the database,
     * So I first remove it from the application (the diary note), then remove the database and re-add
     * The information to the database from the array list of diary pages. A better solution would be to
     * just delete a diary page based on a ID or similar, but i did not know how to do this.
     * @param position specifies the position of the diary page
     */
    private void removeItem(final int position) {
        pages.remove(position);
        //Notify any registered observers that the item previously located at position has been removed from the data set.
        notifyItemRemoved(position);
        //Notify any registered observers that the itemCount items starting at position positionStart have changed.
        notifyItemRangeChanged(position, pages.size());


    }


    public void addItem(DiaryPage page){
        pages.add(page);
        //Notify any registered observers that the item reflected at position has been newly inserted.
        notifyItemInserted(pages.size()-1);
    }

    /**
     * Holds references to the layout's UI elements used by the recycler view
     */
    class PageViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView bodyText;
        private TextView timeView;

        PageViewHolder(@NonNull final View itemView) {
            super(itemView);
            timeView = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.myTitle);
            bodyText = itemView.findViewById(R.id.body);
            ImageView deleteButton = itemView.findViewById(R.id.deletePageButton);

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

                                }
                            });


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
