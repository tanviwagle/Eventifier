package com.example.eventifier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.eventholder> {

    List<Events> event;

    RecyclerViewAdapter(List<Events> event){
        this.event=event;
    }

    public static class eventholder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView name, desc, date, time, price, committee;

        public eventholder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            price = itemView.findViewById(R.id.price);
            committee = itemView.findViewById(R.id.comm);

        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.eventholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        eventholder holder = new eventholder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.eventholder holder, int position) {
        holder.name.setText(holder.name.getText()+": "+event.get(position).name);
        holder.desc.setText(holder.desc.getText()+": "+event.get(position).desc);
        holder.committee.setText(holder.committee.getText()+": "+event.get(position).committee);
        holder.date.setText(holder.date.getText()+": "+event.get(position).date);
        holder.time.setText(holder.time.getText()+": "+event.get(position).time);
        holder.price.setText(holder.price.getText()+": "+event.get(position).price);
    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
