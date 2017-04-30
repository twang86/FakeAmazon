package com.lab.twang.capitalismwarrior.DatabaseStuff;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by twang on 4/8/17.
 */

public class DBAssetHelper extends SQLiteAssetHelper{

    private static final String DATABASE_NAME = "ITEMS.db";
    private static final int DATABASE_VERSION = 1;

    public DBAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
