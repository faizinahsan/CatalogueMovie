package com.faizinahsan.cataloguemovie;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.faizinahsan.cataloguemovie.model.MovieFav;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w500";

    private final Context mContext;
    int mAppWidgetId;
    private Cursor cursor;
    private MovieFav movieFav;

    public StackRemoteViewsFactory(Context mContext,Intent intent) {
        this.mContext = mContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onDataSetChanged() {
        final long token = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(token);
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.darth_vader));
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_wars_logo));
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.storm_trooper));
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.starwars));
//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.falcon));
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
//        return mWidgetItems.size();
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        if (cursor.moveToPosition(position)) {

            movieFav = new MovieFav(cursor);
            Bitmap bmp;
            try {
                bmp = Glide.with(mContext)
                        .asBitmap()
                        .load(IMG_BASE_URL + movieFav.getImage())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                rv.setImageViewBitmap(R.id.imageView, bmp);
//                rv.setTextViewText(R.id.tv_movie_title, movieResult.getTitle());
            } catch (InterruptedException | ExecutionException e) {
                Log.d("Widget Load Error", "error");
            }
        }
//        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));
        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
