package com.lo.sisyphuser.fish;

import android.content.Context;

import java.io.File;

/**
 * Created by xx on 2016/9/14.
 */
public class BaseCache {
    public static String getBitmapPath(Context context){
        String path = context.getExternalCacheDir().getAbsolutePath() + "/drawable";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path;
    }
}
