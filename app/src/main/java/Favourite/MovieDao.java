package Favourite;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie_details")
    List<MovieUser> getAll();

    @Insert
    public void addMovieDetails(MovieUser movieUser);

    @Query("DELETE FROM movie_details where movieId = :movieId")
    public void deleteBymovieId(int movieId);

    @Query("SELECT movie_name FROM movie_details where movieId = :movieId")
    public String getMovieIdDetailsFromDB(int movieId);

    //@Delete
    //public void deleteMovieDetails(MovieUser movieUser);


}
