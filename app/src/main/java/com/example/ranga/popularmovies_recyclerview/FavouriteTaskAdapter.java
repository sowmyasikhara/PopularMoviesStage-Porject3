package com.example.ranga.popularmovies_recyclerview;

import android.content.Context;
import android.content.Intent;
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

import Favourite.MovieUser;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class FavouriteTaskAdapter extends RecyclerView.Adapter<FavouriteTaskAdapter.ViewHolder> {

    String path = "https://image.tmdb.org/t/p/w500";
    private Context mFavContext;
    private LayoutInflater mFavInflater;
    private FavouriteTaskAdapter.ItemClickListener mFavClickListener;
    ArrayList<MovieUser> favMoviesList;
    private String[] movie_images;
    final static String LOG_TAG = "FavouriteTaskAdapter";
    private String movie_id;
    private int adapterPosition;


    public FavouriteTaskAdapter(Context c,
                                ArrayList<MovieUser> tasks,
                                FavouriteTaskAdapter.ItemClickListener listener) {

        favMoviesList = tasks;
        this.mFavContext = c;
        mFavInflater = LayoutInflater.from(mFavContext);
        mFavClickListener = listener;

//

        movie_images = new String[favMoviesList.size()];
        for (int i = 0; i < favMoviesList.size(); i++) {
            String poster = favMoviesList.get(i).getMovie_thumbnail();
           // poster = path + poster;
            movie_images[i] = poster;

        }
    }


    @Override
    public int getItemCount() {
        return favMoviesList.size();
    }
    @Override
    @NonNull
    public FavouriteTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mFavInflater.inflate(R.layout.recyclerview_favouritetask, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FavouriteTaskAdapter.ViewHolder holder, int position) {

        Picasso.get()
                .load(movie_images[position])
                .error(R.drawable.picture_unavailable)
                .placeholder(R.drawable.loading_icon1)
                .into(holder.movieImagesView);

    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View view;
        ImageView movieImagesView;
        ImageButton imageButton;

        ViewHolder(View itemView) {
            super(itemView);
            movieImagesView = itemView.findViewById(R.id.imagesViewId);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mFavContext,DeleteTaskActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int movie_id = favMoviesList.get(adapterPosition).getMovieId();
                    i.putExtra("movie_id",movie_id);
                    mFavContext.startActivity(i);
                }
            });

          itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mFavClickListener != null)
                adapterPosition = getAdapterPosition();
            mFavClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenience method for getting data at click position
    String getItem(int id) {
        return movie_images[id];
    }

    // allows clicks events to be caught
    void setClickListener(FavouriteTaskAdapter.ItemClickListener itemClickListener) {
        this.mFavClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


