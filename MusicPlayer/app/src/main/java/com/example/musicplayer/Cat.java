package com.example.musicplayer;

import android.util.Log;

public class Cat {
    String name;
    int age;
    private static int count = 0;

    public Cat(){
        count++;
    }

    void showCountOfInstance(){
        Log.i("countOfInstance", "" + count);
    }
}
