package com.faizinahsan.cataloguemovie.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "tvfavorite")
public class TvFavorite implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "image")
    String image;
    @ColumnInfo(name = "id_tv")
    String id_title;
    @ColumnInfo(name = "overview")
    String overview;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId_title() {
        return id_title;
    }

    public void setId_title(String id_title) {
        this.id_title = id_title;
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
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.id_title);
        dest.writeString(this.overview);
    }

    public TvFavorite() {
    }

    protected TvFavorite(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.image = in.readString();
        this.id_title = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<TvFavorite> CREATOR = new Parcelable.Creator<TvFavorite>() {
        @Override
        public TvFavorite createFromParcel(Parcel source) {
            return new TvFavorite(source);
        }

        @Override
        public TvFavorite[] newArray(int size) {
            return new TvFavorite[size];
        }
    };
}
