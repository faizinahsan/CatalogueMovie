package com.faizinahsan.cataloguemovie.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

@Entity(tableName = "tmovie")
public class MovieFavorite implements Parcelable {
    //Name of the ID
    public static final String COLUMN_ID= BaseColumns._ID;
    public static final String COLUMN_NAME = "title";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_ID_MOVIE = "id_movie";
    public static final String COLUMN_OVERVIEW = "overview";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true,name = COLUMN_ID)
    long id;

    @ColumnInfo(name = COLUMN_NAME)
    String title;
    @ColumnInfo(name = COLUMN_IMAGE)
    String image;
    @ColumnInfo(name = COLUMN_ID_MOVIE)
    String id_movie;
    @ColumnInfo(name = COLUMN_OVERVIEW)
    String overview;

    public static MovieFavorite fromContentValues(ContentValues values) {
        final MovieFavorite movieFavorite = new MovieFavorite();
        if (values.containsKey(COLUMN_ID)) {
//            movieFavorite.id = values.getAsLong(COLUMN_ID);
            movieFavorite.setId(values.getAsLong(COLUMN_ID));
        }
        if (values.containsKey(COLUMN_NAME)) {
//            movieFavorite.title = values.getAsString(COLUMN_NAME);
            movieFavorite.setTitle(values.getAsString(COLUMN_NAME));
        }
        return movieFavorite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.id_movie);
        dest.writeString(this.overview);
    }

    public MovieFavorite() {
    }

    protected MovieFavorite(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.image = in.readString();
        this.id_movie = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<MovieFavorite> CREATOR = new Parcelable.Creator<MovieFavorite>() {
        @Override
        public MovieFavorite createFromParcel(Parcel source) {
            return new MovieFavorite(source);
        }

        @Override
        public MovieFavorite[] newArray(int size) {
            return new MovieFavorite[size];
        }
    };
    public MovieFavorite(Cursor cursor){
        this.id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
        this.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
        this.image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
        this.overview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW));
        this.overview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID_MOVIE));
    }

    public MovieFavorite(long id, String title, String image, String id_movie, String overview) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.id_movie = id_movie;
        this.overview = overview;
    }
}
