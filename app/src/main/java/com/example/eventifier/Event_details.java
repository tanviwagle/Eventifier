package com.example.eventifier;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Event_details extends Fragment{
    Toolbar toolbar;
    ImageView image;
    TextView name, place, desc, date, time, price, comm, cat;
    DatabaseReference reff;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_event_details, container, false);


        image = root.findViewById(R.id.cardImage);
        name = root.findViewById(R.id.name);
        place = root.findViewById(R.id.place);
        desc = root.findViewById(R.id.desc);
        date = root.findViewById(R.id.date);
        time = root.findViewById(R.id.time);
        price = root.findViewById(R.id.price);
        comm = root.findViewById(R.id.comm);
        cat = root.findViewById(R.id.cat);

        reff = FirebaseDatabase.getInstance().getReference().child("Events");
        String id = this.getArguments().getString("id");
        if(id != null)
        {
            Log.d("id",reff.child(id).toString());
        }

        reff.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.d("msg","here");
                    Picasso.get().load(snapshot.child("Image").getValue().toString()).into(image);
                    name.setText(snapshot.child("Name").getValue().toString());
                    place.setText(snapshot.child("Place").getValue().toString());
                    desc.setText(snapshot.child("Description").getValue().toString());
                    date.setText(snapshot.child("Date").getValue().toString());
                    time.setText(snapshot.child("Time").getValue().toString());
                    price.setText(snapshot.child("Charges").getValue().toString()+" /-");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
}