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

    @Query("SELECT * FROM transactions_table " + "WHERE description LIKE '%' || :searchQuery || '%' " + "OR category LIKE '%' || :searchQuery || '%' " + "OR date LIKE '%' || :searchQuery || '%' " + "OR amount LIKE '%' || :searchQuery || '%'")
    List<Transactions> searchTransactions(String searchQuery);

    @Query("DELETE FROM transactions_table")
    void deleteAllTransactions();
}
