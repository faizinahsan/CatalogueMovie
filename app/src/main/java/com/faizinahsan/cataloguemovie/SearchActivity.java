package com.faizinahsan.cataloguemovie;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.faizinahsan.cataloguemovie.adapter.MoviesRVAdapter;
import com.faizinahsan.cataloguemovie.adapter.TvRVAdapter;
import com.faizinahsan.cataloguemovie.api.APIService;
import com.faizinahsan.cataloguemovie.model.MovieResponse;
import com.faizinahsan.cataloguemovie.model.Movies;
import com.faizinahsan.cataloguemovie.model.TVResponse;
import com.faizinahsan.cataloguemovie.model.TVShows;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MoviesRVAdapter adapter;
    TvRVAdapter tvRVAdapter;
    String text;
    ProgressBar progressBar;
    int code;
private static final String BASE_URL= "https://api.themoviedb.org/3/";
    private Retrofit retrofit;
    private void initializeRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Search");
        progressBar = findViewById(R.id.progress_search);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.search_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        code = getIntent().getIntExtra("code",0);
        if (code == 1){
             text = getIntent().getStringExtra("movie_search");
             initMovieView(text,savedInstanceState);
        }else{
             text = getIntent().getStringExtra("tv_search");
             initTvView(text,savedInstanceState);
        }
//        TextView textView = findViewById(R.id.search_view_text);
//        textView.setText(text);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void initMovieView(String query,@Nullable Bundle savedInstanceState){
        initializeRetrofit();
        APIService service = retrofit.create(APIService.class);
        Call<MovieResponse> call = service.searchMovie(query,"0421fe7c9fd6d2048fb64bfca56e3819");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                generateDataList(response.body().getResults());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        if (savedInstanceState != null){
            ArrayList<Movies> list;
            list = savedInstanceState.getParcelableArrayList("movie");
            generateDataList(list);
            progressBar.setVisibility(View.GONE);
        }
    }
    private  void initTvView(String query,@Nullable Bundle savedInstanceState){
        initializeRetrofit();
        APIService service = retrofit.create(APIService.class);
        Call<TVResponse> call = service.searchTv(query,"0421fe7c9fd6d2048fb64bfca56e3819");
        call.enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                generateTvDataList(response.body().getResults());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {

            }
        });
        //jgn diganggu gugat wkwk
        if (savedInstanceState != null){
            ArrayList<TVShows> list;
            list = savedInstanceState.getParcelableArrayList("tv");
            generateTvDataList(list);
            progressBar.setVisibility(View.GONE);
        }
    }
    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(ArrayList<Movies> moviesList) {
        adapter = new MoviesRVAdapter(this);
        adapter.setMovies(moviesList);
        recyclerView.setAdapter(adapter);
    }
    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateTvDataList(ArrayList<TVShows> tvShows) {
        tvRVAdapter = new TvRVAdapter(this);
        tvRVAdapter.setTvShows(tvShows);
        recyclerView.setAdapter(tvRVAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (code ==1){
            outState.putParcelableArrayList("movie",new ArrayList<Parcelable>(adapter.getMovies()));
        }else{
            outState.putParcelableArrayList("tv",new ArrayList<Parcelable>(tvRVAdapter.getTvShows()));
        }
    }
}
