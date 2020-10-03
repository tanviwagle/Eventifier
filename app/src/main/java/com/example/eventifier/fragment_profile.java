package com.example.eventifier;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class fragment_profile extends Fragment {

    Toolbar toolbar;
    TextView sap_id, name, stream;
    EditText  pwd, email;
    DatabaseReference reff;
    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        final String sid = this.getArguments().getString("sap_id");
        Log.d("final",sid);
        toolbar = root.findViewById(R.id.toolbar);
        sap_id = root.findViewById(R.id.tvsap);
        name = root.findViewById(R.id.tvname);
        pwd = root.findViewById(R.id.pwd);
        email = root.findViewById(R.id.email);
        stream = root.findViewById(R.id.tvstream);
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        Log.d("child",reff.child(sid).toString());
        reff.child(sid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                        sap_id.setText(""+sid);
                        name.setText(""+snapshot.child("name").getValue());
                        pwd.setText(""+snapshot.child("password").getValue());
                        email.setText(""+snapshot.child("email").getValue());
                        stream.setText(""+snapshot.child("stream").getValue());

                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

}