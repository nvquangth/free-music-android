package com.quangnv.freemusic.data.source.local.asset;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by quangnv on 12/10/2018
 */

public class AssetsHelper {

    private static AssetsHelper sInstance;
    private Context mContext;

    private AssetsHelper(Context context) {
        mContext = context;
    }

    public static synchronized AssetsHelper getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new AssetsHelper(context);
        }
        return sInstance;
    }

    public String read(String path) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open(path), "UTF-8"));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                sb.append(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
