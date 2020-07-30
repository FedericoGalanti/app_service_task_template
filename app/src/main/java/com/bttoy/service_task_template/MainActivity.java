package com.bttoy.service_task_template;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected static final String TAG = "MainActivity";
    private static final String CHANNEL_ID = "001";
    private NotificationManager mNotification = null;
    private BroadcastReceiver receiver = null;
    private Switch taskSw, serviceSw;
    private boolean compliant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskSw = findViewById(R.id.taskSwitch);
        serviceSw = findViewById(R.id.serviceSwitch);

        /*
            Setting up listeners for switches
         */
        taskSw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                //TODO: activate task
            } else {
                //TODO: terminate task
            }
        });

        serviceSw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                startService(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                stopService(intent);
            }
        });

        /*
            Setting up BReceiver into a LocalBroadcastManager
         */
        createNotificationChannel();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String[] message = intent.getStringExtra("update").split(":");
                showNotification(message[0], message[1].trim());
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiver, new IntentFilter("SERVICE_UPDATE"));
    }

    @Override
    protected void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiver, new IntentFilter());
    }

    @Override
    protected void onPause(){
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(receiver);
    }

    private void createNotificationChannel(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager mNotification = getSystemService(NotificationManager.class);
            if (mNotification!=null) mNotification.createNotificationChannel(channel);
            else Toast.makeText(this, "Notification manager is null",Toast.LENGTH_SHORT);
        }
    }
    private void showNotification(final String title, final String msg) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .build();
        if(mNotification!= null) mNotification.notify(0, notification);
    }

}
