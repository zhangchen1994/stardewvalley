package com.chen.stardewvalley.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by zc on 2018/5/16.
 */

public class GetImageIdByName {
    public static int getImageId(String name, Context context){
        ApplicationInfo appInfo = context.getApplicationInfo();
        int resID = context.getResources().getIdentifier(name, "drawable", appInfo.packageName);

        return  resID;
    }
}
