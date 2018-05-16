package com.chen.stardewvalley.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chen.stardewvalley.domain.NewsBean;

import java.util.ArrayList;

/**
 * Created by zc on 2018/5/14.
 */

public class HouseDb{
    private static final String TITLE = "title";
    private static final String IMAGE_URL = "image_url";
    private static final String DATE = "date";
    private static final String CONTENT_URL = "content_url";
    private static final String TABLE = "house";

    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;
    private HouseDb(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }
    private static HouseDb houseDb = null;
    public static HouseDb getInstance(Context context){
        if(houseDb == null){
            houseDb = new HouseDb(context);
        }
        return houseDb;
    }
    public void insert(String title,String imageUrl,String Date,String contentUrl){
        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE,title);
        contentValues.put(IMAGE_URL,imageUrl);
        contentValues.put(DATE,Date);
        contentValues.put(CONTENT_URL,contentUrl);

        db.insert(TABLE,null,contentValues);
        db.close();
    }
    public boolean find(String contentUrl){
        boolean result = false;
        db = dbHelper.getWritableDatabase();
         Cursor cursor = db.query(TABLE,null,"content_url = ?",
                new String[]{contentUrl},null,null,null);
         if(cursor.moveToNext()){
             result = true;
         }
         db.close();
         cursor.close();
        return result;
    }
    public void delete(String contentUrl){
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE,"content_url = ?",new String[]{contentUrl});
        db.close();
    }
    public ArrayList<NewsBean> findAll(){
        db = dbHelper.getWritableDatabase();

        ArrayList<NewsBean> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String title = cursor.getString(1);
            String imageUrl = cursor.getString(2);
            String date = cursor.getString(3);
            String url = cursor.getString(4);
            NewsBean newsBean = new NewsBean();

            newsBean.setTitle(title);
            newsBean.setImageUrl(imageUrl);
            newsBean.setDate(date);
            newsBean.setUrl(url);

            list.add(newsBean);
        }

        return list;
    }
}
