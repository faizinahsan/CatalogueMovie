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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faizinahsan.cataloguemovie.DetailActivity;
import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.model.TvFav;

import java.util.ArrayList;

public class TvFavAdapter extends RecyclerView.Adapter<TvFavAdapter.ViewHolder> {
    ArrayList<TvFav> tvFavs = new ArrayList<>();
    Context context;
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public TvFavAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<TvFav> getTvFavs() {
        return tvFavs;
    }

    public void setTvFavs(ArrayList<TvFav> tvFavs) {
        this.tvFavs = tvFavs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvTitle.setText(tvFavs.get(i).getTitle());
        Glide.with(context).load(IMG_BASE_URL + tvFavs.get(i).getImage()).apply(new RequestOptions().override(350,350)).into(viewHolder.tvPoster);
        viewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.CODE_TAG_DETAIL,4);
                intent.putExtra(DetailActivity.MOVIE_TAG_DETAIL,tvFavs.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvFavs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView tvPoster;
        Button btnMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title_container);
            tvPoster = itemView.findViewById(R.id.poster_container);
            btnMore = itemView.findViewById(R.id.more_info_btn);
        }
    }
}
