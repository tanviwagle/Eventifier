package com.example.eventifier;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class fragment_events extends Fragment {

    RecyclerView rv;
    Toolbar toolbar;
    LinearLayoutManager llm;
    RecyclerViewAdapter adapter;
    List<Events> event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        toolbar = root.findViewById(R.id.toolbar);
        rv = root.findViewById(R.id.my_recycler_view);
        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
        return root;
    }


    private void initializeData() {
        event = new ArrayList<>();
        event.add(new Events("Game of codes", "Coding competition", "Free", "10th October", "1.30pm", "Taqneeq"));
        event.add(new Events("The App Evolution", "Coding competition", "Free", "10th October", "2.30pm", "Taqneeq"));
        event.add(new Events("Rasberry Pie", "Worshop", "Free", "11th October", "4.30pm", "Taqneeq"));
    }

    private void initializeAdapter() {
        adapter = new RecyclerViewAdapter(event);
        rv.setAdapter(adapter);
    }
}