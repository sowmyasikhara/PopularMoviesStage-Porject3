package com.example.ranga.popularmovies_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


import model.Movie;
import utils.MovieNetworkUtils;
import utils.ParseMovieJSONDetails;

public class MainActivity extends AppCompatActivity  implements RecyclerViewAdapter.ItemClickListener{

   private RecyclerViewAdapter mAdapter;
   private Context mContext;
   private RecyclerView mRecyclerView;


    private ImageView imageview;
    Movie obj;

    ArrayList<Movie> allMoviesList;
    private static String response1;
    private String sortByValue;
    public String sortByPref;
    final static String LOG_TAG ="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  gridview = (GridView) findViewById(R.id.gridview);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mContext = MainActivity.this;
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.popular_movie) {
            sortByPref=getString(R.string.popular);
            //sortByValue = String.valueOf(R.string.sort_by_popularity);
            sortByValue=getString(R.string.sort_by_popularity);
            Log.d(LOG_TAG, sortByValue);

            String textToShow = "showing popular movies";
            Toast.makeText(mContext, textToShow, Toast.LENGTH_LONG).show();
            getMovieDetails(sortByValue,sortByPref);

        } else {
            if (itemThatWasClickedId == R.id.top_rated) {

                sortByPref=getString(R.string.top_rated);
                sortByValue = String.valueOf(R.string.sort_by_topRated);
                Log.d(LOG_TAG, sortByValue);
                String textToShow = "showing top rated movies";
                Toast.makeText(mContext, textToShow, Toast.LENGTH_LONG).show();
                getMovieDetails(sortByValue,sortByPref);
            }

            if(itemThatWasClickedId == R.id.favourite){

               Intent i = new Intent(MainActivity.this,FavouriteTaskActivity.class);
               startActivity(i);
            }
            return true;
        }


        return super.onOptionsItemSelected(item);


    }

    @Override
    protected void onStart() {

        //default loading popular movies
        sortByPref = getString(R.string.popular);

        //checking if internet connection is available or not
        if(isNetworkAvailable()) {
            //getting list of movie details
            getMovieDetails(sortByValue,sortByPref);

        }else{
            Toast.makeText(mContext,"Oops,No Internet!Please check Internet Connection",Toast.LENGTH_SHORT).show();
        }
        super.onStart();
    }


    private void getMovieDetails(String sortByValue,String sortByPref) {

        //checking if internet connection is available or not
        if(isNetworkAvailable()) {
            String sortBy = sortByValue;
            URL movieDBSearchURL = MovieNetworkUtils.buildUrl(sortBy,sortByPref);
            new MoviesAsyncTask().execute(movieDBSearchURL);
        }
        else
        {
            Toast.makeText(mContext,"Oops,No Internet!Please check Internet Connection",Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onItemClick(View view, int position) {
        Log.d("item clicked",allMoviesList.get(position).getPlot_synopsis());

        Intent i = new Intent(MainActivity.this, MovieDetailView.class);

        // Pass image index

        String title = allMoviesList.get(position).getTitle();
        i.putExtra("name", title);

        String release_date = allMoviesList.get(position).getRelease_date();
        i.putExtra("release_date", release_date);

        String vote_average = allMoviesList.get(position).getVote_average();
        i.putExtra("vote_average", vote_average);

        String overview = allMoviesList.get(position).getPlot_synopsis();
        i.putExtra("overview", overview);

        String poster_path = allMoviesList.get(position).getMovie_poster();
        i.putExtra("poster_path", poster_path);

        String movie_id = allMoviesList.get(position).getMovie_id();
        i.putExtra("movie_id",movie_id);

        startActivity(i);



    }


    public class MoviesAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];

            try {
                response1 = MovieNetworkUtils.getResponseFromHttpUrl(searchUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return response1;
        }

        @Override
        protected void onPostExecute(String response1) {
            if (response1 != null && !response1.equals("")) {
                loadMovieData(response1);

            }
        }
    }


    private void loadMovieData(String response) {
       // RecyclerViewAdapter.ItemClickListener ItemClickListener = null;

        ParseMovieJSONDetails parse = new ParseMovieJSONDetails();

        allMoviesList = parse.getAllMoviesList(response);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);

        mRecyclerView = (RecyclerView)findViewById(R.id.rvNumbers);


        mAdapter = new RecyclerViewAdapter(MainActivity.this, allMoviesList,this);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private boolean isNetworkAvailable() {
        mContext = MainActivity.this;
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }
}
