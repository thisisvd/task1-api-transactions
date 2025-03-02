package com.example.task1_api_transactions.data.network;

import com.example.task1_api_transactions.domain.Transactions;
import com.example.task1_api_transactions.domain.User;
import com.example.task1_api_transactions.domain.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;

public interface ApiHelper {

    Call<UserResponse> loginUser(@Body User user);

    Call<List<Transactions>> getTransactions(String token);
}