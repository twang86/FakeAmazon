package com.lab.twang.capitalismwarrior;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.lab.twang.capitalismwarrior.DatabaseStuff.DBHelper;
import com.lab.twang.capitalismwarrior.JavaClasses.Review;
import com.lab.twang.capitalismwarrior.JavaClasses.WeaponStoreItem;
import com.lab.twang.capitalismwarrior.ReviewSupport.ReviewRecyclerAdapter;
import com.lab.twang.capitalismwarrior.ViewHelpers.ViewHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemViewActivity extends AppCompatActivity {
    public static final String STORE_ID = "hey bob can you tell me the weapon id please?";
    private RadioGroup mSelectorHolder;
    private int mStoreId;
    private List<WeaponStoreItem> mCurrentWeapons;
    private WeaponStoreItem mSelectedWeapon;
    private List<RadioButton> mRadioButtons;
    private ReviewRecyclerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        //getting item information
        mStoreId = getIntent().getIntExtra(STORE_ID, -1);

        if (mStoreId == -1){
            Toast.makeText(this, "Weapon does not exist! What happened??", Toast.LENGTH_SHORT).show();
            finish();
        }

        ViewHelper.setupSimpleToolbar(findViewById(android.R.id.content));

        mSelectorHolder = (RadioGroup) findViewById(R.id.weapon_element_container);



    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.weapon_button_write_review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemViewActivity.this, ReviewActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(STORE_ID, mStoreId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.weapon_comment_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ReviewRecyclerAdapter(new ArrayList<Review>());
        recyclerView.setAdapter(mAdapter);

        mSelectorHolder.removeAllViews();

//        //Resetting weapon numbers
//        mCurrentWeapons = DBHelper.getInstance(this).getSimilarStoreWeaponById(mStoreId);
//
//        //sets the initial weapons displayed
//        mSelectedWeapon = DBHelper.getInstance(this).getWeaponItemByStoreId(mStoreId);
//
//        setElementsByWeapon(mSelectedWeapon);
//
//        bindRadioButtons(mCurrentWeapons);

        GetSelectedWeapons getWeapon = new GetSelectedWeapons();
        getWeapon.execute();


    }

    public void bindRadioButtons(List<WeaponStoreItem> currentWeapons){
        //binds radio buttons;
        if (currentWeapons.size()>1) {
            for (int i = 0; i < currentWeapons.size(); i++) {
                final WeaponStoreItem weapon = currentWeapons.get(i);
                //RadioButton elementSelector = (RadioButton) getLayoutInflater().inflate(R.layout.radio_button_element_selectors, null);
                RadioButton elementSelector = new RadioButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,0,ViewHelper.dpToPx(10, this),0);
                elementSelector.setLayoutParams(params);
                elementSelector.setButtonDrawable(null);
                elementSelector.setPadding(25,25,25,25);
                elementSelector.setBackgroundResource(R.drawable.box_fill_white);
                elementSelector.setText(DBHelper.getInstance(this).getNameFromElementId(weapon.getElement()));
                elementSelector.setId(weapon.getStoreId());
                elementSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            buttonView.setBackgroundResource(R.drawable.orange_box);
                            mStoreId = weapon.getStoreId();
                            //mSelectedWeapon = weapon;
                            setElementsByWeapon(weapon);
                        }else{
                            buttonView.setBackgroundResource(R.drawable.box_fill_white);
                        }
                    }
                });
                mSelectorHolder.addView(elementSelector);
            }
            mSelectorHolder.clearCheck();
            mSelectorHolder.check(mSelectedWeapon.getStoreId());
        } else {
            mSelectorHolder.setVisibility(View.GONE);
        }
    }

    public void setElementsByWeapon(WeaponStoreItem weapon){
        //i'm not doing this one because this is extra
        mSelectedWeapon = weapon;
        List<Review> reviews = DBHelper.getInstance(this).getReviewsByWeaponId(weapon.getId());

        ((TextView) findViewById(R.id.weapon_name)).setText(weapon.getBrand());
        ((TextView) findViewById(R.id.weapon_price)).setText(ViewHelper.formatMoney(weapon.getPrice()));
        ((TextView) findViewById(R.id.weapon_element)).setText(DBHelper.getInstance(this).getNameFromElementId(weapon.getElement()));
        ViewHelper.setAmount(weapon.getAmount(), (TextView) findViewById(R.id.weapon_amount));
        ((TextView) findViewById(R.id.weapon_number_of_ratings)).setText(String.valueOf(reviews.size()));
        ImageView ratings[] = {
                (ImageView) findViewById(R.id.weapon_star01),
                (ImageView) findViewById(R.id.weapon_star02),
                (ImageView) findViewById(R.id.weapon_star03),
                (ImageView) findViewById(R.id.weapon_star04),
                (ImageView) findViewById(R.id.weapon_star05),
        };
        ViewHelper.setRatings(ViewHelper.getAverageRatings(reviews), ratings, R.drawable.ic_star_on, R.drawable.ic_star_off);

        ((TextView) findViewById(R.id.weapon_description)).setText(weapon.getDescription());
        ViewHelper.setElementFeature(weapon.getType(), weapon.getElement(), (TextView) findViewById(R.id.weapon_element_feature));
        ViewHelper.setDamageFeature(String.valueOf(weapon.getDamage()), (TextView) findViewById(R.id.weapon_damage));
        ViewHelper.setDurability(String.valueOf(weapon.getDurability()), (TextView) findViewById(R.id.weapon_durability));
        ViewHelper.setRarity(weapon.getRarity(), (TextView) findViewById(R.id.weapon_rarity));

        System.out.println(weapon.getPicture());
        Picasso.with(this).load(weapon.getPicture())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .into((ImageView)findViewById(R.id.weapon_image));

        mAdapter.giveNewList(reviews);

        if (weapon.getAmount()>0) {
            findViewById(R.id.weapon_button_add_cart).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.weapon_button_add_cart).setVisibility(View.GONE);
        }

        //adding button functions
        findViewById(R.id.weapon_button_add_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.getInstance(v.getContext()).addToCartById(mSelectedWeapon.getStoreId(), 1);
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.root_layout), "Item added to cart!", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DBHelper.getInstance(view.getContext()).addToCartById(mSelectedWeapon.getStoreId(), -1);
                            }
                        });

                snackbar.show();

            }
        });
    }
    private class GetSelectedWeapons extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            mSelectedWeapon = DBHelper.getInstance(ItemViewActivity.this).getWeaponItemByStoreId(mStoreId);
            mCurrentWeapons = DBHelper.getInstance(ItemViewActivity.this).getSimilarStoreWeaponById(mStoreId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setElementsByWeapon(mSelectedWeapon);
            bindRadioButtons(mCurrentWeapons);
        }
    }

}
