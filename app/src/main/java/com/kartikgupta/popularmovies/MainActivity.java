package com.kartikgupta.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MoviesRecyclerViewAdapter.ItemClickListener {


    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = BuildConfig.TMDB_KEY;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String mSortOrder = "popular";
    private JSONObject mMovieListJsonObject;
    private JSONArray mMovieListResultsJsonArray;
    private RecyclerView mMovieGridRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MoviesRecyclerViewAdapter mMoviesRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMovieData();
        mMovieGridRecyclerView = (RecyclerView) findViewById(R.id.movieGridRecyclerView);
       // mMovieGridRecyclerView.setHasFixedSize(true);//TODO:check its effects

        mLayoutManager = new GridLayoutManager(this, 2);

        mMovieGridRecyclerView.setLayoutManager(mLayoutManager);
        mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter( mMovieListResultsJsonArray
                //getMovieListJsonArray()
                , this);
        mMoviesRecyclerViewAdapter.setClickListener(this);
        mMovieGridRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);
        //setupMoviesGridRecyclerView();

    }

    private void setupMoviesGridRecyclerView() {
        if(mMovieListResultsJsonArray ==null) {
            //TODO:add error message
            Log.e(TAG,"movie data json object is null");
        }else {
            mMovieGridRecyclerView = (RecyclerView) findViewById(R.id.movieGridRecyclerView);
            mMovieGridRecyclerView.setHasFixedSize(true);//TODO:check its effects

            mLayoutManager = new GridLayoutManager(this, 2);

            mMovieGridRecyclerView.setLayoutManager(mLayoutManager);
            mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter( mMovieListResultsJsonArray
                                                        //getMovieListJsonArray()
                                                                                , this);
            mMovieGridRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);
        }
    }

    private JSONArray getMovieListJsonArray() {
        JSONArray jsonArray = null;
        try {
            jsonArray = mMovieListJsonObject.getJSONArray("results");
            System.out.print(jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private void getMovieData() {
            OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("api_key", API_KEY)
                .addFormDataPart("language", "en-US")
              //  .addFormDataPart("page", "1")
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
                String responseString=response.body().string();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                Log.v(TAG,"response obtained is :-\n"+responseString);
                JSONObject jsonObject=null;
                try {
                jsonObject = new JSONObject(responseString);
                    Log.i(TAG,"Response converted to JsonObject without exception");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mMovieListJsonObject = jsonObject;
                updateData();



                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupMoviesGridRecyclerView();

                    }
                });
*/

            }
        });

    }

    private void updateData()  {
        try {
            mMovieListResultsJsonArray = mMovieListJsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.v(TAG,"updated movies data should reflect now");
                mMoviesRecyclerViewAdapter.setMoviesData(mMovieListResultsJsonArray);
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.v(TAG, "You clicked number " + position + ", which is at cell position " + position);
        JSONObject jsonObject = null;
        try {
            jsonObject = mMovieListResultsJsonArray.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jsonObject!=null){
            Intent intent = new Intent(this,MovieDetailsActivity.class);
            intent.putExtra("movieJSONObject",jsonObject.toString());
            startActivity(intent);
        }

    }
}
