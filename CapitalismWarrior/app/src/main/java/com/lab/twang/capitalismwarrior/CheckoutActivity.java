package com.lab.twang.capitalismwarrior;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.twang.capitalismwarrior.DatabaseStuff.DBHelper;
import com.lab.twang.capitalismwarrior.JavaClasses.WeaponStoreItem;
import com.lab.twang.capitalismwarrior.Variables.VariableNames;
import com.lab.twang.capitalismwarrior.ViewHelpers.ViewHelper;
import com.lab.twang.capitalismwarrior.ViewHelpers.WeaponRecyclerAdapter;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements WeaponRecyclerAdapter.OnItemClicked{
    private WeaponRecyclerAdapter mAdapter;
    private List<WeaponStoreItem> mCartWeapons;
    private double mTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //setting toolbar functions
        ViewHelper.setupSimpleToolbar(findViewById(android.R.id.content));

    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.checkout_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mCartWeapons = DBHelper.getInstance(this).getCartWeapons();

        mAdapter = new WeaponRecyclerAdapter(mCartWeapons, WeaponRecyclerAdapter.CHECKOUT_DISPLAY, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);

        setTextViews();

        findViewById(R.id.checkout_all_items_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCartWeapons.size()<=0){
                    Toast.makeText(CheckoutActivity.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < mCartWeapons.size(); i++) {
                    WeaponStoreItem cartItem = mCartWeapons.get(i);
                    int result = DBHelper.getInstance(v.getContext()).checkOutOKByStoreId(cartItem.getStoreId());
                    if (result == DBHelper.getInstance(v.getContext()).ITEM_NOT_ENOUGH){
                        Toast.makeText(CheckoutActivity.this,
                                "There aren't enough "+ cartItem.getBrand() + " (" + DBHelper.getInstance(v.getContext()).getNameFromElementId(cartItem.getElement()) + ") in stock!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                for (WeaponStoreItem cartItem: mCartWeapons) {
                    DBHelper.getInstance(v.getContext()).checkOutById(cartItem.getStoreId());
                }
                Toast.makeText(CheckoutActivity.this, "Checkout successful!", Toast.LENGTH_SHORT).show();
                int size = mCartWeapons.size();
                mCartWeapons.clear();
                mAdapter.notifyItemRangeRemoved(0, size);
                setTextViews();
            }
        });
    }

    public void setTextViews(){
        ((TextView)findViewById(R.id.checkout_cart_amount)).setText("Cart subtotal (" + mCartWeapons.size() + " items): ");
        mTotalPrice =0;
        for (int i = 0; i <mCartWeapons.size(); i++) {
            mTotalPrice += (mCartWeapons.get(i).getPrice()) * mCartWeapons.get(i).getCart();
        }
        ((TextView)findViewById(R.id.checkout_total_price)).setText(ViewHelper.formatMoney(mTotalPrice));
    }

    public void removeItem(int position){
        mCartWeapons.remove(position);
        mAdapter.notifyItemRemoved(position);
        setTextViews();
    }

    @Override
    public void onClicked(final int id, final int position, final View v) {
        switch(v.getId()){
            case R.id.weapon_image:
                Intent intent = new Intent(v.getContext(), ItemViewActivity.class);
                intent.putExtra(ItemViewActivity.STORE_ID, id);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.weapon_cart_delete:
                DBHelper.getInstance(v.getContext()).addToCartById(id, Integer.MIN_VALUE);
                removeItem(position);
                break;
            case R.id.weapon_cart_change:
                //Toast.makeText(this, "change!", Toast.LENGTH_SHORT).show();
                String nums[] = {"1", "2", "3","4","5","6","7","8","9","10"};

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setTitle("Quantity")
                        .setItems(nums, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelper.getInstance(CheckoutActivity.this).updateCartById(id, which+1);
                                mCartWeapons.get(position).setCart(which+1);
                                ((TextView) v.findViewById(R.id.weapon_cart_amount)).setText(String.valueOf(which+1));
                                setTextViews();
                            }
                        }).setCancelable(true);
                AlertDialog setQuantity = builder.create();
                setQuantity.show();
                setQuantity.getWindow().setLayout(ViewHelper.dpToPx(200, this), LinearLayout.LayoutParams.MATCH_PARENT);
                break;
        }
    }
}
