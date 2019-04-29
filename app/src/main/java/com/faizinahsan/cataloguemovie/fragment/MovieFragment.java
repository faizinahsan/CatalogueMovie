package com.faizinahsan.cataloguemovie.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.faizinahsan.cataloguemovie.MainActivity;
import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.SearchActivity;
import com.faizinahsan.cataloguemovie.SettingActivity;
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
    SearchView searchView = null;
    SearchView.OnQueryTextListener queryTextListener;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
//                    Toast.makeText(getContext(), newText, Toast.LENGTH_SHORT).show();

                    return false;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    intent.putExtra("code",1);
                    intent.putExtra("movie_search",query);
                    startActivity(intent);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                break;
            case R.id.settings_activity:
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
}
