package com.faizinahsan.cataloguemovie.fragment;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.adapter.TvFavAdapter;
import com.faizinahsan.cataloguemovie.adapter.TvRVAdapter;
import com.faizinahsan.cataloguemovie.api.APIService;
import com.faizinahsan.cataloguemovie.database.AppDatabase;
import com.faizinahsan.cataloguemovie.model.TVResponse;
import com.faizinahsan.cataloguemovie.model.TVShows;
import com.faizinahsan.cataloguemovie.model.TvFavorite;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFavoriteFragment extends Fragment {

    AppDatabase db;
    RecyclerView recyclerView;
    TvFavAdapter adapter;
    ArrayList<TvFavorite> tvFavorites = new ArrayList<>();

    public TVFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvfavorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = Room.databaseBuilder(getContext(),AppDatabase.class,"moviefavdb").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        recyclerView = view.findViewById(R.id.favorite_tv_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        tvFavorites.addAll(Arrays.asList(db.tvDAO().selectAllTvFromFav()));
        adapter = new TvFavAdapter(getContext());
        adapter.setTvFavorites(tvFavorites);
        recyclerView.setAdapter(adapter);
    }

}
