package com.lab.twang.capitalismwarrior.JavaClasses;

/**
 * Created by twang on 4/8/17.
 */

public class Weapon {

    private int mId, mDamage, mDurability;
    private String mType, mBrand, mDescription, mElement, mElementIcon, mIcon, mPicture;
    private double mPrice;
    private double mRarity;


    public Weapon(Weapon weapon){
        mId = weapon.getId();
        mDamage = weapon.getDamage();
        mDurability = weapon.getDurability();
        mType = weapon.getType();
        mBrand = weapon.getBrand();
        mDescription = weapon.getDescription();
        mElement = weapon.getElement();
        mElementIcon = weapon.getElementIcon();
        mIcon = weapon.getIcon();
        mPicture = weapon.getPicture();
        mPrice = weapon.getPrice();
        mRarity = weapon.getRarity();

    }

    public Weapon(int id,
                  String type,
                  String brand,
                  String description,
                  String element,
                  int damage,
                  int durability,
                  double price,
                  String elementIcon,
                  String icon,
                  String picture,
                  double rarity) {
        mId = id;
        mDamage = damage;
        mDurability = durability;
        mType = type;
        mBrand = brand;
        mDescription = description;
        mElement = element;
        mElementIcon = elementIcon;
        mIcon = icon;
        mPicture = picture;
        mPrice = price;
        mRarity = rarity;
    }

    public int getDurability() {
        return mDurability;
    }

    public void setDurability(int durability) {
        if (durability<0) durability=0;
        mDurability = durability;
    }

    public int getId() {
        return mId;
    }

    public int getDamage() {
        return mDamage;
    }

    public String getType() {
        return mType;
    }

    public String getBrand() {
        return mBrand;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getElement() {
        return mElement;
    }

    public String getIcon() {
        return mIcon;
    }

    public double getPrice() {
        return mPrice;
    }

    public String getPicture() {
        return mPicture;
    }

    public String getElementIcon() {
        return mElementIcon;
    }

    public double getRarity() {
        return mRarity;
    }

}
