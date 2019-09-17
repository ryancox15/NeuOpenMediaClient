package edu.neumont.openmediadatabaseclient;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This is a simple library that can be used by Computer Science
 * students at very early skill levels to experience the excitement
 * of building an app that accesses remote api data.
 * Methods like the synchronous form of searchMoviesByTitle should
 * never be used in a production app as the UI will lock up while
 * waiting for an API response.
 */
public class NeuOpenMediaClient {

    private static final String OMDB_API_BASE_URL = "http://www.omdbapi.com";

    private int timeoutSeconds = 5 * 60; // default to 5 minutes

    private String apiKey;

    private Retrofit retrofit;

    public NeuOpenMediaClient(String myApiKey) {
        this.setApiKey(myApiKey);
        this.retrofit = new Retrofit.Builder()
                .baseUrl(OMDB_API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        if(timeoutSeconds < 1) {
            throw new IllegalArgumentException("timeoutSeconds must be greater than 0");
        }
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        if(apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("apiKey cannot be null or empty");
        }
        this.apiKey = apiKey;
    }

    public void searchMoviesByTitle(String searchTitle, final Consumer<MovieSearchResult> onCompleteCallback) {
        searchMoviesByTitleAux(searchTitle, onCompleteCallback);
    }

    public MovieSearchResult searchMoviesByTitle(String searchTitle) {
        AsyncTask<String, String, MovieSearchResult> task = searchMoviesByTitleAux(searchTitle, null);
        MovieSearchResult movieSearchResult = null;
        try {
            movieSearchResult = task.get(this.getTimeoutSeconds(), TimeUnit.SECONDS);
        }
        catch (ExecutionException | InterruptedException | TimeoutException ex) {
            throw new RuntimeException(ex);
        }
        return movieSearchResult;
    }

    private AsyncTask<String, String, MovieSearchResult> searchMoviesByTitleAux(String searchTitle, final Consumer<MovieSearchResult> onCompleteCallback) {
        return new AsyncTask<String, String, MovieSearchResult>() {
            @Override
            protected MovieSearchResult doInBackground(String... strings) {
                try {
                    OMDBSvc service = retrofit.create(OMDBSvc.class);
                    String searchText = strings[0];
                    Call<MovieSearchResult> result = service.searchMovies(searchText, NeuOpenMediaClient.this.getApiKey());
                    Response<MovieSearchResult> response = result.execute();
                    MovieSearchResult movies = response.body();
                    return movies;
                }
                catch(Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            protected void onPostExecute(MovieSearchResult movies) {
                if(onCompleteCallback != null) {
                    onCompleteCallback.accept(movies);
                }
            }
        }.execute(searchTitle);
    }

    private interface OMDBSvc {

        @GET()
        Call<MovieSearchResult> searchMovies(@Query("s") String title, @Query("apikey") String apiKey);

    }
}
