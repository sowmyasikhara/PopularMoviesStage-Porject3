package com.example.ranga.popularmovies_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import model.Movie;
import utils.MovieNetworkUtils;
import utils.ParseMovieJSONDetails;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

@SuppressWarnings("RedundantCast")
public class MovieDetailView extends AppCompatActivity {

    private static TextView title_name;
    private static TextView release_date_movie;
    private static TextView vote_average_movie;
    private static TextView overview_movie;
    private static ImageView poster_movie;
   // private static TextView review;
    private static TextView author;
    private static TextView content;
    private Button watch_trailor;
    private Button watch_trailor1;

    private static String path = "https://image.tmdb.org/t/p/w500";
    private Context context;

    private String id;
    private String response;
    private ArrayList<Movie> reviews;
    private String author_id;
    private String content_id;
    private String movie_base_url;
    private String title;
    private String release_date;
    private String vote_average;
    private String overview;
    private String poster;
    final String[] youTubeKey = {null};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_view);

        title_name = (TextView) findViewById(R.id.titleNameId);
        release_date_movie = (TextView) findViewById(R.id.releaseDateId);
        vote_average_movie = (TextView) findViewById(R.id.voteAverageId);
        overview_movie = (TextView) findViewById(R.id.plotSynopsisId);
        poster_movie = (ImageView) findViewById(R.id.imageViewId);
       // review = (TextView) findViewById(R.id.movie_id);
        author = (TextView) findViewById(R.id.author_id);
        content = (TextView) findViewById(R.id.content_id);
        watch_trailor = (Button)findViewById(R.id.button1);
        watch_trailor1=(Button)findViewById(R.id.button2);


        // Get intent data
        Intent i = getIntent();


        title = i.getExtras().getString("name");
        title_name.setText(title);

        release_date = i.getExtras().getString("release_date");
        release_date_movie.setText(release_date);

        vote_average = i.getExtras().getString("vote_average");
        vote_average_movie.setText(vote_average);

        overview = i.getExtras().getString("overview");
        overview_movie.setText(overview);

        poster = i.getExtras().getString("poster_path");
        poster = path + poster;

        id = i.getExtras().getString("movie_id");
       // review.setText(id);


        Picasso.get()
                .load(poster)
                .error(R.drawable.picture_unavailable)
                .placeholder(R.drawable.loading_icon1)
                .into(poster_movie);

        movie_base_url = getString(R.string.base_url);
        getMovieVideoTrailer(movie_base_url,id);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.moviesmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClickedId = item.getItemId();

        if(itemClickedId == R.id.favouriteId){

            Intent i = new Intent(MovieDetailView.this,InsertTaskActivity.class);
            int movieID = Integer.parseInt(id);
             i.putExtra("movie_id",movieID);
             i.putExtra("movie_title_name",title);

            i.putExtra("movie_release_date",release_date);

            i.putExtra("movie_vote_avg",vote_average);
            Log.d("movie_vote_avg",vote_average);


            i.putExtra("movie_poster_path",poster);

            i.putExtra("movie_overview",overview);
            startActivity(i);


        }

       if(itemClickedId  == R.id.shareId) {
           {
                   Intent shareIntent = new Intent(Intent.ACTION_SEND);
                   Uri videoLink = Uri.parse("http://www.youtube.com/watch?v="+youTubeKey[0]);
                   shareIntent.setType("video/*");
                   shareIntent.putExtra(Intent.EXTRA_STREAM,videoLink);
                   shareIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                   startActivity(Intent.createChooser(shareIntent, "Share File Using!"));

               }

        }

        return true;
    }

    @Override
    protected void onStart() {
        getMovieReviewDetails(movie_base_url, id);

        super.onStart();
    }
//getting movie reviews

    private void getMovieReviewDetails(String movie_base_url, String movie_id) {
        URL movieDBSearchURL = MovieNetworkUtils.getReviewUrl(movie_base_url, movie_id);
        new MovieReviewAsyncTask().execute(movieDBSearchURL);

    }


    public class MovieReviewAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];

            try {
                response = MovieNetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null && !response.equals("")) {
                loadMovieReviewData(response);

            }
            super.onPostExecute(response);
        }
    }

    private void loadMovieReviewData(String response) {


        reviews = new ArrayList();
        ParseMovieJSONDetails parse = new ParseMovieJSONDetails();


        reviews = parse.getMovieReviews(response);

        if (reviews != null) {
            for (int i = 0; i < reviews.size(); i++) {
                author_id = reviews.get(i).getAuthor();
                content_id = reviews.get(i).getContent();
                author.setText(author_id);
                content.setText(content_id);
            }
        }
            else if (reviews == null) {
                author.setText(" review not available");
                content.setText(" review not available");
            }

    }
//getting video trailer details
private void getMovieVideoTrailer(String movie_base_url, String movie_id) {
    URL movieDBSearchURL = MovieNetworkUtils.getMovieVideoUrl(movie_base_url, movie_id);
    new MovieVideoAsyncTask().execute(movieDBSearchURL);

}
public class MovieVideoAsyncTask extends AsyncTask<URL, Void, String> {
        String movieVideoResponse;
    @Override
    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];

        try {
            movieVideoResponse = MovieNetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieVideoResponse;
    }

    @Override
    protected void onPostExecute(String response) {
        if (response != null && !response.equals("")) {
            loadTrailerMovieVideo(response);

        }
        super.onPostExecute(response);
    }
}

    private void loadTrailerMovieVideo(String response) {

        URL url = MovieNetworkUtils.getMovieVideoUrl(movie_base_url, id);
        final ArrayList youtubeKeys;


        ParseMovieJSONDetails parse = new ParseMovieJSONDetails();


        youtubeKeys = parse.getMovieVideoTrailerKey(response);


        watch_trailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (youtubeKeys!=null) {
                    for (int i = 0; i < youtubeKeys.size(); i++) {
                        youTubeKey[0] = String.valueOf(youtubeKeys.get(0));

                    }
                    Intent i = getIntent();
                    i.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + youTubeKey[0]));

                    startActivity(i);

                } else if(youtubeKeys==null){
                    Toast.makeText(getApplicationContext(), "Trailor not available", Toast.LENGTH_LONG).show();
                }





            }
        });

        watch_trailor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (youtubeKeys!=null) {

                        for (int i = 1; i < youtubeKeys.size(); i++) {
                            youTubeKey[0] = String.valueOf(youtubeKeys.get(i));

                        }
                            Intent intent = getIntent();
                            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + youTubeKey[0]));

                    startActivity(intent);
                } else if (youtubeKeys == null) {
                    Toast.makeText(getApplicationContext(), "Trailor not available", Toast.LENGTH_LONG).show();
                    Log.d("trailor 2",youTubeKey[1]);
                }

            }

        });

    }

}
