package com.lab.twang.capitalismwarrior.ReviewSupport;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lab.twang.capitalismwarrior.JavaClasses.Review;
import com.lab.twang.capitalismwarrior.R;
import com.lab.twang.capitalismwarrior.ViewHelpers.ViewHelper;

/**
 * Created by twang on 4/13/17.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    public TextView mTitle, mComment;
    public ImageView mRating[] = new ImageView[5];


    public ReviewViewHolder(View itemView) {
        super(itemView);
        mRating[0] = (ImageView) itemView.findViewById(R.id.weapon_star01);
        mRating[1] = (ImageView) itemView.findViewById(R.id.weapon_star02);
        mRating[2] = (ImageView) itemView.findViewById(R.id.weapon_star03);
        mRating[3] = (ImageView) itemView.findViewById(R.id.weapon_star04);
        mRating[4] = (ImageView) itemView.findViewById(R.id.weapon_star05);
        mTitle = (TextView) itemView.findViewById(R.id.review_title);
        mComment = (TextView) itemView.findViewById(R.id.review_comment);
    }

    public void bindViewHolder(Review review){
        mTitle.setText(review.getTitle());
        ViewHelper.setRatings(review.getRating(), mRating, R.drawable.ic_star_on, R.drawable.ic_star_off);
        if (review.getComment() == null || review.getComment().equals("")){
            mComment.setVisibility(View.GONE);
        } else {
            mComment.setText(review.getComment());
        }
    }
}
