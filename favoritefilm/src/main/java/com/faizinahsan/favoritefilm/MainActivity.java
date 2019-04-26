package com.faizinahsan.favoritefilm;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.faizinahsan.favoritefilm.helper.MappingHelper;
import com.faizinahsan.favoritefilm.model.MovieFav;

import java.util.ArrayList;

import static com.faizinahsan.favoritefilm.helper.MovieContract.MovieColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieFavAdapter adapter;
    ArrayList<MovieFav> movieFavs;
    Cursor cursor;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    private static final String BASE_URL= "https://api.themoviedb.org/3/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_moviefav);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getData();
    }
    private void getData(){
        cursor= getContentResolver().query(CONTENT_URI,null,null,null,null);
        if (movieFavs != null){
            movieFavs.clear();
        }
        movieFavs= MappingHelper.mapCursorToArrayList(cursor);
        adapter = new MovieFavAdapter(this);
        adapter.setMovieFavs(movieFavs);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getData();
    }
}
