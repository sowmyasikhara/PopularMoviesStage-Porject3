package com.example.ranga.popularmovies_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.Movie;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int adapterPosition;

    List<Movie> poster_images;
    String path = "https://image.tmdb.org/t/p/w500";
    private String poster;
    private String[] movie_images;
    final static String LOG_TAG = "MovieImageAdapter";

//constructor

    public RecyclerViewAdapter(Context c, ArrayList<Movie> poster_paths,ItemClickListener listener) {
        Log.d(LOG_TAG, "Constructor called");


        mContext = c;
        this.mInflater = LayoutInflater.from(mContext);
        mClickListener = listener;


        poster_images = poster_paths;
        movie_images = new String[poster_images.size()];
        for (int i = 0; i < poster_images.size(); i++) {
            poster = poster_images.get(i).getMovie_poster();
            poster = path + poster;
            movie_images[i] = poster;
            Log.d("poster adapter", poster);
        }
    }

  /*  public int getCount() {
        Log.d(LOG_TAG, String.valueOf(poster_images.size()));
        return poster_images.size();
    }*/

    public long getItemId(int position) {
        return position;
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return poster_images.size();
    }
    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get()

                .load(movie_images[position])
                .error(R.drawable.picture_unavailable)
                .placeholder(R.drawable.loading_icon1)
                .into(holder.movieImageView);




    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View view;
        ImageView movieImageView;


        ViewHolder(View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.imageViewId);

            itemView.setOnClickListener(this);
        }




        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                adapterPosition = getAdapterPosition();
            Log.d("item clicked",String.valueOf(getAdapterPosition()));

                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenience method for getting data at click position
    String getItem(int id) {
        return movie_images[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


