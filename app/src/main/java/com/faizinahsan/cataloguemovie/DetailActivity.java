package com.faizinahsan.cataloguemovie;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faizinahsan.cataloguemovie.model.Movies;
import com.faizinahsan.cataloguemovie.model.TVShows;

import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.CONTENT_URI;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.ID;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.ID_MOVIE;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.IMAGE;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.OVERVIEW;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.TITLE;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w500";
    public static final String MOVIE_TAG_DETAIL = "movieItems";
    public static final String CODE_TAG_DETAIL = "code_for_item";
    TextView titleContainer,ratingContainer,dateContainer;
    ImageView posterContainer;
    private int code;
    Movies movies;
    TVShows tvShows;
    Button addToFav,removeFromFav;
    boolean isfav = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleContainer = findViewById(R.id.title_detail_container);
        ratingContainer = findViewById(R.id.rating_detail_container);
        dateContainer = findViewById(R.id.date_detail_container);
        posterContainer = findViewById(R.id.poster_detail_container);
        addToFav = findViewById(R.id.addToFav);
        addToFav.setOnClickListener(this);
        removeFromFav = findViewById(R.id.removeFromFavorite);
        removeFromFav.setOnClickListener(this);
        removeFromFav.setVisibility(View.GONE);
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
        ifMovieFav(movies.getId());
    }
    private void getTVShows(){
        tvShows = getIntent().getParcelableExtra(MOVIE_TAG_DETAIL);
        titleContainer.setText(tvShows.getName());
        ratingContainer.setText(String.valueOf(tvShows.getVoteAverage()));
        dateContainer.setText(tvShows.getFirstAirDate());
        Glide.with(this).load(IMG_BASE_URL+tvShows.getPosterPath()).apply(new RequestOptions().override(350,350)).into(posterContainer);
    }
    private void insertMovieFav(Movies movies){
        ContentValues values = new ContentValues();
        values.put(ID_MOVIE,movies.getId());
        values.put(TITLE,movies.getTitle());
        values.put(OVERVIEW,movies.getOverview());
        values.put(IMAGE,movies.getPosterPath());
        Uri uri = getContentResolver().insert(CONTENT_URI, values);
        if (uri != null) {
            Log.d("TAG", "Uri " + uri);
        }
    }
    private void deleteMovieFav(int id){
        Uri uri = CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        getContentResolver().delete(uri, null, null);
    }
    private void ifMovieFav(int id){
        Uri uri = CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor.getCount() > 0){
            isfav = true;
            addToFav.setVisibility(View.GONE);
            removeFromFav.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addToFav:
                insertMovieFav(movies);
                finish();
                break;
            case R.id.removeFromFavorite:
                deleteMovieFav(movies.getId());
                finish();
                break;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
