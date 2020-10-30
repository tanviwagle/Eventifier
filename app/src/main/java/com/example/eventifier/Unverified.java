package com.example.eventifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Unverified extends AppCompatActivity {


    RecyclerView rv;
    Toolbar toolbar;
    LinearLayoutManager llm;

    FirebaseRecyclerOptions<Book> options;
    FirebaseRecyclerAdapter<Book, BookHolder> adapter;

    DatabaseReference reff;

    TextView event_name, event_place, event_time, event_date;
    String cid, cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unverified);


        toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.recycler);

        event_name = findViewById(R.id.tvEventName);
        event_date = findViewById(R.id.tvdate);
        event_place = findViewById(R.id.tvplace);
        event_time = findViewById(R.id.tvtime);

        llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(false);

        cname = getIntent().getStringExtra("committeeName").toString();

        reff = FirebaseDatabase.getInstance().getReference().child("Events");
        loadData();



    }

    public void loadData(){
        options = new FirebaseRecyclerOptions.Builder<Book>().setQuery(reff.orderByChild("Verified").equalTo("NO"), Book.class).build();
        adapter = new FirebaseRecyclerAdapter<Book, BookHolder>(options) {
            @NonNull
            @Override
            public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_booked_events, parent,false);
                return new BookHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull BookHolder holder, int i, @NonNull Book model) {
                holder.event_name.setText(model.getName().toUpperCase());
                holder.event_place.setText(model.getPlace());
                holder.event_date.setText(model.getDate());
                holder.event_time.setText(model.getTime());
            }


        };

        adapter.startListening();
        rv.setAdapter(adapter);
    }
}