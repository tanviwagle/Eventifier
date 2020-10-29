package com.example.eventifier;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookHolder extends RecyclerView.ViewHolder {
    TextView event_name, event_place, event_time, event_date;

    public BookHolder(@NonNull View itemView) {
        super(itemView);
        event_name = itemView.findViewById(R.id.tvEventName);
        event_date = itemView.findViewById(R.id.tvdate);
        event_place = itemView.findViewById(R.id.tvplace);
        event_time = itemView.findViewById(R.id.tvtime);
    }
}
