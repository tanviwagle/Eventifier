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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragment_profile extends Fragment {

    Toolbar toolbar;

    EditText sap_id, name, pwd, email, stream;

    DatabaseReference reff;
    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        final long id = Long.parseLong(getActivity().getIntent().getStringExtra("id"));
        toolbar = root.findViewById(R.id.toolbar);
        sap_id = root.findViewById(R.id.sap);
        name = root.findViewById(R.id.name);
        pwd = root.findViewById(R.id.pwd);
        email = root.findViewById(R.id.email);
        stream = root.findViewById(R.id.stream);

        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    Users user = snap.getValue(Users.class);
                    if(id == user.getSap()){
                        sap_id.setText(""+id);
                        name.setText(""+user.getName());
                        pwd.setText(""+user.getPassword());
                        email.setText(""+user.getEmail());
                        stream.setText(""+user.getStream());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

}