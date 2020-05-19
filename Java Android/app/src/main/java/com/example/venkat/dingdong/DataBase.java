package com.example.venkat.dingdong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by venkat on 30/4/18.
 */

public class DataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Bookmark.db";
    public static final String TABLE_NAME = "Favourite";
    public static final String CONTEST_NAME = "name";
    public static final String CONTEST_START = "start";
    public static final String CONTEST_END = "finish";


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Favourite" + "(name text,start text,finish text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Favourite");
        onCreate(db);
    }

    public void BookMark(String name, String start, String finish){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("start",start);
        contentValues.put("finish",finish);
        db.insert("Favourite",null,contentValues);
    }

    public ArrayList<ContestInfo> getAllContest(){
        ArrayList<String> array_list = new ArrayList<String>();
        ArrayList<ContestInfo> al = new ArrayList<ContestInfo>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Favourite",null);
        res.moveToFirst();
        while(res.isAfterLast()==false){
           // array_list.add(res.getString(res.getColumnIndex(TABLE_NAME)));
            array_list.add(res.getString(0));
            al.add(new ContestInfo(res.getString(0),res.getString(1),res.getString(2)));
            res.moveToNext();
        }
        return al;
    }
}
