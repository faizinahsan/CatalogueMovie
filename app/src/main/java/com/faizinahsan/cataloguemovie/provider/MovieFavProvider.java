package com.faizinahsan.cataloguemovie.provider;

import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.faizinahsan.cataloguemovie.database.AppDatabase;
import com.faizinahsan.cataloguemovie.model.MovieFavorite;

import org.jetbrains.annotations.NotNull;

public class MovieFavProvider extends ContentProvider {
//    /** The authority of this content provider. */
//    public static final String AUTHORITY = "com.example.android.contentprovidersample.provider";
//
//    /** The URI for the Cheese table. */
//    public static final Uri URI_CHEESE = Uri.parse(
//            "content://" + AUTHORITY + "/" + Cheese.TABLE_NAME);
//
//    /** The match code for some items in the Cheese table. */
//    private static final int CODE_CHEESE_DIR = 1;
//
//    /** The match code for an item in the Cheese table. */
//    private static final int CODE_CHEESE_ITEM = 2;
//
//    /** The URI matcher. */
//    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
//
//    static {
//        MATCHER.addURI(AUTHORITY, Cheese.TABLE_NAME, CODE_CHEESE_DIR);
//        MATCHER.addURI(AUTHORITY, Cheese.TABLE_NAME + "/*", CODE_CHEESE_ITEM);
//    }
    //Authorities
    public static final String AUTHORITY = "com.faizinahsan.cataloguemovie.provider";
    //table name
    public static final String BASE_PATH = "tmovie";
    //URI for Movie Favorite Table
    public static final Uri URI_MOVIEFAV= Uri.parse("content://"+AUTHORITY+"/"+BASE_PATH);
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH,MOVIE);
        uriMatcher.addURI(AUTHORITY,BASE_PATH+"/#",MOVIE_ID);
    }

    AppDatabase db;
    Context context;
    @Override
    public boolean onCreate() {
        db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,"moviefavdb").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        context = getContext();
        return true;
    }

    @Override
    public Cursor query(@NotNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        final int code = uriMatcher.match(uri);
        if (code==MOVIE || code==MOVIE_ID){
            if (code == MOVIE){
                cursor = db.movieDAO().selectAllMoviesFavs();
            }else{
                cursor = db.movieDAO().selectSingleMovieFav(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(),uri);
            return cursor;
        }else{
            throw new IllegalArgumentException("Unknown URI:"+uri);
        }
    }

    @Override
    public String getType(@NotNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NotNull Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)){
            case MOVIE :
                final long id = db.movieDAO().insertMovieFavorite(MovieFavorite.fromContentValues(values));
                context.getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,id);
            case MOVIE_ID:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
        }
    }

    @Override
    public int delete(@NotNull Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)){
            case MOVIE:
                throw new IllegalArgumentException("Invalid URI, cannot delete without ID" + uri);
            case MOVIE_ID:
                final int count = db.movieDAO().deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri,null);
                return count;
            default:
                throw new IllegalArgumentException("Invalid URI, cannot delete without ID" + uri);
        }
    }

    @Override
    public int update(@NotNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)){
            case MOVIE:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID"+uri);
            case MOVIE_ID:
                final MovieFavorite movieFavorite = MovieFavorite.fromContentValues(values);
                movieFavorite.setId(ContentUris.parseId(uri));
                final int count = db.movieDAO().update(movieFavorite);
                context.getContentResolver().notifyChange(uri,null);
                return count;
            default:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID"+uri);
        }
    }
}
