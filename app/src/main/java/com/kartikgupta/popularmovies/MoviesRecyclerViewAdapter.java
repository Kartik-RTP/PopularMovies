package com.kartikgupta.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;

/**
 * Created by kartik on 12/7/17.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>{


    private JSONArray mMovieDetailsJsonArray;
    private LayoutInflater mLayoutInflater;

    public MoviesRecyclerViewAdapter(JSONArray movieDetailsJsonArray , Context context){
        mMovieDetailsJsonArray = movieDetailsJsonArray;
        mLayoutInflater = LayoutInflater.from(context);
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


    }


    @Override
    public int getItemCount() {
        return mMovieDetailsJsonArray.length();//TODO : check if correct
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {

        public ImageView moviePosterImageView;

        public ViewHolder(View itemView) {
            super(itemView);
     //       myTextView = (TextView) itemView.findViewById(R.id.info_text);
            moviePosterImageView = (ImageView) itemView.findViewById(R.id.moviePosterImageView);
        }

    }


}
