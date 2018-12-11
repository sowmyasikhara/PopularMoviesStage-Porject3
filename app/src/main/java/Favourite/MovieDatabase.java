package Favourite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {MovieUser.class},version = 2,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase{

   public abstract MovieDao movieDao();


}
