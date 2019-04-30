package com.atif.quranapp.Helper;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class FileOpener {
    public String loadJsonFromAssest(String file_Name, Context ctx){
        String json = null;
        try {
            InputStream is = ctx.getAssets().open(file_Name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
