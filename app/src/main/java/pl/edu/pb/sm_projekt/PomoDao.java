package pl.edu.pb.sm_projekt;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PomoDao {
    @Insert
    void insert(PomoItem pomoItem);

    @Update
    void update(PomoItem pomoItem);

    @Delete
    void delete(PomoItem pomoItem);

    @Query("SELECT * FROM pomo_items")
    List<PomoItem> getAllPomoItems(); // Using LiveData for real-time updates

    @Query("SELECT * FROM pomo_items WHERE title = :title LIMIT 1")
    PomoItem getPomoItemByTitle(String title);
}

