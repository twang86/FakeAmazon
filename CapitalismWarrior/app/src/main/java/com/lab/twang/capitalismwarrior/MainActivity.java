package com.lab.twang.capitalismwarrior;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.lab.twang.capitalismwarrior.DatabaseStuff.DBHelper;
import com.lab.twang.capitalismwarrior.JavaClasses.WeaponStoreItem;
import com.lab.twang.capitalismwarrior.StoreViewSupport.StoreWeaponViewHolder;
import com.lab.twang.capitalismwarrior.ViewHelpers.ViewHelper;
import com.lab.twang.capitalismwarrior.ViewHelpers.WeaponRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WeaponRecyclerAdapter.OnItemClicked{
    private WeaponRecyclerAdapter mAdapter;
    private LoadListTask mGetListTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //setting toolbar functions
        ViewHelper.setupSimpleToolbar(findViewById(android.R.id.content));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new WeaponRecyclerAdapter(new ArrayList<WeaponStoreItem>(), WeaponRecyclerAdapter.STORE_DISPLAY, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);

        //mGetListTask.execute();

        findViewById(R.id.button_restock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGetListTask != null && mGetListTask.getStatus() == AsyncTask.Status.RUNNING){
                    Toast.makeText(MainActivity.this, "Already loading...", Toast.LENGTH_SHORT).show();
                } else {
                    mGetListTask = new LoadListTask();
                    mGetListTask.execute();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGetListTask = new LoadListTask();
        mGetListTask.execute();

    }

    @Override
    public void onClicked(int id, int position, View v) {
        Intent intent = new Intent(v.getContext(), ItemViewActivity.class);
        intent.putExtra(ItemViewActivity.STORE_ID, id);
        v.getContext().startActivity(intent);
    }



    private class LoadListTask extends AsyncTask<Void, Void, List<WeaponStoreItem>>{

        @Override
        protected List<WeaponStoreItem> doInBackground(Void... params) {
            DBHelper.getInstance(MainActivity.this).lootStore();
            DBHelper.getInstance(MainActivity.this).stockStore();
            return DBHelper.getInstance(MainActivity.this).getStoreInventory();
        }

        @Override
        protected void onPostExecute(List<WeaponStoreItem> weaponStoreItems) {
            super.onPostExecute(weaponStoreItems);
            mAdapter.giveNewList(weaponStoreItems);
            mAdapter.notifyDataSetChanged();
        }
    }
}
