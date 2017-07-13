package com.kartikgupta.popularmovies;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = BuildConfig.TMDB_KEY;
    private String mSortOrder = "popular";
    private JSONObject mMovieListJsonObject;
    private RecyclerView mMovieGridRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMovieData();
        if(mMovieListJsonObject==null){
            //TODO:add error message
        }
        mMovieGridRecyclerView = (RecyclerView) findViewById(R.id.movieGridRecyclerView);
        mMovieGridRecyclerView.setHasFixedSize(true);//TODO:check its effects

        mLayoutManager = new GridLayoutManager(this,2);

        mMovieGridRecyclerView.setLayoutManager(mLayoutManager);



    }

    private void getMovieData() {
            OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("api_key", API_KEY)
                .addFormDataPart("language", "en-US")
                .addFormDataPart("page", "1")
                .build();


        Request request = new Request.Builder()
                .url(BASE_URL + mSortOrder)
                .post(requestBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                JSONObject jsonObject=null;
                try {
                jsonObject = new JSONObject(String.valueOf(response.body()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mMovieListJsonObject = jsonObject;
                //System.out.println(response.body().string());

            }
        });

    }
}
