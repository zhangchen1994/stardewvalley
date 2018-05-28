package com.chen.stardewvalley.utils;

import android.content.Context;

import com.chen.stardewvalley.domain.PeopleBean;
import com.chen.stardewvalley.domain.ToolsBean;
import com.chen.stardewvalley.domain.ValleyBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zc on 2018/5/24.
 */

public class JsonParse {
    public static ToolsBean toolsBean;
    public static PeopleBean peopleBean;
    public static ValleyBean valleyBean;

    public static void jsonTools(Context context){
        InputStream inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/"+"tools.json");
        String gsonStr = inToString(inputStream);
        Gson gson = new Gson();
        toolsBean = gson.fromJson(gsonStr,ToolsBean.class);
    }
    public static void jsonValley(Context context){
        InputStream inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/"+"valley.json");
        String gsonStr = inToString(inputStream);
        Gson gson = new Gson();
        valleyBean = gson.fromJson(gsonStr,ValleyBean.class);
    }
    public static void jsonPeople(Context context){
        InputStream inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/"+"people.json");
        String gsonStr = inToString(inputStream);
        Gson gson = new Gson();
        peopleBean = gson.fromJson(gsonStr,PeopleBean.class);
    }
    public static ToolsBean returnTools(){
        return toolsBean;
    }
    public static ValleyBean returnValley(){
        return valleyBean;
    }
    public static PeopleBean returnPeople(){
        return peopleBean;
    }
    private static String inToString(InputStream in){
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().trim();
    }
}
