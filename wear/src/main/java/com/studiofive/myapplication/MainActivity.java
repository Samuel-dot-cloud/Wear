package com.studiofive.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
    }

    public void notifyMe(View view){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Watch");
//        builder.setContentTitle("Title on page 1");
//        builder.setContentText("Contents on page 1");
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
//        managerCompat.notify(1, builder.build());
        createNotification();
    }

    public void createNotification(){
        Intent intent = new Intent(this, NotificationDetails.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String notificationMessage = getString(R.string.app_name);

        int requestId = (int)System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationMessage))
                .setContentText(notificationMessage)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "Anything_channel";
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
            builder.setContentIntent(pendingIntent);

        }
        notificationManager.notify(5, builder.build());
    }

    public void voiceInput(){
        Intent intent = new Intent(this, NotificationDetails.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String reply_label = getResources().getString(R.string.reply_lable);
        String[] reply_choices = getResources().getStringArray(R.array.reply_choices);

        RemoteInput.Builder remoteInputBuilder = new RemoteInput.Builder(NotificationUtils.EXTRA_VOICE_REPLY);
        remoteInputBuilder.setLabel(reply_label);
        remoteInputBuilder.setChoices(reply_choices);

        RemoteInput remoteInput = remoteInputBuilder.build();

        NotificationCompat.Action.Builder notificationActionBuilder = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "My voice", pendingIntent);
        notificationActionBuilder.addRemoteInput(remoteInput);

        NotificationCompat.Action action = notificationActionBuilder.build();

        String notificationMessage = getString(R.string.app_name);

        int requestId = (int)System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationMessage))
                .setContentText(notificationMessage)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "Anything_channel";
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
            builder.setContentIntent(pendingIntent);
            builder.extend(new NotificationCompat.WearableExtender().addAction(action));

        }
        notificationManager.notify(5, builder.build());
    }
}
