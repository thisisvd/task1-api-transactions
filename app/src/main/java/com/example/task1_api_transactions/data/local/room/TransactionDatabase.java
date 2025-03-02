package com.example.task1_api_transactions.data.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.task1_api_transactions.domain.Transactions;

@Database(entities = {Transactions.class}, version = 1, exportSchema = false)
public abstract class TransactionDatabase extends RoomDatabase {

    public abstract TransactionDao transactionDao();
}
