package com.example.androidfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FetchWorker.fetchWorkerListener {

    private String url = "https://min-api.cryptocompare.com/data/";
    private final String APIKEY = "9c9c65051e18937847eee15f316bece9dbf51c6690221c9acaf542a15f875309";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url += "top/mktcapfull?limit=" + "100" + "&tsym=USD" + "&api_key=" + APIKEY;
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
}