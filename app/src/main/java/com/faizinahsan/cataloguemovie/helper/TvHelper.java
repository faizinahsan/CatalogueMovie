package com.faizinahsan.cataloguemovie.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.faizinahsan.cataloguemovie.helper.TvContract.TvColumn.ID;
import static com.faizinahsan.cataloguemovie.helper.TvContract.TvColumn.ID_MOVIE;
import static com.faizinahsan.cataloguemovie.helper.TvContract.TvColumn.TABLE_NAME;


public class TvHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private final MovieDatabaseHelper dataBaseHelper;
    private static TvHelper INSTANCE;
    private SQLiteDatabase database;

    public TvHelper(Context context) {
        dataBaseHelper = new MovieDatabaseHelper(context);
    }
    public static TvHelper getInstance(Context context) {
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new TvHelper(context);
                }
            }
        }
        return INSTANCE;
    }


    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }
    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , ID_MOVIE + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, ID_MOVIE + " = ?", new String[]{id});
    }
}
