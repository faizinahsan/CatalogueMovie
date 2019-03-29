package com.faizinahsan.cataloguemovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faizinahsan.cataloguemovie.model.Movies;
import com.faizinahsan.cataloguemovie.model.TVShows;

public class DetailActivity extends AppCompatActivity {
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w500";
    public static final String MOVIE_TAG_DETAIL = "movieItems";
    public static final String CODE_TAG_DETAIL = "code_for_item";
    TextView titleContainer,ratingContainer,dateContainer;
    ImageView posterContainer;
    private int code;
    Movies movies;
    TVShows tvShows;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        titleContainer = findViewById(R.id.title_detail_container);
        ratingContainer = findViewById(R.id.rating_detail_container);
        dateContainer = findViewById(R.id.date_detail_container);
        posterContainer = findViewById(R.id.poster_detail_container);
        code = getIntent().getIntExtra(CODE_TAG_DETAIL,0);
        if (code == 1){
            getMovie();
        }else{
            getTVShows();
        }
    }
    private void getMovie(){
        movies = getIntent().getParcelableExtra(MOVIE_TAG_DETAIL);
        titleContainer.setText(movies.getTitle());
        ratingContainer.setText(String.valueOf(movies.getVoteAverage()));
        dateContainer.setText(movies.getReleaseDate());
        Glide.with(this).load(IMG_BASE_URL+movies.getPosterPath()).apply(new RequestOptions().override(350,350)).into(posterContainer);
    }
    private void getTVShows(){
        tvShows = getIntent().getParcelableExtra(MOVIE_TAG_DETAIL);
        titleContainer.setText(tvShows.getName());
        ratingContainer.setText(String.valueOf(tvShows.getVoteAverage()));
        dateContainer.setText(tvShows.getFirstAirDate());
        Glide.with(this).load(IMG_BASE_URL+tvShows.getPosterPath()).apply(new RequestOptions().override(350,350)).into(posterContainer);
    }
}
