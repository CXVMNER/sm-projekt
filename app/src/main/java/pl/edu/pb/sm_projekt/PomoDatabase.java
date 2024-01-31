package pl.edu.pb.sm_projekt;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PomoItem.class}, version = 1)
public abstract class PomoDatabase extends RoomDatabase {
    private static PomoDatabase instance;

    public abstract PomoDao pomoItemDao();

    public static synchronized PomoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            PomoDatabase.class, "pomo_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
