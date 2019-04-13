package com.faizinahsan.cataloguemovie;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.faizinahsan.cataloguemovie.fragment.FavoritesFragment;
import com.faizinahsan.cataloguemovie.fragment.MovieFragment;
import com.faizinahsan.cataloguemovie.fragment.TvShowsFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListened = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()){
                case R.id.navigation_home:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragment  = new TvShowsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_notifications:
                    fragment = new FavoritesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListened);
        if (savedInstanceState == null){
            navigationView.setSelectedItemId(R.id.navigation_home);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
