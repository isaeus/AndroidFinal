package com.example.androidfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

public class AddPortfolio extends AppCompatActivity implements FetchWorker.fetchWorkerListener{

    private TextView tvDate;
    private DatePickerDialog datePicker;
    private Calendar calendar;
    private int year, month, day;
    private Button addTransaction;
    private DBHandler db;
    private RadioButton buyTransaction;
    private RadioButton sellTransaction;
    private EditText pPrice;
    private EditText pQuantity;
    private TextView pDate;
    private EditText pTicker;
    private boolean tickerExists;

    private TextWatcher tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_portfolio);

        db = new DBHandler(this);
        tvDate = (TextView) findViewById(R.id.portfolioDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setText(month + 1 + "/" + day + "/" + year);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(AddPortfolio.this, dateListener, year, month, day);
                datePicker.show();
            }
        });

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
                url = CryptoAPI.url + "pricemultifull?fsyms=" + pTicker.getText().toString().toUpperCase() +"&tsyms=USD";
                FetchWorker fw = new FetchWorker(AddPortfolio.this);
                fw.execute(url);
            }
        };
        pTicker = findViewById(R.id.portfolioCoinName);

        pTicker.addTextChangedListener(tw);

        addTransaction = findViewById(R.id.addTransaction);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase wdb = db.getWritableDatabase();
                pPrice = findViewById(R.id.portfolioPrice);
                pQuantity = findViewById(R.id.portfolioQuantity);
                pDate = findViewById(R.id.portfolioDate);
                buyTransaction = findViewById(R.id.buytransaction);
                sellTransaction = findViewById(R.id.selltransaction);

                if(tickerExists == true && (!pPrice.getText().toString().equals("")) && (!pQuantity.getText().toString().equals(""))){
                    ContentValues values = new ContentValues();

                    values.put(portfolioentry.COLUMN_TICKER, pTicker.getText().toString().toUpperCase());
                    Log.d("portfolioEntry", "Ticker:" + pTicker.getText().toString().toUpperCase());
                    values.put(portfolioentry.COLUMN_PRICE, pPrice.getText().toString());
                    Log.d("portfolioEntry", "Purchase Price:" + pPrice.getText().toString());
                    if(buyTransaction.isChecked()){
                        values.put(portfolioentry.COLUMN_QUANTITY, pQuantity.getText().toString());
                    }
                    else if(sellTransaction.isChecked()){
                        values.put(portfolioentry.COLUMN_QUANTITY, "-"+pQuantity.getText().toString());
                    }
                    Log.d("portfolioEntry", "Purchase Quantity:" + pQuantity.getText().toString());
                    values.put(portfolioentry.COLUMN_DATE, pDate.getText().toString());
                    Log.d("portfolioEntry", "Purchase Date:" + pDate.getText().toString());
                    wdb.insert(portfolioentry.TABLE_NAME, null, values);
                    Toast.makeText(getApplicationContext(), "Transaction Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Ticker not found / missing fields", Toast.LENGTH_LONG).show();
                }

            }
        });



    }
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            tvDate.setText( (month+1) + "/" + dayOfMonth + "/" + year);
        }
    };

    @Override
    public List getResult(String jsonData) {
        try {
            JSONObject response = new JSONObject(jsonData);
            if( (response.getJSONObject("RAW").getJSONObject(pTicker.getText().toString().toUpperCase()).getJSONObject("USD").getString("FROMSYMBOL").toUpperCase()).equals(pTicker.getText().toString().toUpperCase())){
                Log.d("test", "Coin Found");
                tickerExists = true;
            }
            else{
                tickerExists = false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tickerExists", e.getMessage());
            tickerExists = false;
        }


        return null;
    }
}