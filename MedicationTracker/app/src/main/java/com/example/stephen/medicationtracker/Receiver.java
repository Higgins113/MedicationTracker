package com.example.stephen.medicationtracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.view.ContextMenu;
import android.widget.Toast;

import junit.framework.Test;

import java.security.Provider;

/**
 * Created by Stephen on 13-03-2017.
 */
public class Receiver extends BroadcastReceiver {
    String username,medName;


    @Override
    public void onReceive(Context context, Intent intent) {

        username = intent.getStringExtra("username");
        medName = intent.getStringExtra("medname");

        //context.startService(intent1);
        long[] pattern = {0, 300, 0};
        Intent newIntent = new Intent(context,MedicationTaken.class);
        newIntent.putExtra("username", username);
        newIntent.putExtra("medName", medName);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(MedicationTaken.class);
        taskStackBuilder.addNextIntent(newIntent);
        PendingIntent resultPendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.drawable.logo);
        notificationBuilder.setContentTitle("MEDICATION REMINDER");
        notificationBuilder.setContentText(username + ": time to take medication: " + medName);
        notificationBuilder.setVibrate(pattern);


        notificationBuilder.setContentIntent(resultPendingIntent);
        notificationBuilder.setDefaults(android.app.Notification.DEFAULT_SOUND);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(username + ": time to take medication: " + medName));
               // .bigText("test"));
        notificationBuilder.addAction(R.mipmap.ic_launcher, "Taken", resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, notificationBuilder.build());

    }
}
