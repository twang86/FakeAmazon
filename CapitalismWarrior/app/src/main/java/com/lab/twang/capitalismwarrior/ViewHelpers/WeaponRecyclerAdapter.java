package com.lab.twang.capitalismwarrior.ViewHelpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.twang.capitalismwarrior.CheckoutSupport.CheckoutViewHolder;
import com.lab.twang.capitalismwarrior.JavaClasses.WeaponStoreItem;
import com.lab.twang.capitalismwarrior.R;
import com.lab.twang.capitalismwarrior.StoreViewSupport.StoreWeaponViewHolder;

import java.util.List;

/**
 * Created by twang on 4/13/17.
 */

public class WeaponRecyclerAdapter extends RecyclerView.Adapter {

    private List<WeaponStoreItem> mWeaponList;
    private int mDisplayType;
    private OnItemClicked mListener;
    public static final int STORE_DISPLAY = 123, SEARCH_DISPLAY = 456, CHECKOUT_DISPLAY = 789;


    public WeaponRecyclerAdapter(List<WeaponStoreItem> weaponList, int displayType, OnItemClicked listener) {
        mWeaponList = weaponList;
        mDisplayType = displayType;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            default:
                return new StoreWeaponViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_store_display, parent, false));
            case CHECKOUT_DISPLAY:
                return new CheckoutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_checkout_display, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final WeaponStoreItem currentWeapon = mWeaponList.get(position);
        View.OnClickListener viewClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClicked(mWeaponList.get(holder.getAdapterPosition()).getStoreId(), holder.getAdapterPosition(), v);
            }
        };
        if (holder instanceof  StoreWeaponViewHolder) {
            ((StoreWeaponViewHolder)holder).bindViewHolder(mWeaponList.get(position), mDisplayType);
            ((StoreWeaponViewHolder)holder).mRoot.setOnClickListener(viewClick);
        }

        if (holder instanceof CheckoutViewHolder){
            ((CheckoutViewHolder)holder).bindViewHolder(mWeaponList.get(position));
            ((CheckoutViewHolder)holder).mImage.setOnClickListener(viewClick);
            ((CheckoutViewHolder)holder).mDeleteCart.setOnClickListener(viewClick);
            ((CheckoutViewHolder)holder).mCartChange.setOnClickListener(viewClick);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDisplayType;
    }

    @Override
    public int getItemCount() {
        return mWeaponList.size();
    }

    //my methods
    public void giveNewList(List<WeaponStoreItem> newList){
        mWeaponList = newList;
    }

    public interface OnItemClicked{
        void onClicked(int id, int position, View v);
    }
}
