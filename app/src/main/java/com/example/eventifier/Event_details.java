package com.example.eventifier;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class Event_details extends Fragment{
    Toolbar toolbar;
    ImageView image;
    TextView name, place, desc, date, time, price, comm, cat;
    DatabaseReference reff, refBook;
    String id, user_id;
    Fragment f = null;
    Book book;
    long count = 0;

    String event_place, event_date, event_time, charges;

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
        id = this.getArguments().getString("id");
        user_id = this.getArguments().getString("sap_id");
        Log.d("event", user_id);

        f = new fragment_book_event();
        book = new Book();

        reff.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.d("msg","here");
                    event_place = snapshot.child("Place").getValue().toString();
                    event_date = snapshot.child("Date").getValue().toString();
                    event_time = snapshot.child("Time").getValue().toString();

                    Picasso.get().load(snapshot.child("Image").getValue().toString()).into(image);
                    name.setText(snapshot.child("Name").getValue().toString());
                    place.setText(event_place);
                    desc.setText(snapshot.child("Description").getValue().toString());
                    date.setText(event_date);
                    time.setText(event_time);
                    price.setText(snapshot.child("Charges").getValue().toString()+" /-");

                    charges = snapshot.child("Charges").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refBook = FirebaseDatabase.getInstance().getReference().child("Booked_Events").child(user_id);
        refBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FloatingActionButton btn = root.findViewById(R.id.floatBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Count",""+count);
                book.setName(id);
                book.setPlace(event_place);
                book.setDate(event_date);
                book.setTime(event_time);
                refBook.child("Event "+count).setValue(book);
                //Toast.makeText(getContext(), "Event booked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), payment.class);
                i.putExtra("amount", charges);
                startActivity(i);
            }
        });
        return root;
    }
}