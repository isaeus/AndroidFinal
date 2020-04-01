package com.example.androidfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddWatchlist extends AppCompatActivity {
    private DBHandler db;
    private Button addWatchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_watchlist);
        db = new DBHandler(this);


        addWatchlist = findViewById(R.id.addWatchlist);

        addWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase wdb = db.getWritableDatabase();
                EditText wTicker = findViewById(R.id.watchListTicker);

                ContentValues values = new ContentValues();

                values.put(watchlistentry.COLUMN_TICKER, wTicker.getText().toString().toUpperCase());
                wdb.insert(watchlistentry.TABLE_NAME, null, values);
            }
        });
    }
}
