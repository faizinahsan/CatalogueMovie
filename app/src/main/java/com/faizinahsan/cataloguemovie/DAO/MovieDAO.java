package com.faizinahsan.cataloguemovie.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.faizinahsan.cataloguemovie.model.MovieFavorite;
import com.faizinahsan.cataloguemovie.database.DatabaseContract.NoteColumns;

@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovieFavorite(MovieFavorite movieFavorite);
    @Query("SELECT * FROM tmovie")
    Cursor selectAllMoviesFavs();
    @Query("SELECT * FROM "+ NoteColumns.TABLE_NAME +" WHERE "+NoteColumns.ID +" =:id LIMIT 1")
    Cursor selectSingleMovieFav(long id);

    @Query("SELECT * FROM "+ NoteColumns.TABLE_NAME + " WHERE "+ NoteColumns.ID+" = :id_movie LIMIT 1")
    Cursor selectIdSingleMovieFav(long id_movie);
//
//    @Query("SELECT * FROM "+ NoteColumns.TABLE_NAME + " WHERE "+ NoteColumns.ID_MOVIE+" = :id_movie LIMIT 1")
//    MovieFavorite selectIdMovieFav(String id_movie);

    @Delete
    int deleteBarang(MovieFavorite movieFavorite);

    @Query("DELETE FROM " + NoteColumns.TABLE_NAME + " WHERE " + NoteColumns.ID + " = :id")
    int deleteById(long id);
}
