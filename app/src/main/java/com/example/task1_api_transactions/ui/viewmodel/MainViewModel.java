package com.example.task1_api_transactions.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.task1_api_transactions.core.Resource;
import com.example.task1_api_transactions.data.MainRepository;
import com.example.task1_api_transactions.domain.Transactions;
import com.example.task1_api_transactions.domain.User;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final MainRepository mainRepository;

    @Inject
    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    // login live data
    private final MutableLiveData<Resource<String>> _login = new MutableLiveData<>();
    public LiveData<Resource<String>> login = _login;

    // login user
    public void loginUser(User user) {
        mainRepository.loginUser(user).observeForever(_login::setValue);
    }

    // transaction live data
    private final MutableLiveData<Resource<List<Transactions>>> _transactions = new MutableLiveData<>();
    public LiveData<Resource<List<Transactions>>> transactions = _transactions;

    // transaction lists
    public void getTransactions() {
        mainRepository.getTransactions().observeForever(_transactions::setValue);
    }

    // clear login state after successful user login
    public void clearLoginState() {
        _login.setValue(null);
    }

    // check token
    public boolean hasToken() {
        return mainRepository.hasToken();
    }

    // logout live data
    private final MutableLiveData<Resource<String>> _logout = new MutableLiveData<>();
    public LiveData<Resource<String>> logout = _logout;

    // logout
    public void logout() {
        mainRepository.logout().observeForever(_logout::setValue);
    }
}