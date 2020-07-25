package com.studiofive.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void notifyMe(View view){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Test");
//        builder.setContentTitle("Title on page 1");
//        builder.setContentText("Contents on page 1");
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
//        managerCompat.notify(1, builder.build());
        createNotification();
    }

    public void createNotification(){
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

        }
        notificationManager.notify(5, builder.build());
    }
}
