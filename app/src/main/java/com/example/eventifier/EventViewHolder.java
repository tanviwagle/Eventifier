package com.example.eventifier;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView name, place, desc, date, time, price, comm, cat;
    View v;
    public EventViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.cardImage);
        name = itemView.findViewById(R.id.name);
        place = itemView.findViewById(R.id.place);
        desc = itemView.findViewById(R.id.desc);
        date = itemView.findViewById(R.id.date);
        time = itemView.findViewById(R.id.time);
        price = itemView.findViewById(R.id.price);
        comm = itemView.findViewById(R.id.comm);
        cat = itemView.findViewById(R.id.cat);

        v = itemView;
    }
}
