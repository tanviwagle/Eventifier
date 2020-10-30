package com.example.eventifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EventVerificationPage extends AppCompatActivity {

    TextView t,dt,time,plc,amt,desc,com;
    CardView c;
    DatabaseReference ref;
    ImageView imgv;
    String pdfPath,en;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_verification_page);


        t=(TextView) findViewById(R.id.eventName);
        imgv = (ImageView)findViewById(R.id.imgView);
        c = (CardView) findViewById(R.id.card1);
        dt=(TextView) findViewById(R.id.dt);
        time=(TextView) findViewById(R.id.time);
        plc=(TextView) findViewById(R.id.plc);
        amt=(TextView) findViewById(R.id.amt);
        desc=(TextView) findViewById(R.id.desc);
        com=(TextView) findViewById(R.id.com);


        Intent i = getIntent();
        en = i.getStringExtra("id");
        t.setText(en);
        ref = FirebaseDatabase.getInstance().getReference().child("Events");

        ref.child(en).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Picasso.get().load(snapshot.child("Image").getValue().toString()).into(imgv);
                    dt.setText(snapshot.child("Date").getValue().toString());
                    time.setText(snapshot.child("Time").getValue().toString());
                    plc.setText(snapshot.child("Place").getValue().toString());
                    amt.setText(snapshot.child("Charges").getValue().toString());
                    desc.setText(snapshot.child("Description").getValue().toString());
                    com.setText(snapshot.child("Committee").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}