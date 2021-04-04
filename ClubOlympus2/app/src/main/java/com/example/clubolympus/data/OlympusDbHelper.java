package com.example.clubolympus.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;

public class OlympusDbHelper extends SQLiteOpenHelper {

    public OlympusDbHelper(Context context){
        super(context, ClubOlympusContract.DATA_BASE_NAME, null, ClubOlympusContract.DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SQL_TABLE = "CREATE TABLE "
                + MemberEntry.TABLE_NAME
                + "(" + MemberEntry._ID + " INTEGER PRIMARY KEY,"
                + MemberEntry.FIRST_NAME + " TEXT,"
                + MemberEntry.LAST_NAME + " TEXT,"
                + MemberEntry.GENDER + " INTEGER NOT NULL,"
                + MemberEntry.KIND_OF_SPORT + " TEXT)";

        db.execSQL(CREATE_SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String UPDATE_SQL_TABLE = "DROP TABLE IF EXISTS" + MemberEntry.TABLE_NAME;
        this.onCreate(db);
    }


}
