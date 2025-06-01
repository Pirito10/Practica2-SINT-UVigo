package p2;

public class Movie {
    private String id;
    private String title;
    private String year;

    public Movie(String id, String title, String year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }
}
