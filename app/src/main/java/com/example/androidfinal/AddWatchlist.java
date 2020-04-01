package com.example.androidfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AddWatchlist extends AppCompatActivity implements FetchWorker.fetchWorkerListener{
    private DBHandler db;
    private Button addWatchlist;
    private boolean tickerExists;
    private EditText wTicker;
    private TextWatcher tw;
    private boolean inWatchlist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_watchlist);
        db = new DBHandler(this);
        final SQLiteDatabase rdb = db.getReadableDatabase();

        tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Check if ticker symbol exists
                String url;
                url = CryptoAPI.url + "pricemultifull?fsyms=" + wTicker.getText().toString().toUpperCase() +"&tsyms=USD";
                FetchWorker fw = new FetchWorker(AddWatchlist.this);
                fw.execute(url);

                //Check if coin is already in watchlist or not

                String query = "SELECT * FROM " + watchlistentry.TABLE_NAME + " WHERE " + watchlistentry.COLUMN_TICKER + "=" + "\'" + wTicker.getText().toString().toUpperCase() + "\'";
                Cursor cursor = rdb.rawQuery(query,null);
                if(cursor.getCount()>0){
                    inWatchlist = true;
                }
                else{
                    inWatchlist = false;
                }
                Log.d("inWatchlist", inWatchlist+"");

            }
        };


        wTicker = findViewById(R.id.watchListTicker);
        wTicker.addTextChangedListener(tw);

        addWatchlist = findViewById(R.id.addWatchlist);
        addWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inWatchlist == false && tickerExists == true) {
                    SQLiteDatabase wdb = db.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(watchlistentry.COLUMN_TICKER, wTicker.getText().toString().toUpperCase());
                    wdb.insert(watchlistentry.TABLE_NAME, null, values);
                    rdb.close();
                    wdb.close();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Ticker not found / already in watchlist", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public List getResult(String jsonData) {
        try {
            JSONObject response = new JSONObject(jsonData);
            if( (response.getJSONObject("RAW").getJSONObject(wTicker.getText().toString().toUpperCase()).getJSONObject("USD").getString("FROMSYMBOL").toUpperCase()).equals(wTicker.getText().toString().toUpperCase())){
                Log.d("test", "Coin Found");
                tickerExists = true;
            }
            else{
                tickerExists = false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("connectionStatus", e.getMessage());
            tickerExists = false;
        }


        return null;
    }
}
