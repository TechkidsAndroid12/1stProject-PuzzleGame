package com.example.mypc.a15puzzlegametechkids.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bùm on 3/30/2018.
 */

public class DataManager {
    private static final String TAG = "DataManager";

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper sqLiteHelper;

    public DataManager(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
    }

    public void NewScore(String name, String time, int move) {
        List<ScoreModel> scoreModelList = getAllItems();
        //Log.d(TAG, "NewScore: " + scoreModelList);
        deleteAllItem();

        scoreModelList.add(new ScoreModel(name, time, move));

        for (int i = 0; i < scoreModelList.size(); i++)
            for (int j = i+1; j < scoreModelList.size(); j++)
                if (scoreModelList.get(i).compareTo(scoreModelList.get(j)))
                    Collections.swap(scoreModelList, i, j);

        for (int i = 0; i < Math.min(5, scoreModelList.size()); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", scoreModelList.get(i).name);
            contentValues.put("time", scoreModelList.get(i).time);
            contentValues.put("move", scoreModelList.get(i).move);
            sqLiteDatabase.insert(SQLiteHelper.TABLE, null, contentValues);
        }
    }

    public List<ScoreModel> getAllItems() {
        List<ScoreModel> ScoreModelList = new ArrayList<ScoreModel>();

        Cursor cursor = sqLiteDatabase.query(sqLiteHelper.TABLE, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ScoreModel ScoreModel = cursorToScoreModel(cursor);
            ScoreModelList.add(ScoreModel);
            cursor.moveToNext();
        }
        cursor.close();
        return ScoreModelList;
    }

    private ScoreModel cursorToScoreModel(Cursor cursor) {
        ScoreModel ScoreModel = new ScoreModel(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3));
        return ScoreModel;
    }

    public void deleteAllItem() {
        sqLiteDatabase.delete(sqLiteHelper.TABLE, null, null);
    }
}
