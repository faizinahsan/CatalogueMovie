package com.faizinahsan.cataloguemovie;

import android.arch.persistence.room.Room;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faizinahsan.cataloguemovie.database.AppDatabase;
import com.faizinahsan.cataloguemovie.model.MovieFavorite;
import com.faizinahsan.cataloguemovie.model.Movies;
import com.faizinahsan.cataloguemovie.model.TVShows;
import com.faizinahsan.cataloguemovie.model.TvFavorite;
import com.faizinahsan.cataloguemovie.database.DatabaseContract.NoteColumns;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w500";
    public static final String MOVIE_TAG_DETAIL = "movieItems";
    public static final String CODE_TAG_DETAIL = "code_for_item";
    TextView titleContainer,ratingContainer,dateContainer;
    ImageView posterContainer;
    Button addToFavoriteBtn,removeFromFavBtn;

    private int position;

    private AppDatabase db;
    int code;
    int mvId,tvId;
    Movies movies;
    TVShows tvShows;
    MovieFavorite mv;
    TvFavorite tvFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"moviefavdb").fallbackToDestructiveMigration().build();
        titleContainer = findViewById(R.id.title_detail_container);
        ratingContainer = findViewById(R.id.rating_detail_container);
        dateContainer = findViewById(R.id.date_detail_container);
        posterContainer = findViewById(R.id.poster_detail_container);
        addToFavoriteBtn = findViewById(R.id.add_to_favorite_btn);
        removeFromFavBtn = findViewById(R.id.remove_from_favorite_btn);
        addToFavoriteBtn.setOnClickListener(this);
        removeFromFavBtn.setOnClickListener(this);
        removeFromFavBtn.setVisibility(View.GONE);
        code = getIntent().getIntExtra(CODE_TAG_DETAIL,0);
        if (code == 1){

            getMovie();
        }else if (code == 2){

            getTVShows();
        }else if (code == 3){
            getMoviewFav();
        }else{
            getTvFav();
        }

    }
    private void insertContentProvider(){
        ContentValues values = new ContentValues();
        values.put(NoteColumns.TITLE,movies.getOriginalTitle());
        values.put(NoteColumns.IMAGE,movies.getPosterPath());
        values.put(NoteColumns.OVERVIEW,movies.getOverview());
        values.put(NoteColumns.ID_MOVIE,movies.getId());
        getContentResolver().insert(NoteColumns.CONTENT_URI,values);
    }
    private void getMovie(){
        movies = getIntent().getParcelableExtra(MOVIE_TAG_DETAIL);
        mvId = movies.getId();
        String[] mProjection= {NoteColumns.ID_MOVIE};
        String mSelection = NoteColumns.ID_MOVIE + " = ";
        String[] mSelectionArgs ={String.valueOf(mvId)} ;
        titleContainer.setText(movies.getTitle());
        ratingContainer.setText(String.valueOf(movies.getVoteAverage()));
        dateContainer.setText(movies.getReleaseDate());
//        selectFromDb(mvId);
        Glide.with(this).load(IMG_BASE_URL+movies.getPosterPath()).apply(new RequestOptions().override(350,350)).into(posterContainer);
//        MovieFavorite mv =db.movieDAO().selectIdMovieFav(String.valueOf(movies.getId()));


    }
    private void getTVShows(){
        tvShows = getIntent().getParcelableExtra(MOVIE_TAG_DETAIL);
        tvId = tvShows.getId();
        titleContainer.setText(tvShows.getName());
        ratingContainer.setText(String.valueOf(tvShows.getVoteAverage()));
        dateContainer.setText(tvShows.getFirstAirDate());
        Glide.with(this).load(IMG_BASE_URL+tvShows.getPosterPath()).apply(new RequestOptions().override(350,350)).into(posterContainer);
        selectTvFavFromDB(tvId);
    }
    private void getMoviewFav(){
        mv = getIntent().getParcelableExtra(MOVIE_TAG_DETAIL);
        mvId = Integer.valueOf(mv.getId_movie());
        titleContainer.setText(mv.getTitle());
        ratingContainer.setText(mv.getOverview());
        dateContainer.setText(mv.getId_movie());
        addToFavoriteBtn.setVisibility(View.GONE);
        Glide.with(this).load(IMG_BASE_URL+mv.getImage()).apply(new RequestOptions().override(350,350)).into(posterContainer);
        removeFromFavBtn.setVisibility(View.VISIBLE);
    }
    private void getTvFav(){
        tvFavorite = getIntent().getParcelableExtra(MOVIE_TAG_DETAIL);
        tvId = Integer.valueOf(tvFavorite.getId_title());
        titleContainer.setText(tvFavorite.getTitle());
        ratingContainer.setText(tvFavorite.getOverview());
        dateContainer.setText(tvFavorite.getId_title());
        Glide.with(this).load(IMG_BASE_URL+tvFavorite.getImage()).apply(new RequestOptions().override(350,350)).into(posterContainer);
        addToFavoriteBtn.setVisibility(View.GONE);
        removeFromFavBtn.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_to_favorite_btn){
            if (code == 1){
//                MovieFavorite mv = new MovieFavorite();
//                mv.setTitle(movies.getTitle());
//                mv.setImage(movies.getPosterPath());
//                mv.setId_movie(String.valueOf(movies.getId()));
//                mv.setOverview(movies.getOverview());
//                insertDB(mv);
                insertContentProvider();
                finish();
            }else if (code == 2){
                TvFavorite tv = new TvFavorite();
                tv.setTitle(tvShows.getName());
                tv.setOverview(tvShows.getOverview());
                tv.setId_title(String.valueOf(tvShows.getId()));
                tv.setImage(tvShows.getPosterPath());
                insertTVDB(tv);
                finish();
            }
        } else if (v.getId() == R.id.remove_from_favorite_btn) {
            if (code==1){
//                deleteFromDb(mvId);
                finish();
            }else if (code == 2){
                deleteTvFavFromDb(tvId);
                finish();
            }else if (code == 3){
//                deleteFromDb(mvId);
                finish();
            }else{
                deleteTvFavFromDb(tvId);
                finish();
            }
        }
    }
//    private void insertDB(final MovieFavorite mv){
//        new AsyncTask<Void,Void,Long>(){
//
//            @Override
//            protected Long doInBackground(Void... voids) {
//                long status = db.movieDAO().insertMovieFavorite(mv);
//                return status;
//            }
//
//            @Override
//            protected void onPostExecute(Long status) {
//                Toast.makeText(DetailActivity.this, "add to favorite status row"+status, Toast.LENGTH_SHORT).show();
//            }
//        }.execute();
//    }
//    private void selectFromDb(final int mv){
//        new AsyncTask<Void,Void,MovieFavorite>(){
//
//            @Override
//            protected MovieFavorite doInBackground(Void... voids) {
//                MovieFavorite status = db.movieDAO().selectIdSingleMovieFav(String.valueOf(mv));
//                return status;
//            }
//
//            @Override
//            protected void onPostExecute(MovieFavorite movieFavorite) {
//                if (movieFavorite != null){
//                    addToFavoriteBtn.setVisibility(View.GONE);
//                    removeFromFavBtn.setVisibility(View.VISIBLE);
//                }
//            }
//        }.execute();
//    }
    private void selectTvFavFromDB(final int tv){
        new AsyncTask<Void, Void, TvFavorite>() {
            @Override
            protected TvFavorite doInBackground(Void... voids) {
                TvFavorite favorite = db.tvDAO().selectSingleIdTvFav(String.valueOf(tv));
                return favorite;
            }

            @Override
            protected void onPostExecute(TvFavorite tvFavorite) {
                if (tvFavorite != null){
                    addToFavoriteBtn.setVisibility(View.GONE);
                    removeFromFavBtn.setVisibility(View.VISIBLE);
                }
            }
        }.execute();
    }
    private void deleteTvFavFromDb(final int tv){
        new AsyncTask<Void, Void, TvFavorite>() {
            @Override
            protected TvFavorite doInBackground(Void... voids) {
                TvFavorite favorite = db.tvDAO().selectSingleIdTvFav(String.valueOf(tv));
                db.tvDAO().deleteTv(favorite);
                return favorite;
            }
        }.execute();
    }
//    private void deleteFromDb(final int mv){
//        new AsyncTask<Void,Void,MovieFavorite>(){
//
//            @Override
//            protected MovieFavorite doInBackground(Void... voids) {
//                MovieFavorite status = db.movieDAO().selectIdSingleMovieFav(String.valueOf(mv));
//                db.movieDAO().deleteBarang(status);
//
//                return status;
//            }
//        }.execute();
//    }
    private void insertTVDB(final TvFavorite tvFavorite){
        new AsyncTask<Void,Void,Long>(){


            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.tvDAO().insertTvFavorite(tvFavorite);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(DetailActivity.this, "TV Shows Have been added row"+status, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
    private void deleteContentProvider(){
        int cursor = getContentResolver().delete(NoteColumns.CONTENT_URI,null,null);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
