package com.faizinahsan.cataloguemovie.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.faizinahsan.cataloguemovie.model.MovieFavorite;

@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovieFavorite(MovieFavorite movieFavorite);
    @Query("SELECT * FROM tmovie")
    MovieFavorite[] selectAllMoviesFavs();
    @Query("SELECT * FROM tmovie WHERE id = :id LIMIT 1")
    MovieFavorite selectSingleMovieFav(int id);
    @Query("SELECT * FROM tmovie WHERE id_movie = :id_movie LIMIT 1")
    MovieFavorite selectIdSingleMovieFav(String id_movie);
    @Delete
    int deleteBarang(MovieFavorite movieFavorite);
}
