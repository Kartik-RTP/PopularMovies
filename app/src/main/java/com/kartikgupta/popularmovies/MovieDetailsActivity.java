package com.kartikgupta.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    TextView mMovieTitleTextView;
    ImageView mMoviePosterImageView;
    TextView mOverviewTextView;
    TextView mUserRatingTextView;
    TextView mReleaseDateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMovieTitleTextView = (TextView) findViewById(R.id.movieTitleTextView);
        mMoviePosterImageView = (ImageView) findViewById(R.id.moviePosterImageView);
        mOverviewTextView = (TextView) findViewById(R.id.overviewTextView);
        mReleaseDateTextView = (TextView) findViewById(R.id.releaseDateTextView);
        mUserRatingTextView = (TextView) findViewById(R.id.userRatingTextView);




        String jsonObjectString = getIntent().getStringExtra("movieJSONObject");
        JSONObject movieDetailsJsonObject=null;
        try {
            movieDetailsJsonObject = new JSONObject(jsonObjectString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        if(movieDetailsJsonObject!=null){

            try {
                mMovieTitleTextView.setText(movieDetailsJsonObject.getString("original_title"));
                mOverviewTextView.setText(movieDetailsJsonObject.getString("overview"));
                mUserRatingTextView.setText(movieDetailsJsonObject.getString("vote_average"));
                mReleaseDateTextView.setText(movieDetailsJsonObject.getString("release_date"));

                String relativePath = movieDetailsJsonObject.getString("poster_path").substring(1);
                String  BASE_URL = "https://image.tmdb.org/t/p/w185/";

                Picasso.with(this)
                        .load(BASE_URL+relativePath)
                        .into(mMoviePosterImageView);



            } catch (JSONException e) {
                e.printStackTrace();
            }


          
        }else{
            Log.d(TAG,"movieDetailsJSONOBject created is null");
        }
        


    }
}
