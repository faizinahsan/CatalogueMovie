package com.faizinahsan.cataloguemovie.Helper;

import android.database.Cursor;

import com.faizinahsan.cataloguemovie.model.MovieFavorite;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<MovieFavorite> mapCursorToArrayList(Cursor cursor){
        ArrayList<MovieFavorite> list = new ArrayList<>();
        while (cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MovieFavorite.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MovieFavorite.COLUMN_NAME));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(MovieFavorite.COLUMN_IMAGE));
            String id_movie = cursor.getString(cursor.getColumnIndexOrThrow(MovieFavorite.COLUMN_ID_MOVIE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(MovieFavorite.COLUMN_OVERVIEW));
            list.add(new MovieFavorite(id,title,image,id_movie,overview));
        }
        return list;
    }
}
