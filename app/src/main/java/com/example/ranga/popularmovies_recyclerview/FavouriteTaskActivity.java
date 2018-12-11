package com.example.ranga.popularmovies_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import Favourite.AppDatabase;
import Favourite.MovieUser;

public class FavouriteTaskActivity extends AppCompatActivity implements FavouriteTaskAdapter.ItemClickListener {


    private FavouriteTaskAdapter mFavAdapter;
    private Context mFavContext;
    private RecyclerView mFavRecyclerView;

    ArrayList<MovieUser> favMoviesList;
    private ImageView imageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        mFavContext = getApplicationContext();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mFavContext,2);

        mFavRecyclerView = (RecyclerView)findViewById(R.id.rvFavourite);
        mFavRecyclerView.setLayoutManager(gridLayoutManager);

    }

    @Override
    protected void onStart() {
        new GetTasks().execute();
        favMoviesList = new ArrayList<MovieUser>();
        super.onStart();
    }

    @Override
    public void onItemClick(View view, int position) {


        Intent i = new Intent(FavouriteTaskActivity.this, FavouriteMovieDetailView.class);

        // Pass image index

        String title = favMoviesList.get(position).getMovie_name();

        i.putExtra("titlename", title);

        String release_date = favMoviesList.get(position).getMovie_release_date();
        i.putExtra("release_date", release_date);

        String vote_average = favMoviesList.get(position).getMovie_vote_average();
        i.putExtra("vote_average", vote_average);

        String overview = favMoviesList.get(position).getMovie_overview();
        i.putExtra("overview", overview);

        String poster_path = favMoviesList.get(position).getMovie_thumbnail();
        i.putExtra("poster_path", poster_path);

        int movie_id = favMoviesList.get(position).getMovieId();
        i.putExtra("movie_id",movie_id);

        startActivity(i);
    }

    class GetTasks extends AsyncTask<Void, Void, ArrayList<MovieUser>> {

            @Override
            protected ArrayList<MovieUser> doInBackground(Void... voids) {

                List<MovieUser> favMoviesList = AppDatabase.getInstance(getApplicationContext()).getAppDatabase()
                        .movieDao()
                        .getAll();


                return (ArrayList<MovieUser>) favMoviesList;
            }

            @Override
            protected void onPostExecute(ArrayList<MovieUser> tasks) {
                super.onPostExecute(tasks);
               loadMovieData(tasks);

            }
        }


        private void loadMovieData(ArrayList<MovieUser> list){
            favMoviesList = list;

            for(int i =0;i<favMoviesList.size();i++){
                String thumbnail = favMoviesList.get(i).getMovie_thumbnail();

            }
            mFavAdapter = new FavouriteTaskAdapter(mFavContext,favMoviesList,this);

            mFavRecyclerView.setAdapter(mFavAdapter);
        }
    }

