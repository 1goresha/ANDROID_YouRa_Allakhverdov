package com.example.movies;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    public OnItemClickListener onItemClickListener;

    public MovieRecyclerViewAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {// здесь накачиваем(inflate) view и отдаем ViewHolder(view), когда он создается

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie movie = movieList.get(position);

        holder.title.setText(TextUtils.isEmpty(movie.getTitle()) ? "empty" : movie.getTitle());
        holder.year.setText(TextUtils.isEmpty(movie.getYear()) ? "empty" : movie.getYear());
        Picasso.get().load(movie.getPoster()).fit().centerInside().into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView year;
        ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            year = itemView.findViewById(R.id.year);
            poster = itemView.findViewById(R.id.poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
