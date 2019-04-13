package com.faizinahsan.cataloguemovie.provider;

import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.faizinahsan.cataloguemovie.DAO.MovieDAO;
import com.faizinahsan.cataloguemovie.database.AppDatabase;
import com.faizinahsan.cataloguemovie.database.DatabaseContract.NoteColumns;
import com.faizinahsan.cataloguemovie.model.MovieFavorite;

import static com.faizinahsan.cataloguemovie.database.DatabaseContract.AUTHORITY;

public class MovieFavProvider extends ContentProvider {

    AppDatabase db;

    /** The match code for some items in the Cheese table. */
    private static final int CODE_MOVIE_DIR = 1;

    /** The match code for an item in the Cheese table. */
    private static final int CODE_MOVIE_ITEM = 2;

    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, NoteColumns.TABLE_NAME, CODE_MOVIE_DIR);
        MATCHER.addURI(AUTHORITY, NoteColumns.TABLE_NAME + "/*", CODE_MOVIE_ITEM);
    }
    @Override
    public boolean onCreate() {
        db = Room.databaseBuilder(getContext(), AppDatabase.class,"moviefavdb").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        return true;
    }

    @Override
    public Cursor query(Uri uri,String[] projection,String selection,String[] selectionArgs,String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            final Cursor cursor;
            if (code == CODE_MOVIE_DIR) {
                cursor = db.movieDAO().selectAllMoviesFavs();
                Log.d("TAG","MASUK KE CODE = 1");
            } else {
                cursor = db.movieDAO().selectSingleMovieFav(ContentUris.parseId(uri));
                Log.d("TAG","MASUK KE CODE = 2");
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri,  ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                //mengambil objek movie favorite yg telah di instansiasi di MovieFavorite Class
                final long id = db.movieDAO().insertMovieFavorite(MovieFavorite.fromContentValues(values));
//                final long id = SampleDatabase.getInstance(context).cheese()
//                        .insert(Cheese.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_MOVIE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri,String selection,String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri,ContentValues values,String selection,String[] selectionArgs) {
        return 0;
    }
}
