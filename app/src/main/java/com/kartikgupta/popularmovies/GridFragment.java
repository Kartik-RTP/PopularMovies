package com.kartikgupta.popularmovies;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kartik on 29/8/16.
 */
public class GridFragment extends Fragment {

    private static final String TAG =GridFragment.class.getSimpleName() ;

    JSONObject mResponse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid,container,false);
        GridView gridView = (GridView) view.findViewById(R.id.moviePostersGridView);
        //intialisze a new adapter with list of movie image paths obtained from network call response




        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.themoviedb.org/3/movie/popular?api_key="+BuildConfig.TMDB_KEY).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"failiure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"success");
                try {
                    mResponse = new JSONObject(response.body().string());
                    Log.d(TAG,mResponse.getJSONArray("results").length()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }
}
