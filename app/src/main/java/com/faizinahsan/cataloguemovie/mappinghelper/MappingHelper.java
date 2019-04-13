package com.faizinahsan.cataloguemovie.mappinghelper;

import android.database.Cursor;

import com.faizinahsan.cataloguemovie.model.MovieFavorite;
import com.faizinahsan.cataloguemovie.database.DatabaseContract.NoteColumns;
import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<MovieFavorite> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<MovieFavorite> notesList = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            long id = notesCursor.getLong(notesCursor.getColumnIndexOrThrow(NoteColumns.ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(NoteColumns.TITLE));
            String overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow(NoteColumns.OVERVIEW));
            String image = notesCursor.getString(notesCursor.getColumnIndexOrThrow(NoteColumns.IMAGE));
            String id_movie = notesCursor.getString(notesCursor.getColumnIndexOrThrow(NoteColumns.ID_MOVIE));
            notesList.add(new MovieFavorite(id,title,image,overview,id_movie));
        }
        return notesList;
    }
}
