package com.atif.quranapp.Reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.provider.Settings;

import com.atif.quranapp.Services.AzanService;

import java.io.IOException;

public class AlamBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, AzanService.class));
    }
}
