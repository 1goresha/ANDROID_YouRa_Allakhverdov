package com.example.myfavoritemovies.viewmodel;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfavoritemovies.R;
import com.example.myfavoritemovies.databinding.MovieListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private OnItemClickListener onItemClickListener;
    private List<Movie> movieList;

    interface OnItemClickListener {

        void onItemClick(Movie movie);
    }

    public MovieAdapter() {
        movieList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MovieListItemBinding movieListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(movieListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie movie = movieList.get(position);
        holder.movieListItemBinding.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        MovieListItemBinding movieListItemBinding;

        public MovieViewHolder(MovieListItemBinding movieListItemBinding) {
            super(movieListItemBinding.getRoot());          //getRoot() возвращает View

            this.movieListItemBinding = movieListItemBinding;

            movieListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Movie movie = movieList.get(position);

                    if (onItemClickListener != null && movie != null) {
                        onItemClickListener.onItemClick(movie);
                    }
                }
            });
        }
    }
}
