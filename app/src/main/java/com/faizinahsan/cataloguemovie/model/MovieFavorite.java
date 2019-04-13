package com.faizinahsan.cataloguemovie.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import com.faizinahsan.cataloguemovie.database.DatabaseContract.NoteColumns;
import com.faizinahsan.cataloguemovie.provider.MovieFavProvider;

@Entity(tableName = NoteColumns.TABLE_NAME)
public class MovieFavorite implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true,name = NoteColumns.ID)
    long id;
    @ColumnInfo(name =  NoteColumns.TITLE)
    String title;
    @ColumnInfo(name = NoteColumns.IMAGE)
    String image;
    @ColumnInfo(name = NoteColumns.ID_MOVIE)
    String id_movie;
    @ColumnInfo(name = NoteColumns.OVERVIEW)
    String overview;

    public MovieFavorite(long id, String title, String image, String id_movie, String overview) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.id_movie = id_movie;
        this.overview = overview;
    }
    //untuk mentransformasikan value dari insertData dari Detail Data yg bersifat ContentValues menjadi objek MovieFavorite sehingga dapat di masukan di table
    public static MovieFavorite fromContentValues(ContentValues values) {
        final MovieFavorite movieFavorite = new MovieFavorite();
        if (values.containsKey(NoteColumns.ID)) {
            movieFavorite.id = values.getAsLong(NoteColumns.ID);
        }
        if (values.containsKey(NoteColumns.TITLE)) {
            movieFavorite.title = values.getAsString(NoteColumns.TITLE);
        }
        if (values.containsKey(NoteColumns.IMAGE)){
            movieFavorite.image = values.getAsString(NoteColumns.IMAGE);
        }
        if (values.containsKey(NoteColumns.ID_MOVIE)){
            movieFavorite.id_movie = values.getAsString(NoteColumns.OVERVIEW);
        }
        if (values.containsKey(NoteColumns.OVERVIEW)){
            movieFavorite.overview = values.getAsString(NoteColumns.OVERVIEW);
        }
        return movieFavorite;
    }
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getId_movie() {
        return id_movie;
    }

    public String getOverview() {
        return overview;
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
}
