package com.example.mypc.a15puzzlegametechkids.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bùm on 3/30/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE = "HighScore";
    public static final String DATABASE = "HighScore.db";
    public static final int VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE +
                "( id integer primary key autoincrement, " +
                "name text, " +
                "time text, " +
                "move int);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }
}
