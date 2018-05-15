package com.thaihuy.demoservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service implements MediaPlayer.OnCompletionListener {

    private String TAG = "zhongyao";
    private MediaPlayer player;

    public class SimpleAudioServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    private final IBinder sasBinder = new SimpleAudioServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return sasBinder;
    }
    /**
     * Click on the MainActivity after open call startService open service,
     * The two method calls are as follows: onCreate(),onStartCommand()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate");

        player = MediaPlayer.create(this, R.raw.bangbang);
        player.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        if (!player.isPlaying()) {
            player.start();
        }
        return super.onStartCommand(intent, flags, startId);

    }
    /**
     * Call stopService to stop the service, will call onDestroy () method.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
    }
    /**
     * Play after the call
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        stopSelf();
    }
    /**
     * Service method: rewind
     */
    public void backwardFun() {
        if (player.isPlaying()) {
            player.seekTo(player.getCurrentPosition() - 2500);
        }
    }
    /**
     * Service: fast forward method
     */
    public void forwardFun() {
        if (player.isPlaying()) {
            player.seekTo(player.getCurrentPosition() + 2500);
        }
    }

}