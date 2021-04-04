package com.example.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Intent intent1 = intent;

        if (intent1.getAction()!= null && intent1.getAction().equals(MainActivity.NEW_ALARM)){
            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            })
        }

    }
}
