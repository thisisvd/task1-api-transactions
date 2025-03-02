package com.example.task1_api_transactions.di;

import static com.example.task1_api_transactions.utils.Constants.DATABASE_NAME;

import android.content.Context;

import androidx.room.Room;

import com.example.task1_api_transactions.core.NetworkUtils;
import com.example.task1_api_transactions.data.MainRepository;
import com.example.task1_api_transactions.data.local.EncryptedSharedPreference;
import com.example.task1_api_transactions.data.local.room.TransactionDao;
import com.example.task1_api_transactions.data.local.room.TransactionDatabase;
import com.example.task1_api_transactions.data.network.ApiImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static TransactionDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, TransactionDatabase.class, DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    public static TransactionDao providesTransactionDao(TransactionDatabase database) {
        return database.transactionDao();
    }

    @Provides
    @Singleton
    public EncryptedSharedPreference provideEncryptedSharedPreference(@ApplicationContext Context context) {
        return new EncryptedSharedPreference(context);
    }

    @Provides
    @Singleton
    public MainRepository provideMainRepository(ApiImpl apiImpl, EncryptedSharedPreference encryptedSharedPreference, TransactionDao transactionDao, NetworkUtils networkUtils) {
        return new MainRepository(apiImpl, encryptedSharedPreference, transactionDao, networkUtils);
    }
}
