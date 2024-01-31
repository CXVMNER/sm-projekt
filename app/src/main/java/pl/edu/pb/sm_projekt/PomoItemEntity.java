package pl.edu.pb.sm_projekt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.room.TypeConverters;

@TypeConverters(Converters.class)
@Entity(tableName = "pomo_items")
public class PomoItemEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private int duration;

    public PomoItemEntity(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }
}
