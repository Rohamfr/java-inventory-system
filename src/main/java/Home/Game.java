package Home;
import java.io.Serializable;

public class Game implements Serializable {
    private int id;
    private String title;
    private String platform;
    private String genre;
    private int year;

    public Game(int id, String title, String platform, String genre, int year) {
        this.id = id;
        this.title = title;
        this.platform = platform;
        this.genre = genre;
        this.year = year;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getPlatform() { return platform; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }

    public void setTitle(String title) { this.title = title; }
    public void setPlatform(String platform) { this.platform = platform; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return id + " - " + title + " (" + platform + ", " + genre + ", " + year + ")";
    }
}
