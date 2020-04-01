package com.example.androidfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Portfolio extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView botNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        getSupportActionBar().setTitle("Portfolio");
        botNav = (BottomNavigationView) findViewById(R.id.bottomNav);
        botNav.setOnNavigationItemSelectedListener(this);
        botNav.getMenu().findItem(R.id.item3).setChecked(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_app_bar,menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent toMarket = new Intent(this, MainActivity.class);
                startActivity(toMarket);
                break;
            case R.id.item2:
                Intent toWatchlist = new Intent(this, WatchList.class);
                startActivity(toWatchlist);
                break;
            case R.id.item3:
                break;
        }
        return false;
    }
}
