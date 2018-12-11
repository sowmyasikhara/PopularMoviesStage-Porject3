package model;

import android.os.Parcelable;

import java.util.ArrayList;

public class Movie extends ArrayList<Movie> {

    private String title;
    private String release_date;
    private String movie_poster;
    private String vote_average;
    private String plot_synopsis;
    private String movie_id;
    private String movie_reviews;
    private String author;
    private String content;


    public Movie(){
    }

    public Movie(String title, String release_date, String movie_poster, String vote_average, String plot_synopsis, String movie_id){
        this.title = title;
        this.release_date = release_date;
        this.movie_poster = movie_poster;
        this.vote_average = vote_average;
        this.plot_synopsis = plot_synopsis;
        this.movie_id = movie_id;
    }

    public Movie(String author,String content){
        this.author = author;
        this.content = content;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getMovie_poster() {

        return movie_poster;
    }
    public String getMovie_id(){
        return movie_id;
    }
    public String getAuthor(){
        return author;
    }

    public String getContent(){
        return content;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }

    public void setMovie_id(String movie_id){
        this.movie_id = movie_id;
    }

    public void setAuthor(String author){
        this.author = author;
    }
    public void setContent(String content){
        this.content = content;
    }
}
