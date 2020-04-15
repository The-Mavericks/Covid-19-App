package com.example.chatbot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReciever extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Info","Reached alarmreciever");
        Intent i = new Intent(context, push_notification.class);
        context.startService(i);
    }
}
