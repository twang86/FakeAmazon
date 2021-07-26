package com.lab.twang.capitalismwarrior.CheckoutSupport;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lab.twang.capitalismwarrior.DatabaseStuff.DBHelper;
import com.lab.twang.capitalismwarrior.JavaClasses.WeaponStoreItem;
import com.lab.twang.capitalismwarrior.R;
import com.lab.twang.capitalismwarrior.ViewHelpers.ViewHelper;
import com.squareup.picasso.Picasso;

/**
 * Created by twang on 4/12/17.
 */

public class CheckoutViewHolder extends RecyclerView.ViewHolder {
    public TextView mName, mAmount, mPrice, mPriceDecimal, mCartAmount, mDeleteCart;
    public ImageView mImage;
    public View mRoot;
    public RelativeLayout mCartChange;

    public CheckoutViewHolder(View itemView) {
        super(itemView);
        mRoot = itemView;
        mName = (TextView) itemView.findViewById(R.id.weapon_name);
        mAmount = (TextView) itemView.findViewById(R.id.weapon_amount);
        mPrice = (TextView) itemView.findViewById(R.id.weapon_price);
        mPriceDecimal = (TextView) itemView.findViewById(R.id.weapon_price_decimal);
        mImage = (ImageView) itemView.findViewById(R.id.weapon_image);
        mCartAmount = (TextView) itemView.findViewById(R.id.weapon_cart_amount);
        mDeleteCart =(TextView) itemView.findViewById(R.id.weapon_cart_delete);
        mCartChange = (RelativeLayout) itemView.findViewById(R.id.weapon_cart_change);
    }

    public void bindViewHolder(WeaponStoreItem weapon){
        mName.setText(weapon.getBrand() + " ("+ DBHelper.getInstance(mName.getContext()).getNameFromElementId(weapon.getElement()) + ")");
        ViewHelper.setAmount(weapon.getAmount(), mAmount);
        ViewHelper.setStoreViewPrice(weapon.getPrice(), mPrice, mPriceDecimal);
        mCartAmount.setText(String.valueOf(weapon.getCart()));
        Picasso.with(mImage.getContext()).load(weapon.getPicture())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .into(mImage);
    }
}
