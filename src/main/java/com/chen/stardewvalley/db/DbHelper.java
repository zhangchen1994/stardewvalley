package com.chen.stardewvalley.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zc on 2018/5/14.
 */

public class DbHelper extends SQLiteOpenHelper{

    public DbHelper(Context context) {
        super(context, "sqlite.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table house(_id integer primary key autoincrement,title," +
                "image_url,date,content_url)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
