package com.faizinahsan.cataloguemovie.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.faizinahsan.cataloguemovie.helper.MovieHelper;

import static com.faizinahsan.cataloguemovie.helper.MovieContract.AUTHORITY;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.CONTENT_URI;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.TABLE_NAME;

public class MovieFavProvider extends ContentProvider {
    private static final int Movie = 1;
    private static final int Movie_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;
    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, Movie);
        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", Movie_ID);
    }
    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        movieHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case Movie:
                cursor = movieHelper.queryProvider();
                break;
            case Movie_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        movieHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case Movie:
                added = movieHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        //Dicoding Version
//        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
        //Other Reference
//        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(Uri uri,String selection,String[] selectionArgs) {
        movieHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case Movie_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        //Dicoding version
//        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
        //Other Reference
//        getContext().getContentResolver().notifyChange(uri,null);
        return deleted;
    }

    @Override
    public int update(Uri uri,ContentValues values, String selection,String[] selectionArgs) {
        movieHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case Movie_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }
        //Dicoding Vesion
//        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
//        Other Reference
//        getContext().getContentResolver().notifyChange(uri,null);
        return updated;
    }
}
