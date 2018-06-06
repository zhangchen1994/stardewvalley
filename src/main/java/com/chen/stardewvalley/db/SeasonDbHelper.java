package com.chen.stardewvalley.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zc on 2018/6/4.
 */

public class SeasonDbHelper extends SQLiteOpenHelper {
    public SeasonDbHelper(Context context) {
        super(context, "season_db.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table season_things(_id integer primary key autoincrement,data," +
                "things)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
