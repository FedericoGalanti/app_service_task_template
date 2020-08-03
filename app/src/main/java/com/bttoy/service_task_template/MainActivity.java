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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    /*
        TODO: List component development (in referral to BTTOY: this will be use to list visible devices
        from BLE scanning
     */
    protected static final String TAG = "MainActivity";
    private BroadcastReceiver receiver = null;
    private Switch taskSw, serviceSw;
    private ArrayList<ListExampleItem> sauce = new ArrayList<>();
    private ListCustomAdapter listCustomAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskSw = findViewById(R.id.taskSwitch);
        serviceSw = findViewById(R.id.serviceSwitch);
        listView = findViewById(R.id.viewForLists);
        sauce.clear();
        sauce.add(new ListExampleItem("Dev", "Message", "Proto"));
        listCustomAdapter = new ListCustomAdapter(this, sauce);
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
                String[] message = Objects.requireNonNull(intent.getStringExtra("update")).split(":");
                updateList(message);
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiver, new IntentFilter("SERVICE_UPDATE"));
    }

    public void updateList(String[] message) {
        runOnUiThread(() -> {
            if (message[0].equals("In") || message[0].equals("Out")) {
                String[] newItemString = message[1].trim().split(" ");
                ListExampleItem newItem = new ListExampleItem(newItemString[0], newItemString[1], newItemString[2]);

                switch (message[0]) {
                    case "In": {
                        if (!sauce.contains(newItem)) {
                            sauce.add(newItem);
                            listCustomAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                    case "Out": {
                        sauce.remove(newItem);
                        listCustomAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
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
