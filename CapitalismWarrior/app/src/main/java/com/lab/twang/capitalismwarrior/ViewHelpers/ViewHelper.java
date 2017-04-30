package com.lab.twang.capitalismwarrior.ViewHelpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lab.twang.capitalismwarrior.CheckoutActivity;
import com.lab.twang.capitalismwarrior.DatabaseStuff.DBHelper;
import com.lab.twang.capitalismwarrior.JavaClasses.Review;
import com.lab.twang.capitalismwarrior.MainActivity;
import com.lab.twang.capitalismwarrior.R;
import com.lab.twang.capitalismwarrior.SearchActivity;
import com.lab.twang.capitalismwarrior.Variables.VariableNames;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by twang on 4/12/17.
 */

public abstract class ViewHelper {

    public static void setAmount(int amount, TextView textView){
        String message;
        String color;
        if (amount<=0){
            message = "Out of stock";
            color = "#831f1f";
        } else if (amount<=5) {
            message = "Only " + amount + " left in stock, order soon!";
            color = "#831f1f";
        } else {
            message = amount + " in stock";
            color = "#20831f";
        }
        textView.setText(message);
        textView.setTextColor(Color.parseColor(color));

    }

    public static void setStoreViewPrice(double price, TextView priceText, TextView decimalText){
        String[] sPrice = String.valueOf(price).split("\\.");
        priceText.setText(sPrice[0]);
        decimalText.setText(sPrice[1]);
    }

    public static void setRatings(int rating, ImageView[] ratingPics, int ratingOn, int ratingOff){
        for (ImageView star:ratingPics
             ) {
            star.setImageResource(ratingOff);

        }
        if (rating > ratingPics.length){
            rating = ratingPics.length;
        }
        if (rating < 0){
            rating = 0;
        }
        for (int i = 0; i < rating; i++) {
            //mRating[i].setVisibility(View.INVISIBLE);
            ratingPics[i].setImageResource(ratingOn);
        }
    }



    public static void setElementFeature(String type, String element, TextView elementText){
        String weaponType = DBHelper.getInstance(elementText.getContext()).getNameFromWeaponTypeId(type);
        String weaponElement = DBHelper.getInstance(elementText.getContext()).getNameFromElementId(element).toLowerCase();
        elementText.setText(" - " + weaponType+ " infused with the essence of " + weaponElement);
    }

    public static void setDamageFeature(String damage, TextView damageText){
        damageText.setText(" - " + "Does a whopping " + damage + " points of damage");
    }

    public static void setDurability(String durability, TextView durabilityText){
        durabilityText.setText(" - " + "Quality construction. Good for " + durability + " uses.");
    }

    public static void setRarity(double rarity, TextView rarityText){
        String howRare;
        if (rarity >.9){
            howRare = "Fast shipping";
        } else if (rarity >.75){
            howRare = "Limited edition";
        } else if (rarity > .5){
            howRare = "Collectible";
        } else if (rarity > .25){
            howRare = "Ultra rare";
        } else {
            howRare = "Legendary item!";
        }
        rarityText.setText(" - " + howRare);
    }

    public static String formatMoney(double money){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(money);
        return moneyString;
    }

    public static void setupSimpleToolbar(View view){
        view.findViewById(R.id.toolbar_goto_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(v.getContext() instanceof CheckoutActivity)){
                    v.getContext().startActivity(new Intent(v.getContext(), CheckoutActivity.class));
                }
            }
        });
        view.findViewById(R.id.toolbar_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(v.getContext() instanceof SearchActivity)){
                    v.getContext().startActivity(new Intent(v.getContext(), SearchActivity.class));
                }
            }
        });
        view.findViewById(R.id.toolbar_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(v.getContext() instanceof MainActivity)){
                    v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            }
        });
    }

    public static int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int getAverageRatings(List<Review> reviews){
        if (reviews.size() == 0){
            return 0;
        }
        int totalRating=0;
        for (int i = 0; i < reviews.size(); i++) {
            totalRating += reviews.get(i).getRating();
        }
        return totalRating/reviews.size();
    }

}
