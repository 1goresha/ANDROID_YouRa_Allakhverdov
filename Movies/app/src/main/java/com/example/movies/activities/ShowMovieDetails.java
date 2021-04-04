package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.R;
import com.example.movies.data.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowMovieDetails extends AppCompatActivity {

    ImageView imageView;
    ListView listView;
    RequestQueue requestQueue;
    ArrayAdapter<String> arrayAdapter;
    List<String> arrayList;

    private static final String title = "Title";
    private static final String year = "Year";
    private static final String rated = "Rated";
    private static final String released = "Released";
    private static final String runtime = "Runtime";
    private static final String genre = "Genre";
    private static final String director = "Director";
    private static final String writer = "Writer";
    private static final String actors = "Actors";
    private static final String plot = "Plot";
    private static final String language = "Language";
    private static final String country = "Country";
    private static final String awards = "Awards";
    private static final String poster = "Poster";
    private static final String ratingsArray = "Ratings";
    private static final String imdbRating = "imdbRating";
    private static final String imdbVotes = "imdbVotes";
    private static final String imdbID = "imdbID";
    private static final String type = "Type";
    private static final String dVD = "DVD";
    private static final String boxOffice = "BoxOffice";
    private static final String production = "Production";
    private static final String website = "Website";
    private static final String response = "Response";

    String[] stringArray = {
        title, year, rated, released, runtime, genre, director, writer, actors, plot, language, country, awards, poster, ratingsArray, imdbRating, imdbVotes, imdbID, type, dVD, boxOffice, production, website, response
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_details);

        imageView = (ImageView)findViewById(R.id.imageDetails);
        listView = (ListView)findViewById(R.id.listViewDetails);

        requestQueue = Volley.newRequestQueue(this);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        Intent intent = getIntent();
        String imdbID = intent.getStringExtra("imdbID");

        String url = "http://www.omdbapi.com/?i=" + imdbID + "&plot=full" + MainActivity.APIKEY;

        if (intent == null && imdbID == null){
            Log.e("LOG", "ERROR");
        }

        getMovieInfo(url);
    }

    public void fromJSONtoArrayList(String[] stringArray, JSONObject response){


        for (int i = 0; i < stringArray.length; i++){
            try {
                String key = stringArray[i];
                String value = response.getString(stringArray[i]);
                if (!TextUtils.isEmpty(value)){
                    arrayList.add(key + " : " + value + "\n");
                    arrayAdapter.notifyDataSetChanged();
                    if (key.equals(poster)){
                        Picasso.get().load(value).fit().centerInside().into(imageView);
                    }
                }else{
                    arrayList.add("EMPTY" + "\n");
                    arrayAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public void getMovieInfo(String url){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        fromJSONtoArrayList(stringArray, response);

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
}
