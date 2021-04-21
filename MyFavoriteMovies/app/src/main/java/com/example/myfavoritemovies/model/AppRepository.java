package com.example.myfavoritemovies.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {

    private GenreDao genreDao;
    private MovieDao movieDao;

    private MoviesDatabase database;

    public AppRepository(Application application){

        database = MoviesDatabase.getInstance(application);

        genreDao = database.getGenreDao();
        movieDao = database.getMovieDao();
    }



    public void insertGenre(Genre genre){

        new InsertGenreAsyncTask(genreDao).execute(genre);
    }

    private static class InsertGenreAsyncTask extends AsyncTask<Genre, Void, Void> {

        private GenreDao genreDao;

        public InsertGenreAsyncTask(GenreDao genreDao){

            this.genreDao = genreDao;
        }

        @Override
        protected Void doInBackground(Genre... genres) {

            genreDao.insert(genres[0]);

            return null;
        }
    }

    public void insertMovie(Movie movie){

        new InsertMovieAsyncTask(movieDao).execute(movie);
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void>{

        private MovieDao movieDao;

        public InsertMovieAsyncTask(MovieDao movieDao){

            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {

            movieDao.insert(movies[0]);

            return null;
        }
    }




    public void updateGenre(Genre genre){

        new UpdateGenreAsyncTask(genreDao).execute(genre);
    }

    private static class UpdateGenreAsyncTask extends AsyncTask<Genre, Void, Void> {

        private GenreDao genreDao;

        public UpdateGenreAsyncTask(GenreDao genreDao){

            this.genreDao = genreDao;
        }

        @Override
        protected Void doInBackground(Genre... genres) {

            genreDao.update(genres[0]);

            return null;
        }
    }

    public void updateMovie(Movie movie){

        new UpdateMovieAsyncTask(movieDao).execute(movie);
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<Movie, Void, Void>{

        private MovieDao movieDao;

        public UpdateMovieAsyncTask(MovieDao movieDao){

            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {

            movieDao.update(movies[0]);

            return null;
        }
    }



    public void deleteGenre(Genre genre){

        new DeleteGenreAsyncTask(genreDao).execute(genre);
    }

    private static class DeleteGenreAsyncTask extends AsyncTask<Genre, Void, Void> {

        private GenreDao genreDao;

        public DeleteGenreAsyncTask(GenreDao genreDao){

            this.genreDao = genreDao;
        }

        @Override
        protected Void doInBackground(Genre... genres) {

            genreDao.delete(genres[0]);

            return null;
        }
    }

    public void deleteMovie(Movie movie){

        new DeleteMovieAsyncTask(movieDao).execute(movie);
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void>{

        private MovieDao movieDao;

        public DeleteMovieAsyncTask(MovieDao movieDao){

            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {

            movieDao.delete(movies[0]);

            return null;
        }
    }


    public LiveData<List<Genre>> getAllGenres(){

        return genreDao.getAllGenres();
    }

    public LiveData<List<Movie>> getAllMoviesByGenreId(int genreId){

        return movieDao.getAllMoviesByGenreId(genreId);
    }
}
