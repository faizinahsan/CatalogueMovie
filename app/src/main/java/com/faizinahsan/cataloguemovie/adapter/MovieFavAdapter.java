package com.faizinahsan.cataloguemovie.adapter;

import android.content.Context;
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
import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.model.MovieFav;

import java.util.ArrayList;

public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavAdapter.ViewHolder> {
    Context context;
    ArrayList<MovieFav> movieFavs = new ArrayList<>();
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public MovieFavAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<MovieFav> getMovieFavs() {
        return movieFavs;
    }

    public void setMovieFavs(ArrayList<MovieFav> movieFavs) {
        this.movieFavs = movieFavs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.movieTitle.setText(movieFavs.get(i).getTitle());
        Glide.with(context).load(IMG_BASE_URL + movieFavs.get(i).getImage()).apply(new RequestOptions().override(350,350)).into(viewHolder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movieFavs.size();
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