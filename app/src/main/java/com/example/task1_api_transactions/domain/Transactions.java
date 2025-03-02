package com.example.task1_api_transactions.domain;

import static com.example.task1_api_transactions.utils.Constants.DATABASE_TABLE_NAME;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = DATABASE_TABLE_NAME)
public class Transactions {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private String date;

    @SerializedName("amount")
    private int amount;

    @SerializedName("category")
    private String category;

    @SerializedName("description")
    private String description;

    public Transactions(int id, String date, int amount, String category, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}