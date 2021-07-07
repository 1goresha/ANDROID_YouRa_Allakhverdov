package com.example.myfavoritemovies.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import androidx.databinding.library.baseAdapters.BR;

@Entity(tableName = "movies_table", foreignKeys = @ForeignKey(entity = Genre.class, parentColumns = "id", childColumns = "genre_id", onDelete = ForeignKey.CASCADE))
public class Movie extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    private int movieId;

    @ColumnInfo(name = "movie_name")
    private String name;

    @ColumnInfo(name = "movie_description")
    private String description;

    @ColumnInfo(name = "genre_id")
    private int genreId;

    @Ignore
    public Movie(){
    }

    public Movie(int movieId, String name, String description, int genreId) {
        this.movieId = movieId;
        this.name = name;
        this.description = description;
        this.genreId = genreId;
    }

    @Bindable
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
        notifyPropertyChanged(BR.movieId);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }


    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
        notifyPropertyChanged(BR.genreId);
    }
}
