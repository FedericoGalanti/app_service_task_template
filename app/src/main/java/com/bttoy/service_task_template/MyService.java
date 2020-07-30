package com.bttoy.service_task_template;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyService extends Service {
    protected static final String TAG = "MyService";
    private Handler handler = null;
    private Thread worker = null;

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
        handler = new Handler();
        worker = new Thread(new Runnable(){
            @Override
            public void run() {
                /*
                    TODO: Il service va. Il problema è che non manda le notifiche. Perché? Investiga!
                 */
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
    }
}
