package com.bttoy.service_task_template;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyService extends Service {
    protected static final String TAG = "MyService";
    private Handler handler = null;
    private Thread worker = null;
    private static final String CHANNEL_ID = "001";
    private NotificationManager mNotification = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        /*
            Wanting a starting server: binding is of no use.
         */
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mNotification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        handler = new Handler();
        worker = new Thread(new Runnable(){
            @Override
            public void run() {
                Log.d(TAG, "Running service job!");
                tellMain("Service: Hello from Service!!");
                handler.postDelayed(this, 20000l);
            }
        });
        if (startServiceJob()){
            Log.d(TAG, "Job started!");
            tellMain("Debug: Job started!");
        }
        else {
            Log.d(TAG, "Some problem happened with worker!");
            tellMain("Debug: some problem happened with worker!");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        stopServiceJob();
        super.onDestroy();
    }

    private boolean startServiceJob(){
        worker.start();
        if (!worker.isAlive()){
            return false;
        }
        return true;
    }

    private void stopServiceJob(){
        if (worker.isAlive()){
            try {
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handler.removeCallbacksAndMessages(null);
    }

    private void tellMain(String msg){
        Intent intent = new Intent("SERVICE_UPDATE");
        // You can also include some extra data.
        intent.putExtra("update", msg);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        showNotification("Service", msg);
    }

    private void showNotification(final String title, final String msg) {
        /*
            Finalmente la notification worka. E' obbligatoria una icon... (setSmallIcon)
         */
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notify_example_icon)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .build();
        if (mNotification != null) mNotification.notify(0, notification);
    }
}
