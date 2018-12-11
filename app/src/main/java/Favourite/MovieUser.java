package Favourite;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movie_details")
public class MovieUser {

    @PrimaryKey
    private int movieId;

    private String movie_name;

    private String movie_thumbnail;

    private String movie_release_date;

    private String movie_vote_average;

    private String movie_overview;


    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovie_overview() {
        return movie_overview;
    }

    public void setMovie_overview(String movie_overview) {
        this.movie_overview = movie_overview;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_release_date() {
        return movie_release_date;
    }

    public void setMovie_release_date(String movie_release_date) {
        this.movie_release_date = movie_release_date;
    }

    public String getMovie_thumbnail() {
        return movie_thumbnail;
    }

    public void setMovie_thumbnail(String movie_thumbnail) {
        this.movie_thumbnail = movie_thumbnail;
    }

    public String getMovie_vote_average() {
        return movie_vote_average;
    }

    public void setMovie_vote_average(String movie_vote_average) {
        this.movie_vote_average = movie_vote_average;
    }


    public MovieUser(){

    }
    //constructor

    public MovieUser(int movie_id,String movie_name,String movie_release_date,String movie_vote_average,String movie_thumbnail,String movie_overview){
        this.movieId = movie_id;
        this.movie_name = movie_name;
        this.movie_release_date = movie_release_date;
        this.movie_overview = movie_overview;
        this.movie_vote_average = movie_vote_average;
        this.movie_thumbnail = movie_thumbnail;
    }
}
