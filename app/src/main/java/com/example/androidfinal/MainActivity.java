package com.example.androidfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FetchWorker.fetchWorkerListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView botNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Top 100");
        botNav = (BottomNavigationView) findViewById(R.id.bottomNav);
        botNav.setOnNavigationItemSelectedListener(this);
        botNav.getMenu().findItem(R.id.item1).setChecked(true);

        String url;
        url = CryptoAPI.url + "top/mktcapfull?limit=" + "100" + "&tsym=USD" + "&api_key=" + CryptoAPI.APIKEY;
        FetchWorker fw = new FetchWorker(MainActivity.this);
        fw.execute(url);

    }

    @Override
    public List getResult(String jsonData) {
        ListView displayCoins = (ListView) findViewById(R.id.displayCoins);;
        List<Coins> coinsList = new ArrayList<>();
        //create variables for coin parameters
        int rank;
        String ticker;
        String name;
        double price;
        //Log.d("Msg", response.toString());
        try {
            JSONObject response = new JSONObject(jsonData);
            for (int i = 0; i < 100; i++) {
                rank = i + 1;
                ticker = response.getJSONArray("Data").getJSONObject(i)
                        .getJSONObject("CoinInfo").getString("Name");
                name = response.getJSONArray("Data").getJSONObject(i)
                        .getJSONObject("CoinInfo").getString("FullName");
                price = Double.parseDouble(response.getJSONArray("Data").getJSONObject(i)
                        .getJSONObject("RAW").getJSONObject("USD").getString("PRICE"));

                Coins currentCoin = new Coins(rank, ticker, name, price);
                coinsList.add(currentCoin);
                CoinListAdapter cAdapter = new CoinListAdapter(MainActivity.this, coinsList);
                displayCoins.setAdapter(cAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:

                break;
            case R.id.item2:
                Intent toWatchlist = new Intent(this, WatchList.class);
                startActivity(toWatchlist);
                break;
            case R.id.item3:
                Intent toPortfolio = new Intent(this, Portfolio.class);
                startActivity(toPortfolio);
                break;
        }

        return true;
    }
}