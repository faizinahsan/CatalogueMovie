package com.faizinahsan.cataloguemovie.fragment;


import android.arch.persistence.room.Room;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faizinahsan.cataloguemovie.Helper.MappingHelper;
import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.adapter.FavAdapter;
import com.faizinahsan.cataloguemovie.adapter.MoviesRVAdapter;
import com.faizinahsan.cataloguemovie.database.AppDatabase;
import com.faizinahsan.cataloguemovie.model.MovieFavorite;
import com.faizinahsan.cataloguemovie.provider.MovieFavProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavAdapter adapter;
    private ArrayList<MovieFavorite> movies = new ArrayList<>();
    private AppDatabase db;

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = Room.databaseBuilder(getContext(),AppDatabase.class,"moviefavdb").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        recyclerView = view.findViewById(R.id.favorite_movie_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
//        movies.addAll(Arrays.asList(db.movieDAO().selectAllMoviesFavs()));
//        adapter = new FavAdapter(getContext());
//        adapter.setMovieFavorites(movies);
//        recyclerView.setAdapter(adapter);
        adapter = new FavAdapter(getContext());
        setListMovies(adapter);
    }
    private void setListMovies(FavAdapter adapter){
        Cursor cursor = getContext().getContentResolver().query(MovieFavProvider.URI_MOVIEFAV,null, null, null, null);
        ArrayList<MovieFavorite> movieFavorites = MappingHelper.mapCursorToArrayList(cursor);
        adapter.setMovieFavorites(movieFavorites);
        recyclerView.setAdapter(adapter);
    }
}
