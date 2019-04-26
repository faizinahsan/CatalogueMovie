package com.faizinahsan.cataloguemovie.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tmovie";

    private static final int DATABASE_VERSION = 3;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL, "+"%s INTEGER NOT NULL)",
            MovieContract.MovieColumns.TABLE_NAME,
            MovieContract.MovieColumns.ID,
            MovieContract.MovieColumns.TITLE,
            MovieContract.MovieColumns.OVERVIEW,
            MovieContract.MovieColumns.IMAGE,
            MovieContract.MovieColumns.ID_MOVIE
    );

    MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+MovieContract.MovieColumns.TABLE_NAME);
        onCreate(db);
    }
}
