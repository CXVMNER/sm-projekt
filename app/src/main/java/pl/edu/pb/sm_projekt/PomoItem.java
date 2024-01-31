package pl.edu.pb.sm_projekt;

// PomoItem.java
public class PomoItem {
    private final String title;
    private final int duration;

    public PomoItem(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }
}
