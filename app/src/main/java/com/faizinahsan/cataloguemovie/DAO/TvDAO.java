package com.faizinahsan.cataloguemovie.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.faizinahsan.cataloguemovie.model.TvFavorite;

@Dao
public interface TvDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTvFavorite(TvFavorite tvFavorite);
    @Query("SELECT * FROM tvfavorite")
    TvFavorite[] selectAllTvFromFav();
    @Query("SELECT * FROM tvfavorite WHERE id =:id LIMIT 1")
    TvFavorite selectSingleTvFav(int id);
    @Query("SELECT * FROM tvfavorite WHERE id_tv =:id_tv LIMIT 1")
    TvFavorite selectSingleIdTvFav(String id_tv);
    @Delete
    int deleteTv(TvFavorite tvFavorite);
}
