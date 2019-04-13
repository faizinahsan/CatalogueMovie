package com.faizinahsan.cataloguemovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.faizinahsan.cataloguemovie.helper.MovieHelper;
import com.faizinahsan.cataloguemovie.helper.TvHelper;

import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.CONTENT_URI;
import static com.faizinahsan.cataloguemovie.helper.TvContract.AUTHORITY;
import static com.faizinahsan.cataloguemovie.helper.TvContract.TvColumn.TABLE_NAME;

public class TvFavProvider extends ContentProvider {
    private static final int TV = 1;
    private static final int TV_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private TvHelper tvHelper;
    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, TV);
        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", TV_ID);
    }
    @Override
    public boolean onCreate() {
        tvHelper = TvHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri,String[] projection,String selection,String[] selectionArgs,String sortOrder) {
        tvHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case TV:
                cursor = tvHelper.queryProvider();
                break;
            case TV_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
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
    public Uri insert( Uri uri,ContentValues values) {
        tvHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case TV:
                added = tvHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        return Uri.parse(CONTENT_URI + "/" + added);

    }

    @Override
    public int delete(Uri uri,String selection,String[] selectionArgs) {
        tvHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case TV_ID:
                deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
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
    public int update(Uri uri,  ContentValues values,String selection, String[] selectionArgs) {
        tvHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case TV_ID:
                updated = tvHelper.updateProvider(uri.getLastPathSegment(), values);
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
