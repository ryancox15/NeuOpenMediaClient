package edu.neumont.openmediadatabaseclient;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieSearchResult {

    @JsonProperty(value="Search")
    private List<Movie> movies;

    @JsonProperty(value="totalResults")
    private String totalResults;

    @JsonProperty(value="Response")
    private String response;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
