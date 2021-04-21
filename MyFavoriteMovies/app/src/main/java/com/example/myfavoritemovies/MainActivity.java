package com.example.myfavoritemovies;

import android.os.Bundle;

import com.example.myfavoritemovies.databinding.ActivityMainBinding;
import com.example.myfavoritemovies.model.Genre;
import com.example.myfavoritemovies.model.Movie;
import com.example.myfavoritemovies.viewmodel.MainActivityViewModel;
import com.example.myfavoritemovies.viewmodel.MovieAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandler clickHandler;
    private Genre selectedGenre;
    private ArrayList<Genre> genreArrayList;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        clickHandler = new MainActivityClickHandler();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setMainActivityClickedHandler(clickHandler);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainActivityViewModel.class);
        viewModel.getGenres().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genres) {

                genreArrayList = (ArrayList<Genre>) genres;

                showGenresInSpinner();

                for (Genre g :
                        genres) {
                    Log.d("MyLog", g.getGenreName());
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void showGenresInSpinner() {

        ArrayAdapter<Genre> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, genreArrayList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setArrayAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadGenreMoviesInArrayList(int genreId) {
//        Genre genre = (Genre) activityMainBinding.secondaryLayout.spinner.getItemAtPosition(position);

        viewModel.getMoviesByGenreId(genreId).observe(this, new Observer<List<Movie>>() {

            @Override
            public void onChanged(List<Movie> movies) {

                movieList = movies;
                loadMovieAdapter();
            }
        });
    }

    private void loadMovieAdapter() {

        this.movieAdapter = new MovieAdapter();
        movieAdapter.setMovieList(movieList);           //это можно было сделать и в конструкторе MovieAdapter...

        this.recyclerView = this.activityMainBinding.secondaryLayout.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);

    }

    public class MainActivityClickHandler {          //отдельный созданный класс для обработчика событься нажатия на spinner для data binding с content_main.xml

        public void onFabClick(View view) {

            Toast.makeText(MainActivity.this, "Fab is clicked", Toast.LENGTH_SHORT).show();
        }

        public void onSelectedItem(AdapterView<?> parent, View view, int position, long id) {

            selectedGenre = (Genre) parent.getItemAtPosition(position);
            loadGenreMoviesInArrayList(selectedGenre.getId());

            String s = "id = " + selectedGenre.getId() + " , name " + selectedGenre.getGenreName();
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }

    }
}
