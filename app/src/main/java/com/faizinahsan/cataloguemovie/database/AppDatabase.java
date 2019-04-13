package com.faizinahsan.cataloguemovie.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.faizinahsan.cataloguemovie.DAO.MovieDAO;
import com.faizinahsan.cataloguemovie.DAO.TvDAO;
import com.faizinahsan.cataloguemovie.model.MovieFavorite;
import com.faizinahsan.cataloguemovie.model.TvFavorite;

@Database(entities = {MovieFavorite.class, TvFavorite.class},version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDAO movieDAO();
    public abstract TvDAO tvDAO();
}
