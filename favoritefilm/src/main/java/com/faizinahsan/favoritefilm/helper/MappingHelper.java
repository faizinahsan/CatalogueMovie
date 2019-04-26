package com.faizinahsan.favoritefilm.helper;

import android.database.Cursor;


import com.faizinahsan.favoritefilm.model.MovieFav;

import java.util.ArrayList;

import static com.faizinahsan.favoritefilm.helper.MovieContract.MovieColumns.ID;
import static com.faizinahsan.favoritefilm.helper.MovieContract.MovieColumns.ID_MOVIE;
import static com.faizinahsan.favoritefilm.helper.MovieContract.MovieColumns.IMAGE;
import static com.faizinahsan.favoritefilm.helper.MovieContract.MovieColumns.OVERVIEW;
import static com.faizinahsan.favoritefilm.helper.MovieContract.MovieColumns.TITLE;

public class MappingHelper {
    public static ArrayList<MovieFav> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<MovieFav> notesList = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
            String overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow(OVERVIEW));
            String image = notesCursor.getString(notesCursor.getColumnIndexOrThrow(IMAGE));
            int id_movie = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(ID_MOVIE));
            notesList.add(new MovieFav(id, title, overview, image,id_movie));
        }
        return notesList;
    }
//    public static ArrayList<MovieFav> mapTvCursorToArrayList(Cursor notesCursor) {
//        ArrayList<MovieFav> notesList = new ArrayList<>();
//        while (notesCursor.moveToNext()) {
//            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(MovieContract.MovieColumns.ID));
//            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieContract.MovieColumns.TITLE));
//            String overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TvContract.TvColumn.OVERVIEW));
//            String image = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TvContract.TvColumn.IMAGE));
//            int id_movie = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(TvContract.TvColumn.ID_MOVIE));
//            notesList.add(new MovieFav(id, title, overview, image,id_movie));
//        }
//        return notesList;
//    }
}
