package com.faizinahsan.cataloguemovie.fragment;


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
import android.widget.Toast;

import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.adapter.MovieFavAdapter;
import com.faizinahsan.cataloguemovie.adapter.MoviesRVAdapter;
import com.faizinahsan.cataloguemovie.api.APIService;
import com.faizinahsan.cataloguemovie.helper.MappingHelper;
import com.faizinahsan.cataloguemovie.model.MovieFav;
import com.faizinahsan.cataloguemovie.model.MovieResponse;
import com.faizinahsan.cataloguemovie.model.Movies;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    MovieFavAdapter adapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    private static final String BASE_URL= "https://api.themoviedb.org/3/";
//    private Retrofit retrofit;
//    private void initializeRetrofit(){
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
    public MoviesFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favorite_movie_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        ArrayList<MovieFav> movieFavs = MappingHelper.mapCursorToArrayList(cursor);
        adapter = new MovieFavAdapter(getContext());
        adapter.setMovieFavs(movieFavs);
        recyclerView.setAdapter(adapter);
    }

    //    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        recyclerView = view.findViewById(R.id.favorite_movie_rv);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        initializeRetrofit();
//        APIService service = retrofit.create(APIService.class);
//        Call<MovieResponse> call = service.getAllMovies("0421fe7c9fd6d2048fb64bfca56e3819");
//        call.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                generateDataList(response.body().getResults());
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                Toast.makeText(getContext(), "GAGALLL", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    /*Method to generate List of data using RecyclerView with custom adapter*/
//    private void generateDataList(ArrayList<Movies> moviesList) {
//        adapter = new MoviesRVAdapter(getContext());
//        adapter.setMovies(moviesList);
//        recyclerView.setAdapter(adapter);
//    }
}
