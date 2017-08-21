package com.kartikgupta.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by kartik on 12/7/17.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>{


    private static final String BASE_URL = "https://image.tmdb.org/t/p/w185/";
    private static final String POSTER_PATH = "poster_path";
    private static final String TAG = MoviesRecyclerViewAdapter.class.getSimpleName();
    private JSONArray mMovieDetailsJsonArray;
    private LayoutInflater mLayoutInflater;
    private Context mContext;


    public MoviesRecyclerViewAdapter(JSONArray movieDetailsJsonArray , Context context){
        mMovieDetailsJsonArray = movieDetailsJsonArray;
        mLayoutInflater = LayoutInflater.from(context);
        mContext=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //TODO:load image using picasso

        //loading the images into the image view

        if(mMovieDetailsJsonArray!=null){

           String relativePath = null;
            try {
                relativePath = mMovieDetailsJsonArray.getJSONObject(position).getString("poster_path").substring(1);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.v(TAG,position+" index image to be loaded from :-\n"+BASE_URL+relativePath);

            Picasso.with(mContext)
                    .load(BASE_URL+relativePath)
                    .into(holder.moviePosterImageView);


        }else{
            Log.d(TAG,"jsonArray is null");
        }





    }


    @Override
    public int getItemCount() {
       if(mMovieDetailsJsonArray!=null){
           return mMovieDetailsJsonArray.length();
       }
       return 0; //for initially empty jsonArray handling

    }

    public void setMoviesData(JSONArray movieListResultsJsonArray) {

        mMovieDetailsJsonArray=movieListResultsJsonArray;
        notifyDataSetChanged();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {

        public ImageView moviePosterImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            moviePosterImageView = (ImageView) itemView.findViewById(R.id.moviePosterImageView);
        }

    }


}
