package com.example.catatankeuangan;

public class Transaction {

    private int id;
    private String type;
    private String amount;
    private String description;
    private String date;

    public Transaction(int id, String type, String amount, String description, String date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Transaction(String type, String amount, String description, String date) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}