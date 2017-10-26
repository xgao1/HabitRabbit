package com.example.cmput301f17t30.habitrabbit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * arankin on 26/10/17.
 */

public class HabitLayoutAdapter extends RecyclerView.Adapter<HabitLayoutAdapter.ViewHolder>  {

    private ArrayList<Habit> habitList;

    public HabitLayoutAdapter(ArrayList<Habit> habitList, Context context) {
        this.habitList = habitList;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //this is for clicking on the habit items itself
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .habit_row_layout, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(HabitLayoutAdapter.ViewHolder holder, final int position) {
        final Habit habit = habitList.get(position);
        String title = habit.getTitle();


        /**
         * // this is a button that appears on each habit item
        Button button1 = holder.itemView.findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do stuff
                notifyItemChanged(position);

            }
        });

        holder.title.setText(title);
        **/
    }


    @Override
    public int getItemCount() {

        return habitList.size();
    }
}
