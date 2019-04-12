package com.faizinahsan.cataloguemovie.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.faizinahsan.cataloguemovie.model.MovieFavorite;

@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovieFavorite(MovieFavorite movieFavorite);
    @Query("SELECT * FROM tmovie")
//    MovieFavorite[] selectAllMoviesFavs();
    Cursor selectAllMoviesFavs();
    @Query("SELECT * FROM tmovie WHERE "+MovieFavorite.COLUMN_ID+" = :id LIMIT 1")
//    MovieFavorite selectSingleMovieFav(int id);
    Cursor selectSingleMovieFav(long id);
    @Query("SELECT * FROM tmovie WHERE id_movie = :id_movie LIMIT 1")
//    MovieFavorite selectIdSingleMovieFav(String id_movie);
    MovieFavorite selectIdSingleMovieFav(String id_movie);
    @Delete
    int deleteBarang(MovieFavorite movieFavorite);
    @Query("DELETE FROM tmovie WHERE " + MovieFavorite.COLUMN_ID + " = :id")
    int deleteById(long id);
    @Update
    int update(MovieFavorite movieFavorite);
}
