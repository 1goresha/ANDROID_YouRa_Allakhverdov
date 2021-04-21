package com.example.myfavoritemovies.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Genre.class, Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase instance;

    abstract GenreDao getGenreDao();
    abstract MovieDao getMovieDao();

    public static synchronized MoviesDatabase getInstance(Context context){         //паттерн синглтон

        if (instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(), MoviesDatabase.class, "moviesDB")
                    .addCallback(callback)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new InitializeDataAsyncTask(instance).execute();
        }
    };

    private static class InitializeDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private GenreDao genreDao;
        private MovieDao movieDao;


        public InitializeDataAsyncTask(MoviesDatabase moviesDatabase){
            genreDao = moviesDatabase.getGenreDao();
            movieDao = moviesDatabase.getMovieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Genre comedyGenre = new Genre();
            comedyGenre.setGenreName("Comedy");

            Genre romanceGenre = new Genre();
            romanceGenre.setGenreName("Romance");

            Genre dramaGenre = new Genre();
            dramaGenre.setGenreName("Drama");

            genreDao.insert(comedyGenre);
            genreDao.insert(romanceGenre);
            genreDao.insert(dramaGenre);


            Movie movie1 = new Movie();
            movie1.setName("Bad Boys for Life");
            movie1.setDescription("The Bad Boys Mike Lowrey and Marcus Burnett are back together for one last ride in the highly anticipated Bad Boys for Life.");
            movie1.setGenreId(1);

            Movie movie2 = new Movie();
            movie2.setName("Parasite");
            movie2.setDescription("All unemployed, Ki-taek and his family take peculiar interest in the wealthy and glamorous Parks, as they ingratiate themselves into their lives and get entangled in an unexpected incident.");
            movie2.setGenreId(1);

            Movie movie3 = new Movie();
            movie3.setName(" Once Upon a Time... in Hollywood");
            movie3.setDescription("A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywood's Golden Age in 1969 Los Angeles.");
            movie3.setGenreId(1);

            Movie movie4 = new Movie();
            movie4.setName("You");
            movie4.setDescription("A dangerously charming, intensely obsessive young man goes to extreme measures to insert himself into the lives of those he is transfixed by.");
            movie4.setGenreId(2);

            Movie movie5 = new Movie();
            movie5.setName("Little Women");
            movie5.setDescription("Jo March reflects back and forth on her life, telling the beloved story of the March sisters - four young women each determined to live life on their own terms.");
            movie5.setGenreId(2);

            Movie movie6 = new Movie();
            movie6.setName("Vikings");
            movie6.setDescription("Vikings transports us to the brutal and mysterious world of Ragnar Lothbrok, a Viking warrior and farmer who yearns to explore - and raid - the distant shores across the ocean.");
            movie6.setGenreId(2);

            Movie movie7 = new Movie();
            movie7.setName("1917");
            movie7.setDescription("Two young British soldiers during the First World War are given an impossible mission: deliver a message deep in enemy territory that will stop 1,600 men, and one of the soldiers' brothers, from walking straight into a deadly trap.");
            movie7.setGenreId(3);

            Movie movie8 = new Movie();
            movie8.setName("The Witcher");
            movie8.setDescription("Geralt of Rivia, a solitary monster hunter, struggles to find his place in a world where people often prove more wicked than beasts.");
            movie8.setGenreId(3);

            Movie movie9 = new Movie();
            movie9.setName("The Outsider");
            movie9.setDescription("Investigators are confounded over an unspeakable crime that's been committed.");
            movie9.setGenreId(3);

            movieDao.insert(movie1);
            movieDao.insert(movie2);
            movieDao.insert(movie3);
            movieDao.insert(movie4);
            movieDao.insert(movie5);
            movieDao.insert(movie6);
            movieDao.insert(movie7);
            movieDao.insert(movie8);
            movieDao.insert(movie9);

            return null;
        }
    }
}
