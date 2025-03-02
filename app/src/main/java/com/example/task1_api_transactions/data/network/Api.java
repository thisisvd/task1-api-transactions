package com.example.task1_api_transactions.data.network;

import com.example.task1_api_transactions.domain.Transactions;
import com.example.task1_api_transactions.domain.User;
import com.example.task1_api_transactions.domain.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @POST("login")
    Call<UserResponse> loginUser(@Body User user);

    @POST("transactions")
    Call<List<Transactions>> getTransactions(@Header("Authorization") String token);
}