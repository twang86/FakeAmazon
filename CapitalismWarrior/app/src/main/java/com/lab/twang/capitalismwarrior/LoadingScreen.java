package com.lab.twang.capitalismwarrior;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lab.twang.capitalismwarrior.DatabaseStuff.DBAssetHelper;
import com.lab.twang.capitalismwarrior.DatabaseStuff.DBHelper;

public class LoadingScreen extends AppCompatActivity {

    private AsyncTask<Void, Void, Void> mLoadTask;
    private TextView mTapToStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        //loading database and setting singletons
        DBAssetHelper dbSetup = new DBAssetHelper(this);
        dbSetup.getReadableDatabase();

        mTapToStart = (TextView) findViewById(R.id.loading_screen);
        mTapToStart.setVisibility(View.INVISIBLE);

        mLoadTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (DBHelper.getInstance(LoadingScreen.this).storeIsEmpty()){
                    DBHelper.getInstance(LoadingScreen.this).lootStore();
                    DBHelper.getInstance(LoadingScreen.this).stockStore();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mTapToStart.setVisibility(View.VISIBLE);
            }
        };

        mLoadTask.execute();



        mTapToStart.setOnClickListener(v -> {
            startActivity(new Intent(LoadingScreen.this, MainActivity.class));
            finish();
        });


    }
}
