package com.faizinahsan.cataloguemovie.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.adapter.TvFavAdapter;
import com.faizinahsan.cataloguemovie.adapter.TvRVAdapter;
import com.faizinahsan.cataloguemovie.api.APIService;
import com.faizinahsan.cataloguemovie.helper.MappingHelper;
import com.faizinahsan.cataloguemovie.model.TVResponse;
import com.faizinahsan.cataloguemovie.model.TVShows;
import com.faizinahsan.cataloguemovie.model.TvFav;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.faizinahsan.cataloguemovie.helper.TvContract.TvColumn.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFavoriteFragment extends Fragment {

    RecyclerView recyclerView;
    TvFavAdapter adapter;
    private static final String BASE_URL= "https://api.themoviedb.org/3/";
//    private Retrofit retrofit;
//    private void initializeRetrofit(){
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
    public TVFavoriteFragment() {
//         Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvfavorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favorite_tv_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        ArrayList<TvFav> movieFavs = MappingHelper.mapTvCursorToArrayList(cursor);
        adapter = new TvFavAdapter(getContext());
        adapter.setTvFavs(movieFavs);
        recyclerView.setAdapter(adapter);
    }
    //
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        recyclerView = view.findViewById(R.id.favorite_tv_rv);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        initializeRetrofit();
//        APIService service = retrofit.create(APIService.class);
//        Call<TVResponse> call = service.getAllTV("0421fe7c9fd6d2048fb64bfca56e3819");
//        call.enqueue(new Callback<TVResponse>() {
//            @Override
//            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
//                generateDataList(response.body().getResults());
//            }
//
//            @Override
//            public void onFailure(Call<TVResponse> call, Throwable t) {
//                Toast.makeText(getContext(), "GAGALLL", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    /*Method to generate List of data using RecyclerView with custom adapter*/
//    private void generateDataList(ArrayList<TVShows> tvShows) {
//        adapter = new TvRVAdapter(getContext());
//        adapter.setTvShows(tvShows);
//        recyclerView.setAdapter(adapter);
//    }

}
