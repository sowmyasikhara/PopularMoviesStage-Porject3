package Favourite;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

public class AppDatabase {


    private Context mCtx;
    private static AppDatabase mInstance;

    //our app database object
    private MovieDatabase movieAppDatabase;

    private AppDatabase(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MovieDB is the name of the database
        movieAppDatabase = Room.databaseBuilder(mCtx, MovieDatabase.class, "MovieDB")
                .fallbackToDestructiveMigration()
                .build();
        Log.d("database created","in appdatabase");
    }

    public static synchronized AppDatabase getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new AppDatabase(mCtx);
        }
        return mInstance;
    }

    public MovieDatabase getAppDatabase() {
        return movieAppDatabase;
    }
}