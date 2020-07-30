package com.bttoy.service_task_template;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    protected static final String TAG = "MainActivity";
    private static final String CHANNEL_ID = "001";
    private NotificationManager mNotification = null;
    private Switch taskSw, serviceSw;
    private boolean compliant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskSw = findViewById(R.id.taskSwitch);
        serviceSw = findViewById(R.id.serviceSwitch);

    }
}
