package com.example.movies.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.MovieRecyclerViewAdapter;
import com.example.movies.R;
import com.example.movies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = "LOG";

    public static final String ADDRESS = "http://www.omdbapi.com/?s=";
    public static final String APIKEY = "&apikey=339aec6c";


    List<Movie> movieList;
    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {

                if (msg.what != 0) {
                    Toast.makeText(MainActivity.this, "Найдено : " + msg.what, Toast.LENGTH_SHORT).show();
                }
            }
        };

        movieList = new ArrayList<>();

        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(movieList);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieRecyclerViewAdapter);

        requestQueue = Volley.newRequestQueue(this);

        movieRecyclerViewAdapter.setOnItemClickListener(new MovieRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Movie movie = movieList.get(position);

                Intent intent = new Intent(getApplicationContext(), ShowMovieDetails.class);
                intent.putExtra("imdbID", movie.getImdbID());

                startActivityForResult(intent, 1);
            }
        });

    }

    private void getMovies(final String requestString) {

        final String url = ADDRESS + requestString + APIKEY;

        new Thread(new Runnable() {
            @Override
            public void run() {

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("Response").equals("False")) {
                                        return;
                                    }

                                    JSONArray jsonArray = response.getJSONArray("Search");
                                    handler.sendEmptyMessage(jsonArray.length());

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Movie movie = new Movie();
                                        movie.setImdbID(jsonArray.getJSONObject(i).getString("imdbID"));
                                        movie.setTitle(jsonArray.getJSONObject(i).getString("Title"));
                                        movie.setYear(jsonArray.getJSONObject(i).getString("Year"));
                                        movie.setPoster(jsonArray.getJSONObject(i).getString("Poster"));

                                        movieList.add(movie);
                                        movieRecyclerViewAdapter.notifyDataSetChanged();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });

                requestQueue.add(request);
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                movieList.clear();
                getMovies(s);
                movieRecyclerViewAdapter.notifyDataSetChanged();

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Log.i(LOG, "delivered");
        }else{
            Log.i(LOG, "undelivered");
        }
    }
}
