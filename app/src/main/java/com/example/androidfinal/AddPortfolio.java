package com.example.androidfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddPortfolio extends AppCompatActivity {

//    private BottomNavigationView botNav;
    private TextView tvDate;
    private DatePickerDialog datePicker;
    private Calendar calendar;
    private int year, month, day;
    private Button addTransaction;
    private DBHandler db;
    private RadioButton buyTransaction;
    private RadioButton sellTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_portfolio);

        db = new DBHandler(this);
        SQLiteDatabase wdb = db.getWritableDatabase();

//        botNav = (BottomNavigationView) findViewById(R.id.bottomNav);
//        botNav.setOnNavigationItemSelectedListener(this);
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

        addTransaction = findViewById(R.id.addTransaction);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase wdb = db.getWritableDatabase();
                EditText pPrice = findViewById(R.id.portfolioPrice);
                EditText pQuantity = findViewById(R.id.portfolioQuantity);
                TextView pDate = findViewById(R.id.portfolioDate);
                buyTransaction = findViewById(R.id.buytransaction);
                sellTransaction = findViewById(R.id.selltransaction);
                EditText pTicker = findViewById(R.id.portfolioCoinName);

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

                long newRowID = wdb.insert(portfolioentry.TABLE_NAME, null, values);
                Log.d("msg", newRowID + "");
                Toast.makeText(getApplicationContext(), "Transaction Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            tvDate.setText( (month+1) + "/" + dayOfMonth + "/" + year);
        }
    };

}