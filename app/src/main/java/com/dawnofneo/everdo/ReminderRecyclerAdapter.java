package com.dawnofneo.everdo;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Prafull on 03-Apr-17.
 */

public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.MyViewHolder> {

    LayoutInflater inflater;
    Context context;
    List<ReminderElements> elements = Collections.emptyList();
    NotificationManager notificationManager;
    boolean isNotificationActive;
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

        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd/MMM HH:mm");

        ReminderElements currentElement = elements.get(position);
        holder.taskOverview.setText("TASK-"+currentElement.taskOverview);
        holder.subtasks.setText("SUBTASK-"+currentElement.subtasks);
        try {
            holder.notifyAt.setText("at"+format.format(Long.parseLong(currentElement.notifyAt)));
            holder.startAt.setText("From"+format.format(Long.parseLong(currentElement.startAt)));
            holder.endAt.setText("to"+format.format(Long.parseLong(currentElement.endAt)));
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.taskLocation.setText("LOCATION"+currentElement.taskLocation);
        holder.deleteTask.setText(currentElement.deleteTask);
        holder.isDone.setText("isDone"+currentElement.isDone);

        if((currentElement.notifyAt!=null||currentElement.notifyAt!="")&&Long.parseLong(currentElement.notifyAt)>new Date().getTime()){
//            AlarmManager alarms = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//
//            ReminderNotificationReceiver receiver = new ReminderNotificationReceiver();
//            IntentFilter filter = new IntentFilter("ALARM_ACTION");
////            registerReceiver(receiver, filter);
//            context.registerReceiver(receiver,filter);
//
//            Intent intent = new Intent("ALARM_ACTION");
//            intent.putExtra("param", "TASK - "+currentElement.taskOverview);
//            PendingIntent operation = PendingIntent.getBroadcast(context, Integer.parseInt(currentElement.localID), intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            // I choose 3s after the launch of my application
//            alarms.set(AlarmManager.RTC_WAKEUP, Long.parseLong(currentElement.notifyAt), operation) ;

//
//            Intent moreInfoIntent = new Intent(context, ReminderNotificationReceiver.class);
//            Toast.makeText(context, "InComming Msg", Toast.LENGTH_SHORT).show();
//            moreInfoIntent.putExtra("CONTENT_TITLE","New Task")
//                    .putExtra("CONTENT_TEXT",currentElement.taskOverview)
//                    .putExtra("NOTIFICATION_ID",Integer.parseInt(currentElement.localID));
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            alarmManager.set(AlarmManager.RTC_WAKEUP,
//                    Long.parseLong(currentElement.notifyAt),
//                    PendingIntent.getBroadcast(context, 1, moreInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        }


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
