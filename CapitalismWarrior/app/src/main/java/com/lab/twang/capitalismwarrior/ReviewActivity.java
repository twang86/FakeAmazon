package com.lab.twang.capitalismwarrior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.twang.capitalismwarrior.DatabaseStuff.DBHelper;
import com.lab.twang.capitalismwarrior.JavaClasses.WeaponStoreItem;
import com.lab.twang.capitalismwarrior.ViewHelpers.ViewHelper;
import com.squareup.picasso.Picasso;

public class ReviewActivity extends AppCompatActivity {
    private EditText mEditTitle, mEditComment;
    private RatingBar mRatingBar;
    private WeaponStoreItem selectedWeapon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //setting toolbar functions
        ViewHelper.setupSimpleToolbar(findViewById(android.R.id.content));

        int storeId = getIntent().getIntExtra(ItemViewActivity.STORE_ID, -1);

        mRatingBar = (RatingBar) findViewById(R.id.review_rating_edit);
        mRatingBar.setRating(0);
        mEditTitle = (EditText) findViewById(R.id.review_title_edit);
        mEditComment = (EditText) findViewById(R.id.review_comment_edit);

        selectedWeapon = DBHelper.getInstance(this).getWeaponItemByStoreId(storeId);

        Picasso.with(this).load(selectedWeapon.getPicture())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into((ImageView) findViewById(R.id.weapon_image));

        ((TextView)findViewById(R.id.review_weapon_name)).setText(selectedWeapon.getBrand() + " ("+ DBHelper.getInstance(this).getNameFromElementId(selectedWeapon.getElement()) + ")");

        findViewById(R.id.review_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTitle.getText().toString().isEmpty()){
                    mEditTitle.setError("Please enter your name!");
                    return;
                }
                Toast.makeText(ReviewActivity.this, "Review Added!", Toast.LENGTH_SHORT).show();
                int rating = (int) mRatingBar.getRating();
                String title = mEditTitle.getText().toString();
                String comment = mEditComment.getText().toString();

                System.out.println(rating + " " + title + " " + comment);

                DBHelper.getInstance(v.getContext()).addReview(selectedWeapon.getId(), rating, title, comment);

                mEditTitle.getText().clear();
                mEditComment.getText().clear();
                mRatingBar.setRating(0);
                finish();
            }
        });
    }
}
