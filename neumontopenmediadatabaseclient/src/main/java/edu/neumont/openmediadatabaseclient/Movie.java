package edu.neumont.openmediadatabaseclient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {

    @JsonProperty(value="Title")
    private String title;

    @JsonProperty(value="Year")
    private String year;

    @JsonProperty(value="imdbID")
    private String imdbId;

    @JsonProperty(value="Type")
    private String type;

    @JsonProperty(value="Poster")
    private String poster;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
