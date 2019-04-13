package com.faizinahsan.cataloguemovie.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.fragment.MovieFavoriteFragment;
import com.faizinahsan.cataloguemovie.fragment.TVFavoriteFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return new MovieFavoriteFragment();
        }else {
            return new TVFavoriteFragment();
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.favorite_movie);
            case 1:
                return context.getString(R.string.favorite_tv);
            default:
                return null;
        }
    }
}
