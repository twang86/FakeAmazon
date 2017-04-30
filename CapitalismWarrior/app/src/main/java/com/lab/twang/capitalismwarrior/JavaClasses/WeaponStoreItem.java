package com.lab.twang.capitalismwarrior.JavaClasses;

/**
 * Created by twang on 4/10/17.
 */

public class WeaponStoreItem extends Weapon {
    private int mStoreId, mAmount, mCart;

    public WeaponStoreItem(Weapon weapon, int storeId, int amount, int cart) {
        super(weapon);
        mStoreId = storeId;
        mAmount = amount;
        mCart = cart;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        if (amount<0) amount=0;
        mAmount = amount;
    }

    public int getCart() {
        return mCart;
    }

    public void setCart(int cart) {
        if (cart < 0){
            cart = 0;
        }
        mCart = cart;
    }

    public int getStoreId() {
        return mStoreId;
    }
}
