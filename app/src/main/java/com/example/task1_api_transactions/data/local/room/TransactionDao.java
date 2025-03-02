package com.example.task1_api_transactions.data.local.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.task1_api_transactions.domain.Transactions;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insertTransactions(Transactions transactions);

    @Query("SELECT * FROM transactions_table")
    List<Transactions> getAllTransactions();

    @Query("DELETE FROM transactions_table")
    void deleteAllTransactions();
}
