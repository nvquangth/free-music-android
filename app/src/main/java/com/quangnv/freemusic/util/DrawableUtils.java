package com.quangnv.freemusic.util;

import android.content.Context;

/**
 * Created by quangnv on 17/10/2018
 */

public class DrawableUtils {

    private static final String RESOURCE_NAME = "drawable";

    public static int getResourceId(Context context, String variableName)
    {
        try {
            return context.getResources().getIdentifier(variableName, RESOURCE_NAME,
                    context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
