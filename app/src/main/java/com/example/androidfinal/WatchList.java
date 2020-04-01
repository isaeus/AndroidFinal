package com.example.androidfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WatchList extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {
    private BottomNavigationView botNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        getSupportActionBar().setTitle("Watchlist");
        botNav = (BottomNavigationView) findViewById(R.id.bottomNav);
        botNav.setOnNavigationItemSelectedListener(this);
        botNav.getMenu().findItem(R.id.item2).setChecked(true);
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
                break;
            case R.id.item3:
                Intent toPortfolio = new Intent(this, Portfolio.class);
                startActivity(toPortfolio);
                break;
        }
        return false;
    }

}
