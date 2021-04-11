package com.example.myfavoritemovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myfavoritemovies.model.AppRepository;
import com.example.myfavoritemovies.model.Genre;
import com.example.myfavoritemovies.model.GenreDao;
import com.example.myfavoritemovies.model.Movie;
import com.example.myfavoritemovies.model.MovieDao;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    private LiveData<List<Genre>> genres;
    private LiveData<List<Movie>> moviesByGenreId;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AppRepository(application);
    }

    public LiveData<List<Genre>> getGenres() {

        genres = appRepository.getAllGenres();

        return genres;
    }

    public LiveData<List<Movie>> getMoviesByGenreId(int genreId) {

        moviesByGenreId = appRepository.getAllMoviesByGenreId(genreId);

        return moviesByGenreId;
    }

    public void addNewMovie(Movie movie){

        appRepository.insertMovie(movie);
    }

    public void deleteMovie(Movie movie){

        appRepository.deleteMovie(movie);
    }

    public void update(Movie movie){

        appRepository.updateMovie(movie);
    }
}
