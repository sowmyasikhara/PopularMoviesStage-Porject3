package com.example.ranga.popularmovies_recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FavouriteMovieDetailView extends AppCompatActivity {

    private TextView title_name;
    private TextView vote_average;
    private TextView release_date;
    private TextView plot_synopsis;
    private ImageView movie_poster;


    private String title_movie;
    private String vote_avg;
    private String releaseDate;
    private String overview;
    private String poster_path;
    private Context mContextVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_task_view);

            title_name =(TextView)findViewById(R.id.movie_name);
            vote_average=(TextView)findViewById(R.id.voteAverageId);
            release_date=(TextView)findViewById(R.id.releaseDateId);
            plot_synopsis=(TextView)findViewById(R.id.plotSynopsisId);
            movie_poster=(ImageView)findViewById(R.id.poster_movie);



    }
    @Override
    protected void onStart() {
        super.onStart();



        //get intent

        Intent i = getIntent();


        title_movie = i.getExtras().getString("titlename");

        title_name.setText(title_movie);

        vote_avg = i.getExtras().getString("vote_average");
        vote_average.setText(vote_avg);

        releaseDate = i.getExtras().getString("release_date");
        release_date.setText(releaseDate);

        overview = i.getExtras().getString("overview");
        plot_synopsis.setText(overview);

        poster_path = i.getExtras().getString("poster_path");

        Picasso.get()
                .load(poster_path)
                .error(R.drawable.picture_unavailable)
                .placeholder(R.drawable.loading_icon1)
                .into(movie_poster);
    }
}
