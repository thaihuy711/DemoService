package com.thaihuy.demoservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button startServiceButton, stopServiceButton, fastBackward,
            fastForward;
    private Intent intentService;
    private MyService myService;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        startServiceButton = (Button) findViewById(R.id.startServiceButton);
        stopServiceButton = (Button) findViewById(R.id.stopServiceButton);
        fastBackward = (Button) findViewById(R.id.fastBackward);
        fastForward = (Button) findViewById(R.id.fastForward);

        startServiceButton.setOnClickListener(this);
        stopServiceButton.setOnClickListener(this);
        fastBackward.setOnClickListener(this);
        fastForward.setOnClickListener(this);
        intentService = new Intent(this, MyService.class);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.startServiceButton:
                startService(intentService);
                /**
                 * Through a bindService command to establish a connection with the service, and the bindService command to convert the object(
                 * ServiceConnection interface below) named serviceConnection.
                 */
                bindService(intentService, serviceConnection,
                        Context.BIND_AUTO_CREATE);
                flag = true;
                break;
            case R.id.stopServiceButton:
                if (flag) {
                    unbindService(serviceConnection);
                    stopService(intentService);
                    flag = false;
                }

                break;

            case R.id.fastBackward:
                if (flag) {
                    myService.backwardFun();
                }

                break;

            case R.id.fastForward:
                if (flag) {
                    myService.forwardFun();
                }
                break;
            default:
                break;
        }
    }

    /**
     * ServiceConnection is an object of type ServiceConnection, which is an interface for monitoring, service binding state
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        /**
         * Click the open button, will call the serviceConnection object's onServiceConnected method.
         * Pass a IBinder object to the method
         * , Its practice is to create and submit the service itself. The IBinder object is of type SimpleAudioServiceBinder, we will create it in service.
         * It is a method used to return our service itself, a getService object, so that we can return to the method of direct operation.
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder sasBinder) {
            myService = ((MyService.SimpleAudioServiceBinder) sasBinder)
                    .getService();
        }

        /**
         * The method is used for processing and service disconnected situation.
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }

    };

}
