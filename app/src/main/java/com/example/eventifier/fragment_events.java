package com.example.eventifier;

import android.content.Intent;
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
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class fragment_events extends Fragment {

    RecyclerView rv;
    Toolbar toolbar;
    LinearLayoutManager llm;

    FirebaseRecyclerOptions<Events> options;
    FirebaseRecyclerAdapter<Events, EventViewHolder> adapter;

    DatabaseReference reff;
    Fragment f = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        toolbar = root.findViewById(R.id.toolbar);
        rv = root.findViewById(R.id.recycler);
        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(false);

        reff = FirebaseDatabase.getInstance().getReference().child("Events");

        loadData();

        return root;
    }

    public void loadData(){
        options = new FirebaseRecyclerOptions.Builder<Events>().setQuery(reff.orderByChild("Verified").equalTo("YES"), Events.class).build();
        adapter = new FirebaseRecyclerAdapter<Events, EventViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EventViewHolder holder, final int position, @NonNull Events model) {

                    holder.name.setText(model.getName().toUpperCase());
                    holder.cat.setText(model.getCategory());
                    Picasso.get().load(model.getImage()).into(holder.image);
                    f = new Event_details();
                    holder.v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, f).commit();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", getRef(position).getKey());
                            Log.d("id", getRef(position).getKey());
                            f.setArguments(bundle);
                        }
                    });
                }


            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_event, parent,false);
                return new EventViewHolder(v);
            }
        };

        adapter.startListening();
        rv.setAdapter(adapter);
    }

}