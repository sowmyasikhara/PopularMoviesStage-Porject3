package com.example.ranga.popularmovies_recyclerview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import Favourite.AppDatabase;
import Favourite.MovieUser;

public class DeleteTaskActivity extends AppCompatActivity {

   private MovieUser movieUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        movieUser = new MovieUser();
        Intent i = getIntent();
        int movieId = i.getExtras().getInt("movie_id");
        movieUser.setMovieId(movieId);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        DeleteTask st = new DeleteTask();
        st.execute();
        super.onStart();
    }

    class DeleteTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Log.d("in asyc task","delete class");


            //deleting form database
            AppDatabase.getInstance(getApplicationContext()).getAppDatabase()
                    .movieDao()
                    .deleteBymovieId(movieUser.getMovieId());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
            startActivity(new Intent(getApplicationContext(), FavouriteTaskActivity.class));
            Toast.makeText(getApplicationContext(), "Favourite Movie deleted", Toast.LENGTH_LONG).show();
        }
    }
}
