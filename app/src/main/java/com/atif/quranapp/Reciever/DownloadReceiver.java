package com.atif.quranapp.Reciever;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.atif.quranapp.Activities.DetailsActivity;
import com.atif.quranapp.Services.DownloadService;

import static com.atif.quranapp.Activities.DetailsActivity.PUBLIC_SHARED_PREF;

public class DownloadReceiver extends ResultReceiver {
    DetailsActivity detailsActivity;

    public DownloadReceiver(Handler handler,DetailsActivity detailsActivity) {
        super(handler);
        this.detailsActivity = detailsActivity;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        super.onReceiveResult(resultCode, resultData);

        if (resultCode == DownloadService.UPDATE_PROGRESS) {

            int progress = resultData.getInt("progress");
            //get the progress
            //dialog.setProgress(progress);

            if (progress == 100) {
                try {
                    Log.e("Downloaded",detailsActivity.nameList.get(detailsActivity.downloadingIndex-1));
                    SharedPreferences sharedPreferences = detailsActivity.getSharedPreferences(PUBLIC_SHARED_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(detailsActivity.nameList.get(detailsActivity.downloadingIndex-1),true);
                    editor.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (detailsActivity.downloadingIndex < detailsActivity.playlist.size()){
                    detailsActivity.downloadMedia();
                }

                //dialog.dismiss();
            }
        }
    }
}
