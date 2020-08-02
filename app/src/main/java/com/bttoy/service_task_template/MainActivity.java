package com.bttoy.service_task_template;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /*
        TODO: List component development (in referral to BTTOY: this will be use to list visible devices
        from BLE scanning
     */
    protected static final String TAG = "MainActivity";
    private BroadcastReceiver receiver = null;
    private Switch taskSw, serviceSw;
    private ListCustomAdapter listCustomAdapter;
    private ArrayList<ListExampleItem> source;
    private ListView listView;
    private boolean compliant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        source = new ArrayList<ListExampleItem>();
        source.add(new ListExampleItem("Io", "Sono io", "Me meco"));
        setContentView(R.layout.activity_main);

        taskSw = findViewById(R.id.taskSwitch);
        serviceSw = findViewById(R.id.serviceSwitch);
        listView = findViewById(R.id.viewForLists);

        listCustomAdapter = new ListCustomAdapter(this, source);
        listView.setAdapter(listCustomAdapter);
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
