package com.lab.twang.capitalismwarrior.StoreViewSupport;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lab.twang.capitalismwarrior.DatabaseStuff.DBHelper;
import com.lab.twang.capitalismwarrior.JavaClasses.Review;
import com.lab.twang.capitalismwarrior.JavaClasses.WeaponStoreItem;
import com.lab.twang.capitalismwarrior.R;
import com.lab.twang.capitalismwarrior.ViewHelpers.ViewHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.lab.twang.capitalismwarrior.ViewHelpers.WeaponRecyclerAdapter.STORE_DISPLAY;

/**
 * Created by twang on 4/8/17.
 */

public class StoreWeaponViewHolder extends RecyclerView.ViewHolder {

    public TextView mName, mNumRating, mAmount, mPrice, mPriceDecimal;
    public ImageView mImage;
    public ImageView mRating[] = new ImageView[5];
    public View mRoot;

    public StoreWeaponViewHolder(View itemView) {
        super(itemView);
        mRoot = itemView;
        mName = (TextView) itemView.findViewById(R.id.weapon_name);
        mAmount = (TextView) itemView.findViewById(R.id.weapon_amount);
        mPrice = (TextView) itemView.findViewById(R.id.weapon_price);
        mPriceDecimal = (TextView) itemView.findViewById(R.id.weapon_price_decimal);
        mNumRating = (TextView) itemView.findViewById(R.id.weapon_number_of_ratings);
        mImage = (ImageView) itemView.findViewById(R.id.weapon_image);
        mRating[0] = (ImageView) itemView.findViewById(R.id.weapon_star01);
        mRating[1] = (ImageView) itemView.findViewById(R.id.weapon_star02);
        mRating[2] = (ImageView) itemView.findViewById(R.id.weapon_star03);
        mRating[3] = (ImageView) itemView.findViewById(R.id.weapon_star04);
        mRating[4] = (ImageView) itemView.findViewById(R.id.weapon_star05);
    }

    public void bindViewHolder(WeaponStoreItem weapon, int displayType){

        int totalRating = 0;
        int totalAmountReviews = 0;

        List<WeaponStoreItem> similarWeapons = DBHelper.getInstance(mRoot.getContext()).getSimilarStoreWeaponById(weapon.getStoreId());

        for (int i = 0; i < similarWeapons.size(); i++) {
            List<Review> currentReviews = DBHelper.getInstance(mRoot.getContext()).getReviewsByWeaponId(similarWeapons.get(i).getId());
            totalRating += ViewHelper.getAverageRatings(currentReviews);
            totalAmountReviews += currentReviews.size();
        }

        int averageRating = totalRating/similarWeapons.size();


        mName.setText(weapon.getBrand() + " ("+ DBHelper.getInstance(mName.getContext()).getNameFromElementId(weapon.getElement()) + ")");
        if (displayType == STORE_DISPLAY) {
            mName.setText(weapon.getBrand());
        }
        ViewHelper.setAmount(weapon.getAmount(), mAmount);
        ViewHelper.setStoreViewPrice(weapon.getPrice(), mPrice, mPriceDecimal);
        mNumRating.setText(String.valueOf(totalAmountReviews));
        ViewHelper.setRatings(averageRating, mRating, R.drawable.ic_star_on, R.drawable.ic_star_off);
        Picasso.with(mImage.getContext()).load(weapon.getPicture())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .into(mImage);
    }
}
