package com.chen.stardewvalley.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zc on 2018/5/14.
 */

public class SpUtils {
    static  SharedPreferences sp;
    public static void setSpString(Context context,String htmlkey,String htmlstr){
        sp = context.getSharedPreferences("config.xml",Context.MODE_PRIVATE);
        sp.edit().putString(htmlkey,htmlstr).commit();
    }
    public static String getSpString(Context context,String key){
        sp = context.getSharedPreferences("config.xml",Context.MODE_PRIVATE);
        return sp.getString(key,null);
    }
}
