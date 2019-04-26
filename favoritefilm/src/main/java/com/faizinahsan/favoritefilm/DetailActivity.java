package com.faizinahsan.favoritefilm;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faizinahsan.favoritefilm.model.MovieFav;

import static com.faizinahsan.favoritefilm.helper.MovieContract.MovieColumns.CONTENT_URI;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w500";
    public static final String MOVIE_TAG_DETAIL = "movieItems";
    TextView titleContainer,ratingContainer,dateContainer;
    ImageView posterContainer;
    MovieFav movieFav;
    Button removeFromFav;
    int id_movie;

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
        removeFromFav = findViewById(R.id.removeFromFavorite);
        removeFromFav.setOnClickListener(this);
        removeFromFav.setVisibility(View.GONE);
        getFavMNovie();
    }
    private void getFavMNovie(){
        movieFav = getIntent().getParcelableExtra(MOVIE_TAG_DETAIL);
        id_movie = movieFav.getId_movie();
        titleContainer.setText(movieFav.getTitle());
        ratingContainer.setText(movieFav.getOverview());
        dateContainer.setText("");
        Glide.with(this).load(IMG_BASE_URL+movieFav.getImage()).apply(new RequestOptions().override(350,350)).into(posterContainer);
        removeFromFav.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.removeFromFavorite:
                deleteMovieFav(id_movie);
                finish();
                break;
        }
    }
    private void deleteMovieFav(int id){
        Uri uri = CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        getContentResolver().delete(uri, null, null);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
