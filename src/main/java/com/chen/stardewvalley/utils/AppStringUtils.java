package com.chen.stardewvalley.utils;

/**
 * Created by zc on 2018/5/13.
 */

public class AppStringUtils {
    public static String getNewsUrl(int pager){
        return "http://news.baidu.com/ns?word=%E6%98%9F%E9%9" +
                "C%B2%E8%B0%B7%E7%89%A9%E8%AF%AD&pn="+20*pager+"&cl=2&ct=1&tn=news&rn" +
                "=20&ie=utf-8&bt=0&et=0";
    }
}
