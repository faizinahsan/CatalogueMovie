package com.faizinahsan.cataloguemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.faizinahsan.cataloguemovie.helper.TvContract.TvColumn.ID;
import static com.faizinahsan.cataloguemovie.helper.TvContract.TvColumn.ID_MOVIE;
import static com.faizinahsan.cataloguemovie.helper.TvContract.TvColumn.IMAGE;
import static com.faizinahsan.cataloguemovie.helper.TvContract.TvColumn.OVERVIEW;
import static com.faizinahsan.cataloguemovie.helper.TvContract.TvColumn.TITLE;
import static com.faizinahsan.cataloguemovie.helper.TvContract.getColumnInt;
import static com.faizinahsan.cataloguemovie.helper.TvContract.getColumnString;

public class TvFav implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String image;
    private int id_tv;

    public TvFav(int id, String title, String overview, String image, int id_tv) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.image = image;
        this.id_tv = id_tv;
    }

    public TvFav(Cursor cursor) {
        this.id = getColumnInt(cursor, ID);
        this.title = getColumnString(cursor, TITLE);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.image = getColumnString(cursor, IMAGE);
        this.id_tv = getColumnInt(cursor,ID_MOVIE);
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

    public int getId_tv() {
        return id_tv;
    }

    public void setId_tv(int id_tv) {
        this.id_tv = id_tv;
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
        dest.writeInt(this.id_tv);
    }

    protected TvFav(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.image = in.readString();
        this.id_tv = in.readInt();
    }

    public static final Parcelable.Creator<TvFav> CREATOR = new Parcelable.Creator<TvFav>() {
        @Override
        public TvFav createFromParcel(Parcel source) {
            return new TvFav(source);
        }

        @Override
        public TvFav[] newArray(int size) {
            return new TvFav[size];
        }
    };
}
