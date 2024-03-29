package pl.edu.pb.sm_projekt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// PomoItem.java
@Entity(tableName = "pomo_items")
public class PomoItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String title;
    private final int duration;

    public PomoItem(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    // Getter for the id field
    public int getId() {
        return id;
    }

    // Setter for the id field (if needed)
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }
}
