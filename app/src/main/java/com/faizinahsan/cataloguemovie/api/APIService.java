package com.faizinahsan.cataloguemovie.api;

import com.faizinahsan.cataloguemovie.model.MovieResponse;
import com.faizinahsan.cataloguemovie.model.Movies;
import com.faizinahsan.cataloguemovie.model.TVResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("movie/popular")
    Call<MovieResponse> getAllMovies(@Query("api_key") String apiKey);
    @GET("tv/popular")
    Call<TVResponse> getAllTV(@Query("api_key") String apiKey);
}
