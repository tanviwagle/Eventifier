package com.example.eventifier;

import android.annotation.SuppressLint;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class fragment_profile extends Fragment {

    Toolbar toolbar;
    TextView sap_id, name, stream, profilepwd, email;
    DatabaseReference reff1;
    Intent intent;
    String sid, pass;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        sid = this.getArguments().getString("sap_id");

        Log.d("Profile", "Profile");
        toolbar = root.findViewById(R.id.toolbar);
        sap_id = root.findViewById(R.id.tvsap);
        name = root.findViewById(R.id.tvname);
        profilepwd = root.findViewById(R.id.profilepwd);
        email = root.findViewById(R.id.email);
        stream = root.findViewById(R.id.tvstream);
        reff1 = FirebaseDatabase.getInstance().getReference().child("Users");
        loadData();
        profilepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePasswordDialog();
            }
        });

        return root;
    }

    public void loadData(){
        reff1.child(sid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.d("Still here","still here");
                    sap_id.setText(sid);
                    name.setText(snapshot.child("name").getValue().toString());
                    profilepwd.setText(snapshot.child("password").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                    stream.setText(snapshot.child("stream").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openChangePasswordDialog(){
        ChangePasswordDialog change = new ChangePasswordDialog();
        change.setTargetFragment(fragment_profile.this, 1);
        Bundle bundle=new Bundle();
        bundle.putString("sap_id",sid);
        change.setArguments(bundle);
        change.show(getFragmentManager(),"Change Password");
    }

}