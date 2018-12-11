package utils;

import android.net.Uri;
import android.util.Log;

import com.example.ranga.popularmovies_recyclerview.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class MovieNetworkUtils {
        static String MOVIEDB_URL =
        "https://api.themoviedb.org/3/movie/";
        /*
         * Default: results are sorted by best match if no field is specified.
         */
        final static String PARAM_SORT = "sort_by";
        final static String API_KEY = "06a566cf4b59a36e14f0e4ab07ab8f85";
        final static String PARAM_API_KEY = "api_key";
        static String PARAM_SORT_VALUE = "primary_release_year=2018";
        final static String LOG_TAG="MovieNetworkUtils";
        final static String PARAM_REVIEW = "reviews";
        final static String PARAM_VIDEO="videos";

        /**
         * Builds the URL used to query movie database.
         *

         * @return The URL to use to query the movie db server.
         */
        public static URL buildUrl(String Sort_preference,String sort_pref_value){

          //getting movie details from TMDB

            PARAM_SORT_VALUE = Sort_preference;

            MOVIEDB_URL = sort_pref_value;
            Log.d("finalmoviedburl",MOVIEDB_URL);

            Uri builtUri = Uri.parse(MOVIEDB_URL).buildUpon()
                    .appendQueryParameter(PARAM_SORT,PARAM_SORT_VALUE)
                    .appendQueryParameter(PARAM_API_KEY,API_KEY)
                    .build();

            URL url = null;
            try {
                url = new URL(builtUri.toString());

                Log.d(LOG_TAG,url.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return url;
        }

        /**
         * This method returns the entire result from the HTTP response.
         *
         * @param url The URL to fetch the HTTP response from.
         * @return The contents of the HTTP response.
         * @throws IOException Related to network and stream reading
         */
        public static String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }


    public static URL getReviewUrl(String movie_base_url,String movie_id){
            MOVIEDB_URL = movie_base_url;


            Uri builtUri = Uri.parse(MOVIEDB_URL).buildUpon()
                .appendPath(movie_id)
                .appendPath(PARAM_REVIEW)
                .appendQueryParameter(PARAM_API_KEY,API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());

            Log.d(LOG_TAG,url.toString());
            Log.d("get review url",url.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL getMovieVideoUrl(String movie_base_url,String movie_id){
        MOVIEDB_URL = movie_base_url;


        Uri builtUri = Uri.parse(MOVIEDB_URL).buildUpon()
                .appendPath(movie_id)
                .appendPath(PARAM_VIDEO)
                .appendQueryParameter(PARAM_API_KEY,API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());

            Log.d(LOG_TAG,url.toString());
            Log.d("get video url",url.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    }


