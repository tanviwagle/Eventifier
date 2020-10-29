package com.example.eventifier;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class fragment_book_event extends Fragment {
    RecyclerView rv;
    Toolbar toolbar;
    LinearLayoutManager llm;

    FirebaseRecyclerOptions<Book> options;
    FirebaseRecyclerAdapter<Book, BookHolder> adapter;

    DatabaseReference reff;

    TextView event_name, event_place, event_time, event_date;

    String sap_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_book_event, container, false);
        toolbar = root.findViewById(R.id.toolbar);
        rv = root.findViewById(R.id.recycler);

        event_name = root.findViewById(R.id.tvEventName);
        event_date = root.findViewById(R.id.tvdate);
        event_place = root.findViewById(R.id.tvplace);
        event_time = root.findViewById(R.id.tvtime);

        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(false);

        sap_id = this.getArguments().getString("sap_id");

        reff = FirebaseDatabase.getInstance().getReference().child("Booked_Events").child(sap_id);
        loadData();

        return root;
    }

    public void loadData(){
        options = new FirebaseRecyclerOptions.Builder<Book>().setQuery(reff.orderByChild("Name"), Book.class).build();
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