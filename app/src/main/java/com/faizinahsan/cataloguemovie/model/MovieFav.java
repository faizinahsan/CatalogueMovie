package com.faizinahsan.cataloguemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.ID;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.ID_MOVIE;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.IMAGE;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.OVERVIEW;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.MovieColumns.TITLE;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.getColumnInt;
import static com.faizinahsan.cataloguemovie.helper.MovieContract.getColumnString;

public class MovieFav implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String image;
    private int id_movie;


    public MovieFav(Cursor cursor){
        this.id = getColumnInt(cursor, ID);
        this.title = getColumnString(cursor, TITLE);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.image = getColumnString(cursor, IMAGE);
        this.id_movie = getColumnInt(cursor,ID_MOVIE);
    }

    public MovieFav(int id, String title, String overview, String image, int id_movie) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.image = image;
        this.id_movie = id_movie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_movie() {
        return id_movie;
    }

    public void setId_movie(int id_movie) {
        this.id_movie = id_movie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.image);
        dest.writeInt(this.id_movie);
    }

    protected MovieFav(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.image = in.readString();
        this.id_movie = in.readInt();
    }

    public static final Parcelable.Creator<MovieFav> CREATOR = new Parcelable.Creator<MovieFav>() {
        @Override
        public MovieFav createFromParcel(Parcel source) {
            return new MovieFav(source);
        }

        @Override
        public MovieFav[] newArray(int size) {
            return new MovieFav[size];
        }
    };
}
