package com.example.clubolympus.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.clubolympus.data.ClubOlympusContract.*;

public class OlympusContentProvider extends ContentProvider {

    private static final String LOG = "LOG";

    private static final int MEMBERS = 1;
    private static final int MEMBERS_ID = 11;

    OlympusDbHelper olympusDbHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, MemberEntry.TABLE_NAME, MEMBERS);
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, MemberEntry.TABLE_NAME + "/#", MEMBERS_ID);
    }

    @Override
    public boolean onCreate() {
        olympusDbHelper = new OlympusDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = uriMatcher.match(uri);

        switch(match){
            case MEMBERS :
                return MemberEntry.CONTENT_MULTIPLE_ITEMS;
            case MEMBERS_ID :
                return MemberEntry.CONTENT_SINGLE_ITEM;
            default :
                throw new IllegalArgumentException("Incorrect URI" + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = olympusDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);

        switch(match){
            case MEMBERS :
                cursor = sqLiteDatabase.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MEMBERS_ID :
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default :
                throw new IllegalArgumentException("Incorrect URI" + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase sqLiteDatabase = olympusDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch(match){
            case MEMBERS :
                long id = sqLiteDatabase.insert(MemberEntry.TABLE_NAME, null, values);
                if (id == -1){
                    Log.e(LOG, "insertion uri " + uri + " is failed");
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default :
                throw new IllegalArgumentException("insertion uri " + uri + " is failed");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = olympusDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        int rowsDeleted;
        switch(match){
            case MEMBERS :
                rowsDeleted = sqLiteDatabase.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MEMBERS_ID :
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = sqLiteDatabase.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default :
                throw new IllegalArgumentException("Incorrect URI" + uri);
        }

        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = olympusDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        int rowsUpdated;
        switch(match){
            case MEMBERS :
                rowsUpdated = sqLiteDatabase.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MEMBERS_ID :
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = sqLiteDatabase.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default :
                throw new IllegalArgumentException("Incorrect URI" + uri);
        }

        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
