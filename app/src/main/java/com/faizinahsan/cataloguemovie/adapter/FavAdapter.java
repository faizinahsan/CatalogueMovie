package com.faizinahsan.cataloguemovie.adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faizinahsan.cataloguemovie.DetailActivity;
import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.database.AppDatabase;
import com.faizinahsan.cataloguemovie.model.MovieFavorite;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private AppDatabase db;
    private Context context;
    private ArrayList<MovieFavorite> movieFavorites;
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public FavAdapter(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"moviefavdb").fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    public ArrayList<MovieFavorite> getMovieFavorites() {
        return movieFavorites;
    }

    public void setMovieFavorites(ArrayList<MovieFavorite> movieFavorites) {
        notifyDataSetChanged();
        this.movieFavorites = movieFavorites;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.movieTitle.setText(movieFavorites.get(i).getTitle());
        Glide.with(context).load(IMG_BASE_URL + movieFavorites.get(i).getImage()).apply(new RequestOptions().override(350,350)).into(viewHolder.moviePoster);

        viewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MovieFavorite mv =db.movieDAO().selectSingleMovieFav(movieFavorites.get(i).getId());
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra(DetailActivity.CODE_TAG_DETAIL,3);
//                intent.putExtra(DetailActivity.MOVIE_TAG_DETAIL,mv);
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieFavorites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        ImageView moviePoster;
        Button btnMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.title_container);
            moviePoster = itemView.findViewById(R.id.poster_container);
            btnMore = itemView.findViewById(R.id.more_info_btn);
        }
    }
}
