package com.example.task1_api_transactions.data.network;

import com.example.task1_api_transactions.domain.Transactions;
import com.example.task1_api_transactions.domain.User;
import com.example.task1_api_transactions.domain.UserResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class ApiImpl implements ApiHelper {

    private final Api api;

    @Inject
    public ApiImpl(Api api) {
        this.api = api;
    }

    @Override
    public Call<UserResponse> loginUser(User user) {
        return api.loginUser(user);
    }

    @Override
    public Call<List<Transactions>> getTransactions(String token) {
        return api.getTransactions(token);
    }
}
