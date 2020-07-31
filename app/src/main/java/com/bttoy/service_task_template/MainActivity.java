package com.bttoy.service_task_template;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {
    protected static final String TAG = "MainActivity";
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
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Received intent!");
                String[] message = intent.getStringExtra("update").split(":");
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiver, new IntentFilter("SERVICE_UPDATE"));
    }

    @Override
    protected void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiver, new IntentFilter("SERVICE_UPDATE"));
    }

    @Override
    protected void onPause(){
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(receiver);
    }


}
