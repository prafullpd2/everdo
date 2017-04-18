package com.dawnofneo.everdo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Prafull on 03-Apr-17.
 */

public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.MyViewHolder> {

    LayoutInflater inflater;
    Context context;
    List<ReminderElements> elements = Collections.emptyList();
//    ReminderDatabaseAdapter databaseAdapter = new ReminderDatabaseAdapter(context);

    public ReminderRecyclerAdapter(Context context, List<ReminderElements> elements) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.elements = elements;
    }

    public void deleteReminderCard(int posiiton){
        ReminderDatabaseAdapter databaseAdapter = new ReminderDatabaseAdapter(context);

        ReminderElements element = elements.get(posiiton);

        databaseAdapter.deleteReminder(Integer.parseInt(element.localID));

        elements.remove(posiiton);
        notifyItemRemoved(posiiton);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.reminder_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ReminderElements currentElement = elements.get(position);
        holder.taskOverview.setText(currentElement.taskOverview);
        holder.subtasks.setText(currentElement.subtasks);
        holder.notifyAt.setText(currentElement.notifyAt);
        holder.taskLocation.setText(currentElement.taskLocation);
        holder.deleteTask.setText(currentElement.deleteTask);
        holder.isDone.setText(currentElement.isDone);
        holder.startAt.setText(currentElement.startAt);
        holder.endAt.setText(currentElement.endAt);


    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView taskOverview;
        TextView subtasks;
        TextView notifyAt;
        TextView taskLocation;
        TextView deleteTask;
        TextView isDone;
        TextView startAt;
        TextView endAt;

        public MyViewHolder(View itemView) {
            super(itemView);
            taskOverview = (TextView) itemView.findViewById(R.id.card_task_overview);
            subtasks = (TextView) itemView.findViewById(R.id.card_subtasks);
            notifyAt = (TextView) itemView.findViewById(R.id.card_notify_time);
            taskLocation = (TextView) itemView.findViewById(R.id.card_task_location);
            deleteTask = (TextView) itemView.findViewById(R.id.card_delete);
            isDone = (TextView) itemView.findViewById(R.id.card_is_done);
            startAt = (TextView) itemView.findViewById(R.id.card_starting_time);
            endAt = (TextView) itemView.findViewById(R.id.card_end_time);
            deleteTask.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            deleteReminderCard(getAdapterPosition());
        }
    }
}
