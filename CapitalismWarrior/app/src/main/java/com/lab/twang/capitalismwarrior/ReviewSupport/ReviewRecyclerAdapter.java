package com.lab.twang.capitalismwarrior.ReviewSupport;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.lab.twang.capitalismwarrior.JavaClasses.Review;
import com.lab.twang.capitalismwarrior.R;

import java.util.List;

/**
 * Created by twang on 4/14/17.
 */

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private List<Review> mReviews;

    public ReviewRecyclerAdapter(List<Review> reviews){
        mReviews = reviews;
    }


    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_review, parent, false));

    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bindViewHolder(mReviews.get(position));

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public void giveNewList(List<Review> reviews){
        mReviews = reviews;
        notifyDataSetChanged();
    }
}
