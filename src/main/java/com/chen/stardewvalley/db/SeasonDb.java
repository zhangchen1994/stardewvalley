package com.chen.stardewvalley.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/4.
 */

public class SeasonDb {
    private static final String TABLE = "season_things";

    private SeasonDbHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;
    private SeasonDb(Context context){
        this.context = context;
        dbHelper = new SeasonDbHelper(context);
    }
    private static SeasonDb seasonDb = null;
    public static SeasonDb getInstance(Context context){
        if(seasonDb == null){
            seasonDb = new SeasonDb(context);
        }
        return seasonDb;
    }
    public void insert(String data,String things){
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("data",data);
        contentValues.put("things",things);

        db.insert(TABLE,null,contentValues);
        db.close();
    }
    public boolean find(String data){
        boolean result = false;
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE,null,"data = ?",
                new String[]{data},null,null,null);
        if(cursor.moveToNext()){
            result = true;
        }
        db.close();
        cursor.close();
        return result;
    }
    public void delete(String things){
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE,"things = ?",new String[]{things});
        db.close();
    }
    public ArrayList<String> findThings(String data){
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE,null,"data = ?",new String[]{data},
                null,null,null);
        ArrayList<String> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String result = cursor.getString(2);

            list.add(result);
        }
        return list;
    }
}
