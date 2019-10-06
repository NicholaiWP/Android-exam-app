package com.example.nicholai.examdiaryapp.Fragments;


import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nicholai.examdiaryapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //This fragment's layout purely consists of text UI and an image and provides the app no additional functionality.
        return inflater.inflate(R.layout.fragment_welcome, container, false);

    }

}
