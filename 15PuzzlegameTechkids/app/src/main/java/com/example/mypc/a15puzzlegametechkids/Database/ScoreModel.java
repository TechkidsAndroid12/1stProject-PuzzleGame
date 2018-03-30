package com.example.mypc.a15puzzlegametechkids.Database;

import android.util.Log;

/**
 * Created by bùm on 3/30/2018.
 */

public class ScoreModel {
    private static final String TAG = "ScoreModel";
    String name;
    String time;
    int move;

    public ScoreModel(String name, String time, int move) {
        this.name = name;
        this.time = time;
        this.move = move;
    }

    @Override
    public String toString() {
        return "ScoreModel{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", move=" + move +
                '}';
    }
    //hh:mm:ss
    public int getValue(ScoreModel model) {
        int res = 0;
        String time = model.time.substring(0);

        while (time.contains(":")) {
            int i;
            for (i = 0; i < time.length(); i++)
                if (time.charAt(i) == ':') break;
            res = res*60 + Integer.parseInt(time.substring(0,i));
            time = time.substring(i+1);
        }

        res = res*60 + Integer.parseInt(time) + model.move*10;

        return res;
    }

    public boolean compareTo(ScoreModel other) {
        return (getValue(this) > getValue(other));
    }
}
