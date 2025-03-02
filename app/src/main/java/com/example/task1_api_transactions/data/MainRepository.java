package com.example.task1_api_transactions.data;

import static com.example.task1_api_transactions.utils.Constants.RESULT_SUCCESS;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.task1_api_transactions.core.Resource;
import com.example.task1_api_transactions.data.local.EncryptedSharedPreference;
import com.example.task1_api_transactions.data.network.ApiImpl;
import com.example.task1_api_transactions.domain.Transactions;
import com.example.task1_api_transactions.domain.User;
import com.example.task1_api_transactions.domain.UserResponse;

import java.util.List;

import javax.inject.Inject;

import jakarta.inject.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class MainRepository {

    // vars
    private final ApiImpl apiImpl;
    private final EncryptedSharedPreference encryptedSharedPreference;

    // inject classes
    @Inject
    public MainRepository(ApiImpl apiImpl, EncryptedSharedPreference encryptedSharedPreference) {
        this.apiImpl = apiImpl;
        this.encryptedSharedPreference = encryptedSharedPreference;
    }

    // login user
    public LiveData<Resource<String>> loginUser(User user) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        liveData.postValue(new Resource.Loading<>());

        apiImpl.loginUser(user).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    Timber.d("Response : %s", token);
                    encryptedSharedPreference.saveToken(token);
                    liveData.postValue(new Resource.Success<>(RESULT_SUCCESS));
                } else {
                    String errorMsg = "Login Failed : " + response.code() + ", " + response.message();
                    Timber.d(errorMsg);
                    if (response.code() == 401) {
                        errorMsg = "Invalid username or password";
                    }
                    liveData.postValue(new Resource.Error<>(errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable throwable) {
                Timber.d("Login Error : %s", throwable.getMessage());
                liveData.postValue(new Resource.Error<>("Network error occurred!"));
            }
        });

        return liveData;
    }

    // get transactions lists
    public LiveData<Resource<List<Transactions>>> getTransactions() {
        MutableLiveData<Resource<List<Transactions>>> liveData = new MutableLiveData<>();
        liveData.postValue(new Resource.Loading<>());

        String token = encryptedSharedPreference.getToken();

        if (token != null) {
            apiImpl.getTransactions(token).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<List<Transactions>> call, @NonNull Response<List<Transactions>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (!response.body().isEmpty()) {
                            liveData.postValue(new Resource.Success<>(response.body()));
                        } else {
                            liveData.postValue(new Resource.Error<>("Empty List"));
                        }
                    } else {
                        Timber.d("Transactions Failed : " + response.code() + ", " + response.message());
                        liveData.postValue(new Resource.Error<>("Transactions Failed : " + response.code() + ", " + response.message()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Transactions>> call, @NonNull Throwable throwable) {
                    Timber.d("Transactions Error : %s", throwable.getMessage());
                    liveData.postValue(new Resource.Error<>("Network error occurred!"));
                }
            });
        } else {
            Timber.d("Transactions Error : Empty Token.");
            liveData.postValue(new Resource.Error<>("Transactions Failed : Empty Token."));
        }
        return liveData;
    }

    public boolean hasToken() {
        return encryptedSharedPreference.getToken() != null;
    }

    // logout user
    public LiveData<Resource<String>> logout() {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        liveData.postValue(new Resource.Loading<>());

        try {
            encryptedSharedPreference.removeToken();
            liveData.postValue(new Resource.Success<>(RESULT_SUCCESS));
        } catch (Exception e) {
            Timber.d("Error : %s", e.getMessage());
            liveData.postValue(new Resource.Error<>(e.getMessage()));
        }

        return liveData;
    }
}