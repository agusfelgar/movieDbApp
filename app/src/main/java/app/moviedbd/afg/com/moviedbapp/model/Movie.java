package app.moviedbd.afg.com.moviedbapp.model;

/**
 * Created by Usuario on 03/01/2017.
 */

public class Movie {
    private String title;
    private String release_date;
    private String poster_path;
    private String overview;

    public Movie(String title, String release_date, String poster_path, String overview) {
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    //region GETTER AND SETTER

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String releaseDate) {
        this.release_date = releaseDate;
    }

    public String getImgUrl() {
        return poster_path;
    }

    public void setImgUrl(String imgUrl) {
        this.poster_path = imgUrl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    //endregion
}
