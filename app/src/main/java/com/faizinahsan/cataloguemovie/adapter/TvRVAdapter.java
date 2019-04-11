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
import com.faizinahsan.cataloguemovie.model.Movies;
import com.faizinahsan.cataloguemovie.model.TVShows;

import java.util.ArrayList;

public class TvRVAdapter extends RecyclerView.Adapter<TvRVAdapter.TvViewHolder> {
    Context context;
    ArrayList<TVShows> tvShows = new ArrayList<>();
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w500";


    public TvRVAdapter(Context context) {
        this.context = context;
    }

    public void setTvShows(ArrayList<TVShows> tvShows) {
        this.tvShows = tvShows;
    }

    public ArrayList<TVShows> getTvShows() {
        return tvShows;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv,viewGroup,false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder tvViewHolder, final int i) {
        tvViewHolder.titleTv.setText(tvShows.get(i).getName());
        tvViewHolder.ratingTv.setText(String.valueOf(tvShows.get(i).getVoteAverage()));
        tvViewHolder.dateTv.setText(tvShows.get(i).getFirstAirDate());
        Glide.with(context).load(IMG_BASE_URL + tvShows.get(i).getPosterPath()).apply(new RequestOptions().override(350,350)).into(tvViewHolder.posterTv);
        tvViewHolder.btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.CODE_TAG_DETAIL,2);
                intent.putExtra(DetailActivity.MOVIE_TAG_DETAIL,tvShows.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv,ratingTv,dateTv;
        ImageView posterTv;
        Button btnMoreInfo;
        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title_container);
            ratingTv = itemView.findViewById(R.id.rating_container);
            dateTv = itemView.findViewById(R.id.date_container);
            posterTv = itemView.findViewById(R.id.poster_container);
            btnMoreInfo = itemView.findViewById(R.id.more_info_btn);
        }
    }
}
