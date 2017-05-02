package com.dawnofneo.everdo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Date;

/**
 * Created by Prafull on 19-Apr-17.
 */

public class ReminderNotificationReceiver extends BroadcastReceiver {

    long timeToNotify;
    @Override
    public void onReceive(Context context, Intent intent) {
//        intent = new Intent(context, Transition.class);
//        long[] pattern = {0, 300, 0};
//        PendingIntent pi = PendingIntent.getActivity(context, 1234, intent, 0);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.ic_menu_camera)
//                .setContentTitle("Take Questionnaire")
//                .setContentText("Take questionnaire for Duke Mood Study.")
//                .setVibrate(pattern)
//                .setAutoCancel(true);
//
//        mBuilder.setContentIntent(pi);
//        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
//        mBuilder.setAutoCancel(true);
//        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(1234, mBuilder.build());
        timeToNotify = intent.getLongExtra("NOTIFICATION_TIME",new Date().getTime()+5000);
        createnotification(context,"New Task",intent.getStringExtra("CONTENT_TEXT"),"Alert",intent.getIntExtra("NOTIFICATION_ID",1));
    }

    private void createnotification(Context context, String msg, String msgText, String msgAlert,int notificID) {

        PendingIntent notificationIntent = PendingIntent.getActivity(context,0,new Intent(context,Transition.class),0);

        NotificationCompat.Builder notificationBuilder =  new   NotificationCompat.Builder(context)
                .setContentTitle(msg)
                .setContentText(msgText)
                .setTicker(msgAlert)
                .setSmallIcon(R.drawable.alert_icon);
        notificationBuilder.setContentIntent(notificationIntent);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificID,notificationBuilder.build());



    }
}
