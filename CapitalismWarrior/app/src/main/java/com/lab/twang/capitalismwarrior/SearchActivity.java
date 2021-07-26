package com.lab.twang.capitalismwarrior;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.lab.twang.capitalismwarrior.DatabaseStuff.DBHelper;
import com.lab.twang.capitalismwarrior.JavaClasses.WeaponStoreItem;
import com.lab.twang.capitalismwarrior.ViewHelpers.ViewHelper;
import com.lab.twang.capitalismwarrior.ViewHelpers.WeaponRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements WeaponRecyclerAdapter.OnItemClicked{

    GetQuery mGetQuery;
    WeaponRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ViewHelper.setupSimpleToolbar(findViewById(android.R.id.content));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new WeaponRecyclerAdapter(new ArrayList<WeaponStoreItem>(), WeaponRecyclerAdapter.SEARCH_DISPLAY, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);


        SearchView searchInput = (SearchView) findViewById(R.id.search_input);
        searchInput.setIconifiedByDefault(false);
        searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                mAdapter.giveNewList(DBHelper.getInstance(SearchActivity.this).searchInventory(newText));
//                mAdapter.notifyDataSetChanged();
                if (mGetQuery != null && mGetQuery.getStatus() == AsyncTask.Status.RUNNING){
                    return false;
                } else {
                    mGetQuery = new GetQuery();
                    mGetQuery.execute(newText);
                }

                return false;
            }
        });
    }

    @Override
    public void onClicked(int id, int position, View v) {
        Intent intent = new Intent(v.getContext(), ItemViewActivity.class);
        intent.putExtra(ItemViewActivity.STORE_ID, id);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        v.getContext().startActivity(intent);
        finish();
    }

    public class GetQuery extends AsyncTask<String, Void, List<WeaponStoreItem>>{

        @Override
        protected List<WeaponStoreItem> doInBackground(String... params) {
            return DBHelper.getInstance(SearchActivity.this).searchInventory(params[0]);
        }

        @Override
        protected void onPostExecute(List<WeaponStoreItem> weaponStoreItems) {
            super.onPostExecute(weaponStoreItems);
            mAdapter.giveNewList(weaponStoreItems);
            mAdapter.notifyDataSetChanged();
        }
    }
}
