package com.example.androidfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "crypto.db";
    private static final int DB_VERSION = 1;

    //Watchlist sql creation/deletion statements
    private static final String SQL_CREATE_WATCHLIST =
            "CREATE TABLE " + watchlistentry.TABLE_NAME + " (" +
                    watchlistentry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    watchlistentry.COLUMN_TICKER + " TEXT)";

    private static final String SQL_DELETE_WATCHLIST =
            "DROP TABLE IF EXISTS " + watchlistentry.TABLE_NAME;

    //Portfolio sql creation/deletion statements
    private static final String SQL_CREATE_PORTFOLIO =
            "CREATE TABLE " + portfolioentry.TABLE_NAME + " (" +
                    portfolioentry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    portfolioentry.COLUMN_TICKER + " TEXT," +
                    portfolioentry.COLUMN_PRICE + " DOUBLE," +
                    portfolioentry.COLUMN_QUANTITY + " DOUBLE," +
                    portfolioentry.COLUMN_DATE + " DATE)";

    private static final String SQL_DELETE_PORTFOLIO =
            "DROP TABLE IF EXISTS " + portfolioentry.TABLE_NAME;


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create both tables
        db.execSQL(SQL_CREATE_PORTFOLIO);
        db.execSQL(SQL_CREATE_WATCHLIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PORTFOLIO);
        db.execSQL(SQL_DELETE_WATCHLIST);
        onCreate(db);
    }


}
