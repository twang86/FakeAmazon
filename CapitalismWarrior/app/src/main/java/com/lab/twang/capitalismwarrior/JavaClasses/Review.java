package com.lab.twang.capitalismwarrior.JavaClasses;

/**
 * Created by twang on 4/13/17.
 */

public class Review {
    private int mId;
    private int mWeaponId;
    private int mRating;
    private String mTitle;
    private String mComment;

    public Review(int id, int weaponId, int rating, String title, String comment) {
        mId = id;
        mWeaponId = weaponId;
        mRating = rating;
        mTitle = title;
        mComment = comment;
    }

    public int getId() {
        return mId;
    }

    public int getWeaponId() {
        return mWeaponId;
    }

    public int getRating() {
        return mRating;
    }

    public String getComment() {
        return mComment;
    }

    public String getTitle() {
        return mTitle;
    }
}
