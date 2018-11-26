package com.example.nicholai.examdiaryapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.nicholai.examdiaryapp.R;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyNotesFragment extends Fragment {

    private String[] countries = { "Albania", "Algeria", "Armenia", "Andorra",
            "Angola", "Argentina", "Australia", "Bahrain", "Bangladesh",
            "Barbados", "Brazil", "China", "Denmark", "Egypt", "France",
            "Ghana", "Hong Kong", "India", "Italy", "United Kingdom",
            "United States", "United Arab Emirates" };

    public MyNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflates the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_my_notes, container, false);

        ArrayList<String> list = new ArrayList<>(Arrays.asList(countries));

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_checked, list);

        final ListView listView = view.findViewById(R.id.noteList);

        //only allow one element to be selected at the same time
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("listview","itemclicked");
                String country = countries[position];
                Toast.makeText(view.getContext(),"you clicked : "+
                        country,Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

}
