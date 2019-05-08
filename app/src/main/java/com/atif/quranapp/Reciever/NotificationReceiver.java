package com.atif.quranapp.Reciever;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.atif.quranapp.Helper.Shared.MEDIA_PLAYER;
import static com.atif.quranapp.Helper.Shared.NOTIFICATION_ID;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getStringExtra("action");
        if(action.equals("stop")) {
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(NOTIFICATION_ID);
            MEDIA_PLAYER.reset();
        }
    }
}
