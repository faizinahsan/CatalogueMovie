package com.faizinahsan.cataloguemovie.fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.adapter.MoviesRVAdapter;
import com.faizinahsan.cataloguemovie.api.APIService;
import com.faizinahsan.cataloguemovie.api.RetrofitClientInstance;
import com.faizinahsan.cataloguemovie.model.MovieResponse;
import com.faizinahsan.cataloguemovie.model.Movies;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    RecyclerView recyclerView;
    MoviesRVAdapter adapter;
    ProgressBar progressBar;

    private static final String BASE_URL= "https://api.themoviedb.org/3/";
    private Retrofit retrofit;
    private void initializeRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Create handle for the RetrofitInstance interface*/
        progressBar = view.findViewById(R.id.progress_movie);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.movies_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        initializeRetrofit();
        APIService service = retrofit.create(APIService.class);
        Call<MovieResponse> call = service.getAllMovies("0421fe7c9fd6d2048fb64bfca56e3819");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                generateDataList(response.body().getResults());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), "GAGALLL", Toast.LENGTH_SHORT).show();
            }
        });
        if (savedInstanceState != null){
            ArrayList<Movies> list;
            list = savedInstanceState.getParcelableArrayList("movie");
            generateDataList(list);
            progressBar.setVisibility(View.GONE);
        }
    }
    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(ArrayList<Movies> moviesList) {
        adapter = new MoviesRVAdapter(getContext());
        adapter.setMovies(moviesList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie",new ArrayList<Parcelable>(adapter.getMovies()));
    }
}
