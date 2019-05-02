package com.atif.quranapp.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;

    public DownloadService() {
        super("DownloadService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        String urlToDownload = intent.getStringExtra("url");
        String name = intent.getStringExtra("name");
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        try {

            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();

            InputStream input = new BufferedInputStream(connection.getInputStream());

            FileOutputStream output = openFileOutput(name, Context.MODE_PRIVATE);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;

                // publishing the progress....
                Bundle resultData = new Bundle();
                resultData.putInt("progress" ,(int) (total * 100 / fileLength));
                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress" ,100);

        receiver.send(UPDATE_PROGRESS, resultData);
    }

}
