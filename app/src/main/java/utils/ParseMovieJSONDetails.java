package utils;

import android.util.Log;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.Movie;

public class ParseMovieJSONDetails {


    static Movie movieObj;
    ArrayList<Movie> allMoviesList;
    ArrayList reviews;

    final static String LOG_TAG="ParseJSONData";

    public ArrayList<Movie> getAllMoviesList(String movieDbresponse) {
        int indexOfMovie =0;
        try {
            Log.d(LOG_TAG, movieDbresponse);
            JSONObject movieJSON = new JSONObject(movieDbresponse);
            JSONArray resultsJSON;

            //noinspection Convert2Diamond
            allMoviesList = new ArrayList<Movie>();

            resultsJSON = movieJSON.getJSONArray("results");
            Log.d(LOG_TAG, resultsJSON.toString());

            for (int i = 0; i < resultsJSON.length(); i++) {

                JSONObject jsonObj = resultsJSON.optJSONObject(i);

                String title = jsonObj.getString("title");

                String release_date = jsonObj.getString("release_date");

                String poster_path = jsonObj.getString("poster_path");

                String vote_average = jsonObj.getString("vote_average");

                String plot_synopsis = jsonObj.getString("overview");

                String movie_id = jsonObj.getString("id");
                Log.d("movie id",movie_id);


                movieObj = new Movie(title,release_date,poster_path,vote_average,plot_synopsis,movie_id);

                movieObj.setTitle(title);
                movieObj.setRelease_date(release_date);
                movieObj.setMovie_poster(poster_path);
                movieObj.setVote_average(vote_average);
                movieObj.setPlot_synopsis(plot_synopsis);
                movieObj.setMovie_id(movie_id);



                allMoviesList.add(movieObj);
                Log.d(LOG_TAG,allMoviesList.toString());

                indexOfMovie++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return allMoviesList;
    }



    public ArrayList getMovieReviews(String response){

        reviews = new ArrayList();

        Log.d("response review",response);

        JSONObject movieJSON = null;
        JSONArray resultsJSON;
        try {
            movieJSON = new JSONObject(response);
            resultsJSON = movieJSON.getJSONArray("results");

            Log.d("review json",resultsJSON.toString());

            for(int i =0;i<resultsJSON.length();i++){
                JSONObject jsonObj = resultsJSON.optJSONObject(i);
                String author = jsonObj.getString("author");
                Log.d("author",author);

                String content = jsonObj.getString("content");
                Log.d("content",content);



                movieObj = new Movie(author,content);

                movieObj.setAuthor(author);
                movieObj.setContent(content);

                reviews.add(movieObj);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }


    public ArrayList getMovieVideoTrailerKey(String response){
        String youTubeKey = null;
        ArrayList youtubeKeys = null;

        Log.d("response video",response);

        JSONObject movieJSON = null;
        JSONArray resultsJSON;
        try {
            movieJSON = new JSONObject(response);
            resultsJSON = movieJSON.getJSONArray("results");

            Log.d("video json",resultsJSON.toString());

            for(int i =0;i<resultsJSON.length();i++){
                JSONObject jsonObj = resultsJSON.optJSONObject(i);
                youTubeKey = jsonObj.getString("key");
                Log.d("key youtube",youTubeKey);
                youtubeKeys = new ArrayList();
                youtubeKeys.add(youTubeKey);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return youtubeKeys;
    }


}
