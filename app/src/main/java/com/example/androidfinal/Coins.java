package com.example.androidfinal;

class Coins {
    private int rank;
    private String ticker;
    private String name;
    private double price;

    public Coins(int rank, String ticker, String name, double price) {
        this.rank = rank;
        this.ticker = ticker;
        this.name = name;
        this.price = price;
    }

    public int getRank() {
        return rank;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Coins{" +
                "rank=" + rank +
                ", ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}