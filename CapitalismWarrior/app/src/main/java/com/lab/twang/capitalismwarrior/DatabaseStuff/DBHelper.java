package com.lab.twang.capitalismwarrior.DatabaseStuff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.lab.twang.capitalismwarrior.JavaClasses.Review;
import com.lab.twang.capitalismwarrior.JavaClasses.Weapon;
import com.lab.twang.capitalismwarrior.JavaClasses.WeaponStoreItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twang on 4/8/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    // --------------------------------------------------------------------------------
    // Constants
    // --------------------------------------------------------------------------------

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ITEMS.db";

    public static abstract class Store {
        public static final String TABLE_NAME = "store",
                COLUMN_ID = "id",
                COLUMN_WEAPON_ID = "store_weapon_id",
                COLUMN_AMOUNT = "store_amount",
                COLUMN_CART_AMOUNT = "store_cart_mount";
    }

    private static final String CREATE_STORE_TABLE = "CREATE TABLE " +
            Store.TABLE_NAME + " (" +
            Store.COLUMN_ID + " INTEGER PRIMARY KEY," +
            Store.COLUMN_WEAPON_ID + " INTEGER NOT NULL," +
            Store.COLUMN_AMOUNT + " INTEGER NOT NULL," +
            Store.COLUMN_CART_AMOUNT + " INTEGER NOT NULL" +
            ")";

    private static final String DELETE_STORE_TABLE = "DROP TABLE IF EXISTS " +
            Store.TABLE_NAME;

    public static abstract class WeaponsTable {
        public static final String TABLE_NAME = "weapons",
                COLUMN_ID = "id",
                COLUMN_TYPE_ID = "weapon_type_id",
                COLUMN_BRAND = "weapon_brand",
                COLUMN_DESCRIPTION = "weapon_description",
                COLUMN_ELEMENT_ID = "weapon_element_id",
                COLUMN_DAMAGE = "weapon_damage",
                COLUMN_DURABILITY = "weapon_durability",
                COLUMN_PRICE = "weapon_price",
                COLUMN_ICON = "weapon_icon",
                COLUMN_PICTURE = "weapon_picture",
                COLUMN_RARITY = "weapon_rarity";
    }

    private static final String CREATE_WEAPONS_TABLE = "CREATE TABLE " +
            WeaponsTable.TABLE_NAME + " (" +
            WeaponsTable.COLUMN_ID + " INTEGER PRIMARY KEY," +
            WeaponsTable.COLUMN_TYPE_ID + " TEXT NOT NULL," +
            WeaponsTable.COLUMN_BRAND + " TEXT," +
            WeaponsTable.COLUMN_DESCRIPTION + " TEXT," +
            WeaponsTable.COLUMN_ELEMENT_ID + " TEXT," +
            WeaponsTable.COLUMN_DAMAGE + " INTEGER," +
            WeaponsTable.COLUMN_DURABILITY + " INTEGER," +
            WeaponsTable.COLUMN_PRICE + " REAL," +
            WeaponsTable.COLUMN_ICON + " TEXT, " +
            WeaponsTable.COLUMN_PICTURE + " TEXT, " +
            WeaponsTable.COLUMN_RARITY + " TEXT" +
            ")";

    private static final String DELETE_WEAPONS_TABLE = "DROP TABLE IF EXISTS " +
            WeaponsTable.TABLE_NAME;


    public static abstract class WeaponTypesTable{
        public static final String TABLE_NAME = "weapon_types",
                COLUMN_ID = "id",
                COLUMN_NAME = "weapon_type_name",
                COLUMN_DAMAGE = "weapon_type_damage",
                COLUMN_DURABILITY = "weapon_type_durability",
                COLUMN_PRICE = "weapon_type_price",
                COLUMN_ICON = "weapon_type_icon",
                COLUMN_PICTURE = "weapon_type_picture",
                COLUMN_RARITY = "weapon_type_rarity";
    }

    private static final String CREATE_WEAPON_TYPES_TABLE = "CREATE TABLE " +
            WeaponTypesTable.TABLE_NAME + " (" +
            WeaponTypesTable.COLUMN_ID + " TEXT PRIMARY KEY," +
            WeaponTypesTable.COLUMN_NAME + " TEXT NOT NULL," +
            WeaponTypesTable.COLUMN_DURABILITY + " INTEGER NOT NULL," +
            WeaponTypesTable.COLUMN_DAMAGE + " INTEGER NOT NULL," +
            WeaponTypesTable.COLUMN_PRICE + " REAL NOT NULL," +
            WeaponTypesTable.COLUMN_ICON + " TEXT NOT NULL," +
            WeaponTypesTable.COLUMN_PICTURE + " TEXT NOT NULL," +
            WeaponTypesTable.COLUMN_RARITY + " TEXT NOT NULL" +
            ")";

    private static final String DELETE_WEAPON_TYPES_TABLE = "DROP TABLE IF EXISTS " +
            WeaponTypesTable.TABLE_NAME;


    public static abstract class ElementsTable{
        public static final String TABLE_NAME = "elements",
                COLUMN_ID = "id",
                COLUMN_NAME = "element_name",
                COLUMN_PRICE_MULTIPLIER = "element_price_multiplier",
                COLUMN_ICON = "element_icon";
    }

    private static final String CREATE_ELEMENTS_TABLE = "CREATE TABLE " +
            ElementsTable.TABLE_NAME + " (" +
            ElementsTable.COLUMN_ID + " TEXT PRIMARY KEY," +
            ElementsTable.COLUMN_NAME + " TEXT NOT NULL," +
            ElementsTable.COLUMN_PRICE_MULTIPLIER + " REAL NOT NULL," +
            ElementsTable.COLUMN_ICON + " TEXT NOT NULL" +
            ")";

    private static final String DELETE_ELEMENTS_TABLE = "DROP TABLE IF EXISTS " +
            ElementsTable.TABLE_NAME;

    public static abstract class ReviewsTable{
        public static final String TABLE_NAME = "reviews",
                COLUMN_ID = "id",
                COLUMN_WEAPON_ID = "review_weapon_id",
                COLUMN_RATING = "review_rating",
                COLUMN_TITLE = "review_title",
                COLUMN_COMMENT = "review_comment";
    }

    private static final String CREATE_REVIEW_TABLE = "CREATE TABLE " +
            ReviewsTable.TABLE_NAME + " (" +
            ReviewsTable.COLUMN_ID + " INTEGER PRIMARY KEY," +
            ReviewsTable.COLUMN_WEAPON_ID + " INTEGER NOT NULL," +
            ReviewsTable.COLUMN_RATING + " INTEGER NOT NULL," +
            ReviewsTable.COLUMN_TITLE + " TEXT NOT NULL" +
            ReviewsTable.COLUMN_COMMENT + " TEXT";

    private static final String DELETE_REVIEWS_TABLE = "DROP TABLE IF EXISTS " +
            ReviewsTable.TABLE_NAME;

    private static final String JOIN_WEAPON_TYPE_ELEMENT = WeaponsTable.TABLE_NAME +
            " JOIN " + WeaponTypesTable.TABLE_NAME + " ON " +
            WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_TYPE_ID +
            " = " +
            WeaponTypesTable.TABLE_NAME + "." + WeaponTypesTable.COLUMN_ID +
            " JOIN " + ElementsTable.TABLE_NAME + " ON " +
            WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_ELEMENT_ID +
            " = " +
            ElementsTable.TABLE_NAME + "." + ElementsTable.COLUMN_ID;

    private static final String JOIN_STORE_WEAPON = Store.TABLE_NAME +
            " JOIN " + WeaponsTable.TABLE_NAME + " ON " +
            Store.TABLE_NAME + "." + Store.COLUMN_WEAPON_ID +
            " = " +
            WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_ID;

    private static final String JOIN_WEAPON_REVIEW = WeaponsTable.TABLE_NAME +
            " JOIN " + ReviewsTable.TABLE_NAME + " ON " +
            WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_ID +
            " = " +
            ReviewsTable.TABLE_NAME + "." + ReviewsTable.COLUMN_WEAPON_ID;

    private static final String JOIN_ALL = WeaponsTable.TABLE_NAME +
            " JOIN " + WeaponTypesTable.TABLE_NAME + " ON " +
            WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_TYPE_ID +
            " = " +
            WeaponTypesTable.TABLE_NAME + "." + WeaponTypesTable.COLUMN_ID +
            " JOIN " + ElementsTable.TABLE_NAME + " ON " +
            WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_ELEMENT_ID +
            " = " +
            ElementsTable.TABLE_NAME + "." + ElementsTable.COLUMN_ID +
            " JOIN " + Store.TABLE_NAME + " ON " +
            Store.TABLE_NAME + "." + Store.COLUMN_WEAPON_ID +
            " = " +
            WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_ID;


    //Database messages

    public static final int ITEM_NOT_FOUND = 0;
    public static final int ITEM_NOT_ENOUGH = -1;
    public static final int ITEM_TOO_EXPENSIVE = -2;
    public static final int CHECKOUT_OK = 10;
    public static final int CHECKOUT_SUCCESSFUL = 100;

    // Singleton Stuff

    private static DBHelper sInstance;

    public static DBHelper getInstance(Context context){
        if (sInstance == null){
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Required methods

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STORE_TABLE);
        db.execSQL(CREATE_ELEMENTS_TABLE);
        db.execSQL(CREATE_WEAPON_TYPES_TABLE);
        db.execSQL(CREATE_WEAPONS_TABLE);
        db.execSQL(CREATE_REVIEW_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_STORE_TABLE);
        db.execSQL(DELETE_ELEMENTS_TABLE);
        db.execSQL(DELETE_WEAPON_TYPES_TABLE);
        db.execSQL(DELETE_WEAPONS_TABLE);
        db.execSQL(DELETE_REVIEWS_TABLE);
        onCreate(db);
    }

    //My Methods

    public void lootStore(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DELETE_STORE_TABLE);
        db.execSQL(CREATE_STORE_TABLE);
        db.close();
    }

    public boolean storeIsEmpty(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'store'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return false;
            }
            cursor.close();
        }
        return true;
    }

    public void stockStore(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor shippingTruck = db.query(WeaponsTable.TABLE_NAME,
                new String[]{WeaponsTable.COLUMN_ID},
                null, null, null, null, null);
        if (shippingTruck.moveToFirst()){
            do{
                int weaponID = shippingTruck.getInt(shippingTruck.getColumnIndex(WeaponsTable.COLUMN_ID));
                Weapon currentWeapon = getWeaponById(weaponID);
                int possibleStock = (int) (Math.random() * 10 * currentWeapon.getRarity()) + 1;
                if (Math.random()>currentWeapon.getRarity()) {
                    possibleStock = 0;
                }
                ContentValues values = new ContentValues();
                values.put(Store.COLUMN_WEAPON_ID, weaponID);
                values.put(Store.COLUMN_AMOUNT, possibleStock);
                values.put(Store.COLUMN_CART_AMOUNT, 0);
                db.insert(Store.TABLE_NAME, null, values);
            } while (shippingTruck.moveToNext());
        }
        db.close();
        shippingTruck.close();
    }

    public List<WeaponStoreItem> getStoreInventory(){
        List<WeaponStoreItem> inventory = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor bob = db.query(Store.TABLE_NAME, null, null, null, null, null, null);
        if (bob.moveToFirst()){
            do{
                WeaponStoreItem currentWeapon = getWeaponItemByStoreId(bob.getInt(bob.getColumnIndex(Store.COLUMN_ID)));
                inventory.add(currentWeapon);
            } while (bob.moveToNext());
        }
        bob.close();
        //this returns the full list of weapons from the store table

        //but we want to eliminate duplicates. If a weapon has the same name but is a different element, I want it in as one store item
        //user can choose what element he sees in the detail view. Similar to color or size of an item in amazon.

        for (int i = 0; i < inventory.size(); i++) {
            if (i==inventory.size()-1){
                break;
            }
            while(i<inventory.size()-1&&inventory.get(i).getType().equals(inventory.get(i+1).getType())&&inventory.get(i).getBrand().equals(inventory.get(i+1).getBrand())){
                WeaponStoreItem currentWeapon = inventory.get(i);
                WeaponStoreItem nextWeapon = inventory.get(i+1);
                currentWeapon.setAmount(currentWeapon.getAmount()+nextWeapon.getAmount());
                inventory.remove(i+1);
            }
        }
        return inventory;
    }

    public WeaponStoreItem getWeaponItemByStoreId(int storeId){
        WeaponStoreItem result=null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor bob = db.query(Store.TABLE_NAME, null,
                Store.COLUMN_ID + " =? ",
                new String[]{String.valueOf(storeId)},
                null, null, null);
        if (bob.moveToFirst()){
            result = new WeaponStoreItem(
                        getWeaponById(bob.getInt(bob.getColumnIndex(Store.COLUMN_WEAPON_ID))),
                        storeId,
                        bob.getInt(bob.getColumnIndex(Store.COLUMN_AMOUNT)),
                        bob.getInt(bob.getColumnIndex(Store.COLUMN_CART_AMOUNT)));
        }
        bob.close();
        return result;
    }

    public List<WeaponStoreItem> getCartWeapons(){
        List<WeaponStoreItem> cart = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor bob = db.query(Store.TABLE_NAME,
                null,
                Store.COLUMN_CART_AMOUNT + " > ?",
                new String[]{String.valueOf(0)},
                null,
                null,
                null);
        if (bob.moveToFirst()){
            do{
                cart.add(new WeaponStoreItem(
                        getWeaponById(bob.getInt(bob.getColumnIndex(Store.COLUMN_WEAPON_ID))),
                        bob.getInt(bob.getColumnIndex(Store.COLUMN_ID)),
                        bob.getInt(bob.getColumnIndex(Store.COLUMN_AMOUNT)),
                        bob.getInt(bob.getColumnIndex(Store.COLUMN_CART_AMOUNT))));
            } while (bob.moveToNext());
        }
        db.close();
        bob.close();
        return cart;
    }

    public boolean updateCartById(int storeId, int amount){
        SQLiteDatabase db = getWritableDatabase();
        String selection = Store.COLUMN_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(storeId)};
        Cursor bob = db.query(Store.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null, null, null);
        if (bob.moveToFirst()){
            if(amount<0) {
                amount = 0;
            }
            ContentValues values = new ContentValues();
            values.put(Store.COLUMN_CART_AMOUNT, amount);
            db.update(Store.TABLE_NAME, values, selection, selectionArgs);
            bob.close();
            db.close();
            return true;
        }
        bob.close();
        db.close();
        return false;
    }

    public boolean addToCartById(int storeId, int amount){
        SQLiteDatabase db = getWritableDatabase();
        String selection = Store.COLUMN_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(storeId)};
        Cursor bob = db.query(Store.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null, null, null);
        if (bob.moveToFirst()){
            int cartAmount = bob.getInt(bob.getColumnIndex(Store.COLUMN_CART_AMOUNT));
            cartAmount += amount;
            if(cartAmount<0) {
                cartAmount = 0;
            }
            ContentValues values = new ContentValues();
            values.put(Store.COLUMN_CART_AMOUNT, cartAmount);
            db.update(Store.TABLE_NAME, values, selection, selectionArgs);
            bob.close();
            db.close();
            return true;
        }
        bob.close();
        db.close();
        return false;
    }

    public int checkOutOKByStoreId(int storeId){
        List<WeaponStoreItem> cart = new ArrayList<>();
        String selection = Store.COLUMN_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(storeId)};
        SQLiteDatabase db = getReadableDatabase();
        Cursor bob = db.query(Store.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if (bob.moveToFirst()) {
            int cartAmount = bob.getInt(bob.getColumnIndex(Store.COLUMN_CART_AMOUNT));
            int storeAmount = bob.getInt(bob.getColumnIndex(Store.COLUMN_AMOUNT));
            storeAmount -= cartAmount;
            if (storeAmount < 0) {
                bob.close();
                db.close();
                return ITEM_NOT_ENOUGH;
            }
            return CHECKOUT_OK;
        }
        bob.close();
        return ITEM_NOT_FOUND;
    }

    public int checkOutById(int storeId){
        List<WeaponStoreItem> cart = new ArrayList<>();
        String selection = Store.COLUMN_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(storeId)};
        SQLiteDatabase db = getWritableDatabase();
        Cursor bob = db.query(Store.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if (bob.moveToFirst()){
            int cartAmount = bob.getInt(bob.getColumnIndex(Store.COLUMN_CART_AMOUNT));
            int storeAmount = bob.getInt(bob.getColumnIndex(Store.COLUMN_AMOUNT));
            storeAmount -= cartAmount;
            if (storeAmount<0){
                bob.close();
                db.close();
                return ITEM_NOT_ENOUGH;
            } else {
                ContentValues values = new ContentValues();
                values.put(Store.COLUMN_AMOUNT,storeAmount);
                values.put(Store.COLUMN_CART_AMOUNT,0);
                db.update(Store.TABLE_NAME,values, selection, selectionArgs);
                bob.close();
                db.close();
                return CHECKOUT_SUCCESSFUL;
            }
        }
        bob.close();
        db.close();
        return ITEM_NOT_FOUND;
    }

    public List<WeaponStoreItem> searchInventory(String query){
        List<WeaponStoreItem> inventory = new ArrayList<>();
        if (query == null || query.equals("") || query.equals(" ")){
            return inventory;
        }
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(JOIN_ALL);
        Cursor bob = builder.query(db,
                new String[]{Store.TABLE_NAME + "." + Store.COLUMN_ID},
                WeaponsTable.COLUMN_BRAND + " LIKE ? OR " +
                        WeaponTypesTable.COLUMN_NAME + " LIKE ? OR " +
                        ElementsTable.COLUMN_NAME + " LIKE ?",
                new String[]{"%" + query + "%",
                        "%" + query + "%",
                        "%" + query + "%"},
                null, null, null, null);

        if (bob.moveToFirst()){
            do{
                inventory.add(getWeaponItemByStoreId(bob.getInt(bob.getColumnIndex(Store.COLUMN_ID))));
            }while (bob.moveToNext());
        }
        return inventory;
    }


    public List<WeaponStoreItem> getSimilarStoreWeaponById(int storeId){
        //holy crap this was an ordeal...
        //this method basically gives us a list of weapons of the same weapon type, but different elements.
        //ex. if we put in a weaponId that belongs to a dagger,
        //it will give us back a fire dagger, water dagger, earth dagger, air dagger


        List<WeaponStoreItem> results = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(JOIN_STORE_WEAPON);
        Cursor abstractWeapon = builder.query(db,
                null,
                Store.TABLE_NAME + "." + Store.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(storeId)},
                null, null, null, null);

        if (!abstractWeapon.moveToFirst()){
            return results;
        }

        String weaponType = abstractWeapon.getString(abstractWeapon.getColumnIndex(WeaponsTable.COLUMN_TYPE_ID));
        String weaponBrand = abstractWeapon.getString(abstractWeapon.getColumnIndex(WeaponsTable.COLUMN_BRAND));

        abstractWeapon.close();

        Cursor cursor;

        if(weaponBrand == null) {
            cursor = builder.query(db,
                    new String[]{Store.TABLE_NAME + "." + Store.COLUMN_ID},
                    WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_TYPE_ID + " = ? AND " +
                            WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_BRAND + " IS NULL",
                    new String[]{weaponType},
                    null,
                    null,
                    null
            );
        }
        else {
            cursor = builder.query(db,
                    new String[]{Store.TABLE_NAME + "." + Store.COLUMN_ID},
                    WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_TYPE_ID + " = ? AND " +
                            WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_BRAND + " = ?",
                    new String[]{weaponType, weaponBrand},
                    null,
                    null,
                    null
            );
        }

        if(cursor.moveToFirst()){
            do{
                results.add(
                        getWeaponItemByStoreId(cursor.getInt(cursor.getColumnIndex(Store.COLUMN_ID)))
                );
            } while (cursor.moveToNext());
        }
        //i really hope this works. My head hurts, and it's 11PM now...
        cursor.close();
        return results;
    }

    public Weapon getWeaponById(int weaponId){
        Weapon weapon = null;
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(JOIN_WEAPON_TYPE_ELEMENT);
        Cursor weaponCursor = builder.query(db, null,
                WeaponsTable.TABLE_NAME + "." + WeaponsTable.COLUMN_ID + " =?",
                new String[] {String.valueOf(weaponId)},
                null, null, null);
        if(weaponCursor.moveToFirst()){
            String weaponType = weaponCursor.getString(weaponCursor.getColumnIndex(WeaponsTable.COLUMN_TYPE_ID));
            String weaponElement = weaponCursor.getString(weaponCursor.getColumnIndex(WeaponsTable.COLUMN_ELEMENT_ID));
            String weaponName = weaponCursor.getString(weaponCursor.getColumnIndex(WeaponsTable.COLUMN_BRAND));
            if (weaponName == null){
                weaponName = weaponCursor.getString(weaponCursor.getColumnIndex(WeaponTypesTable.COLUMN_NAME));
            }
            String weaponDescription = weaponCursor.getString(weaponCursor.getColumnIndex(WeaponsTable.COLUMN_DESCRIPTION));
            if(weaponDescription == null){
                weaponDescription = "A " +
                        weaponCursor.getString(weaponCursor.getColumnIndex(WeaponTypesTable.COLUMN_NAME)).toLowerCase() +
                        ((weaponElement==null)? ".":" of " +
                                weaponCursor.getString(weaponCursor.getColumnIndex(ElementsTable.COLUMN_NAME)).toLowerCase() + ".");
            }
            int weaponDamage = weaponCursor.getInt(weaponCursor.getColumnIndex(WeaponsTable.COLUMN_DAMAGE));
            if(weaponDamage == 0){
                weaponDamage = weaponCursor.getInt(weaponCursor.getColumnIndex(WeaponTypesTable.COLUMN_DAMAGE));
            }
            int weaponDurability = weaponCursor.getInt(weaponCursor.getColumnIndex(WeaponsTable.COLUMN_DURABILITY));
            if (weaponDurability == 0){
                weaponDurability =  weaponCursor.getInt(weaponCursor.getColumnIndex(WeaponTypesTable.COLUMN_DURABILITY));
            }
            double weaponPrice = weaponCursor.getDouble(weaponCursor.getColumnIndex(WeaponsTable.COLUMN_PRICE));
            if (weaponPrice == 0){
                weaponPrice = weaponCursor.getDouble(weaponCursor.getColumnIndex(WeaponTypesTable.COLUMN_PRICE))
                * ((weaponElement==null)?1:weaponCursor.getDouble(weaponCursor.getColumnIndex(ElementsTable.COLUMN_PRICE_MULTIPLIER)));
            }
            String weaponIcon = weaponCursor.getString(weaponCursor.getColumnIndex(WeaponsTable.COLUMN_ICON));
            if (weaponIcon == null){
                weaponIcon = weaponCursor.getString(weaponCursor.getColumnIndex(WeaponTypesTable.COLUMN_ICON));
            }
            String weaponPicture = weaponCursor.getString(weaponCursor.getColumnIndex(WeaponsTable.COLUMN_PICTURE));
            if (weaponPicture == null){
                weaponPicture = weaponCursor.getString(weaponCursor.getColumnIndex(WeaponTypesTable.COLUMN_PICTURE));
            }
            String weaponElementIcon = weaponCursor.getString(weaponCursor.getColumnIndex(ElementsTable.COLUMN_ICON));
            double weaponRarity = weaponCursor.getDouble(weaponCursor.getColumnIndex(WeaponsTable.COLUMN_RARITY));
            if (weaponRarity == 0){
                weaponRarity = weaponCursor.getDouble(weaponCursor.getColumnIndex(WeaponTypesTable.COLUMN_RARITY));
            }
            weapon = new Weapon(weaponId, weaponType, weaponName, weaponDescription, weaponElement, weaponDamage,
                    weaponDurability, weaponPrice, weaponElementIcon, weaponIcon, weaponPicture, weaponRarity);
        }
        weaponCursor.close();
        return weapon;
    }

    public String getNameFromElementId(String elementId){
        String elementName = "";
        if (elementId == null){
            return elementName;
        }
        SQLiteDatabase db = getReadableDatabase();
        Cursor bob = db.query(ElementsTable.TABLE_NAME,
                new String[]{ElementsTable.COLUMN_NAME},
                ElementsTable.COLUMN_ID + " = ? ",
                new String[]{elementId},
                null, null, null);
        if (bob.moveToFirst()){
            elementName = bob.getString(bob.getColumnIndex(ElementsTable.COLUMN_NAME));
        }
        bob.close();
        return elementName;
    }

    public String getNameFromWeaponTypeId(String weaponTypeId){
        String weaponTypeName = "";
        if (weaponTypeId == null){
            return weaponTypeName;
        }
        SQLiteDatabase db = getReadableDatabase();
        Cursor bob = db.query(WeaponTypesTable.TABLE_NAME,
                new String[]{WeaponTypesTable.COLUMN_NAME},
                WeaponTypesTable.COLUMN_ID + " = ? ",
                new String[]{weaponTypeId},
                null, null, null);
        if (bob.moveToFirst()){
            weaponTypeName = bob.getString(bob.getColumnIndex(WeaponTypesTable.COLUMN_NAME));
        }
        bob.close();
        return weaponTypeName;
    }

    public List<Review> getReviewsByWeaponId(int weaponId){
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor bob = db.query(ReviewsTable.TABLE_NAME,
                null,
                ReviewsTable.COLUMN_WEAPON_ID + " = ? ",
                new String[]{String.valueOf(weaponId)},
                null, null, null);
        if (bob.moveToFirst()){
            do{
                int id = bob.getInt(bob.getColumnIndex(ReviewsTable.COLUMN_ID));
                int wId = bob.getInt(bob.getColumnIndex(ReviewsTable.COLUMN_WEAPON_ID));
                int rating = bob.getInt(bob.getColumnIndex(ReviewsTable.COLUMN_RATING));
                String title = bob.getString(bob.getColumnIndex(ReviewsTable.COLUMN_TITLE));
                String comment = bob.getString(bob.getColumnIndex(ReviewsTable.COLUMN_COMMENT));
                reviews.add(new Review(id,wId, rating, title, comment));
            } while (bob.moveToNext());
        }
        return reviews;
    }


    public void addReview(int weaponId, int rating, String title, String comment){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ReviewsTable.COLUMN_WEAPON_ID, weaponId);
        values.put(ReviewsTable.COLUMN_RATING, rating);
        values.put(ReviewsTable.COLUMN_TITLE, title);
        values.put(ReviewsTable.COLUMN_COMMENT, comment);

        db.insert(ReviewsTable.TABLE_NAME, null, values);

        db.close();

    }

}
