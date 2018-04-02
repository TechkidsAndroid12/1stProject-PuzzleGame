package com.example.mypc.a15puzzlegametechkids.Models;

/**
 * Created by MyPC on 26/03/2018.
 */

public class SpecialPuzzle {
    public int x;
    public int y;
    public int value;
    public boolean isEmpty;

    public SpecialPuzzle(int x, int y, int value, boolean isEmpty) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.isEmpty = isEmpty;
    }
}
