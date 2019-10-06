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
import com.example.nicholai.examdiaryapp.Singleton.PageManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public PageAdapter(Context context, ArrayList<DiaryPage> noteList) {
        this.context = context;
        PageManager.getInstance().pages = noteList;
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
        final DiaryPage page = PageManager.getInstance().pages.get(position);
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
        return PageManager.getInstance().pages.size();
        //returns size of list
    }

    /**
     * Clears list of data
     */
    public void clear() {
        PageManager.getInstance().pages.clear();
        int sizeOfList = PageManager.getInstance().pages.size();
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
    public void removeItem(int position) {
        PageManager.getInstance().pages.remove(position);
        //Notify any registered observers that the item previously located at position has been removed from the data set.
        notifyItemRemoved(position);
        //Notify any registered observers that the itemCount items starting at position positionStart have changed.
        notifyItemRangeChanged(position, PageManager.getInstance().pages.size());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(PageManager.PAGE_PATH);

        ValueEventListener val = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    postSnapshot.getRef().removeValue();

                }
                //Notify any registered observers that the data set has changed.
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(val);

        for (int i = 0; i < PageManager.getInstance().pages.size(); i++) {
            ref.push().setValue(new DiaryPage(PageManager.getInstance().pages.get(i).getTitle(), PageManager.getInstance().pages.get(i).getNoteBody()));
        }

        ref.removeEventListener(val);
    }


    /**
     * method for Adding a diary page
     * @param page
     */
    public void addItem(DiaryPage page){
        PageManager.getInstance().pages.add(page);
        //Notify any registered observers that the item reflected at position has been newly inserted.
        notifyItemInserted(PageManager.getInstance().pages.size()-1);
    }

    /**
     * Holds references to the layout's UI elements used by the recycler view
     */
   public class PageViewHolder extends RecyclerView.ViewHolder{

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
