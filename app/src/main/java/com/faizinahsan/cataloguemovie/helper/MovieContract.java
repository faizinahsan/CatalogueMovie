package com.faizinahsan.cataloguemovie.helper;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {
    public static final String AUTHORITY = "com.faizinahsan.cataloguemovie";
    private static final String SCHEME = "content";
    private MovieContract(){}

    public static final class MovieColumns implements BaseColumns {
        public static final String TABLE_NAME = "tmovie";
        public static final String ID = _ID;
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String IMAGE = "image";
        public static final String ID_MOVIE = "id_movie";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
