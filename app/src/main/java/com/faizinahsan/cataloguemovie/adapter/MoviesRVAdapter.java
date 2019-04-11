package com.faizinahsan.cataloguemovie.adapter;

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

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faizinahsan.cataloguemovie.DetailActivity;
import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.database.AppDatabase;
import com.faizinahsan.cataloguemovie.model.Movies;

import java.util.ArrayList;

public class MoviesRVAdapter extends RecyclerView.Adapter<MoviesRVAdapter.MoviesVH> {
    ArrayList<Movies> movies = new ArrayList<>();
    Context context;

    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public MoviesRVAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(ArrayList<Movies> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MoviesVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv,viewGroup,false);
        return new MoviesVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesVH moviesVH, final int i) {
        moviesVH.movieTitle.setText(movies.get(i).getTitle());
        moviesVH.movieRating.setText(String.valueOf(movies.get(i).getVoteAverage()));
        moviesVH.movieDate.setText(movies.get(i).getReleaseDate());
        Glide.with(context).load(IMG_BASE_URL+movies.get(i).getPosterPath()).apply(new RequestOptions().override(350,350)).into(moviesVH.moviePoster);
        moviesVH.btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.CODE_TAG_DETAIL,1);
                intent.putExtra(DetailActivity.MOVIE_TAG_DETAIL,movies.get(i));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MoviesVH extends RecyclerView.ViewHolder {
        TextView movieTitle,movieRating,movieDate;
        ImageView moviePoster;
        Button btnMoreInfo;
        public MoviesVH(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.title_container);
            movieRating = itemView.findViewById(R.id.rating_container);
            movieDate = itemView.findViewById(R.id.date_container);
            moviePoster = itemView.findViewById(R.id.poster_container);
            btnMoreInfo = itemView.findViewById(R.id.more_info_btn);
        }
    }

    public ArrayList<Movies> getMovies() {
        return movies;
    }
}
