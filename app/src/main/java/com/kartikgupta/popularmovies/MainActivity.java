package com.kartikgupta.popularmovies;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = BuildConfig.TMDB_KEY;
    private String mSortOrder = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMovieData();


    }

    private void getMovieData() {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(BASE_URL+mSortOrder)
                    .addHeader("api_key",API_KEY)
                    .addHeader("language","en-US")
                    .addHeader("page","1")
                    .build();


            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(response.body().string());
                }
            });
    }
}
