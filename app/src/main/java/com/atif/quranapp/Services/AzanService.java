package com.atif.quranapp.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.atif.quranapp.R;
import com.atif.quranapp.Reciever.NotificationReceiver;

import java.io.IOException;

import static com.atif.quranapp.Helper.Shared.MEDIA_PLAYER;
import static com.atif.quranapp.Helper.Shared.NOTIFICATION_ID;

public class AzanService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        MediaPlayer mp = new MediaPlayer();
        MEDIA_PLAYER = mp;
        AssetFileDescriptor descriptor = null;
        try {
            descriptor = getAssets().openFd("azan.mp3");
            mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mp.prepare();
            Intent intent = new Intent(this, NotificationReceiver.class);
            intent.putExtra("action","stop");
            PendingIntent pIntent;
            pIntent = PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            Notification n  = new Notification.Builder(this)
                    .setContentTitle("Azan Time")
                    .setContentText("Subject")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_azan_reminder,"Stop", pIntent)
                    .build();


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(NOTIFICATION_ID, n);

            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
