package com.example.clubolympus.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ClubOlympusContract {

    public static final String DATA_BASE_NAME = "myDb";
    public static final int DATA_BASE_VERSION = 1;

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.clubolympus";
    public static final Uri BASE_URI = Uri.parse(SCHEME + AUTHORITY);

    private ClubOlympusContract(){

    }

    public static final class MemberEntry implements BaseColumns {

        public static final String TABLE_NAME = "members";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, TABLE_NAME);

        public static final String _ID = BaseColumns._ID;
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String GENDER = "gender";
        public static final String KIND_OF_SPORT = "kindOfSport";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final String CONTENT_MULTIPLE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;
    }
}
