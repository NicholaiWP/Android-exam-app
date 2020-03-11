package com.example.nicholai.examdiaryapp.Fragments;


import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.nicholai.examdiaryapp.DiaryPage;
import com.example.nicholai.examdiaryapp.R;
import com.example.nicholai.examdiaryapp.Singleton.PageManager;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePageFragment extends Fragment {

    private View view;
    private EditText editTitle;
    private EditText editBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_create_page, container, false);

    //get firebase instance
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
    // set database reference

        String uid = FirebaseAuth.getInstance().getUid();
        if(uid == null)
            return view;
      final DatabaseReference myRef2 = database.getReference().child(uid).child("pages").push();

         editTitle = view.findViewById(R.id.editTitle);
         editBody = view.findViewById(R.id.editBodyText);

        Button button = (Button)view.findViewById(R.id.createNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef2.setValue(new DiaryPage(editTitle.getText().toString(), editBody.getText().toString()));
                Toast.makeText(view.getContext(),"Page created", Toast.LENGTH_SHORT).show();
                //clear for previous text input
                editTitle.getText().clear();
                editBody.getText().clear();
            }
        });
        return view;
    }

}
