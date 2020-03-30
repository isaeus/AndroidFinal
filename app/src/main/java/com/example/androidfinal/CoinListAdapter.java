package com.example.androidfinal;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CoinListAdapter extends ArrayAdapter<Coins> {

    private Activity context;
    private List<Coins> coinsList = new ArrayList<>();


    public CoinListAdapter(Activity context, List<Coins> coinsList) {
        super(context, 0, coinsList);
        this.context = context;
        this.coinsList = coinsList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.coinlist_layout, null,false);

        Coins currentCoin = coinsList.get(position);

        TextView txtRank =(TextView) rowView.findViewById(R.id.coinlist_rank);
        TextView txtTicker =(TextView) rowView.findViewById(R.id.coinlist_ticker);
        TextView txtName =(TextView) rowView.findViewById(R.id.coinlist_name);
        TextView txtPrice =(TextView) rowView.findViewById(R.id.coinlist_price);

        txtRank.setText(currentCoin.getRank() + "");
        txtTicker.setText(currentCoin.getTicker());
        txtName.setText(currentCoin.getName());
        txtPrice.setText("$" + String.format("%.2f",currentCoin.getPrice()));


        return rowView;
    }
}
