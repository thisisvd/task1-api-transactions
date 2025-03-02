package com.example.task1_api_transactions.di

import android.content.Context
import com.example.task1_api_transactions.BuildConfig
import com.example.task1_api_transactions.data.MainRepository
import com.example.task1_api_transactions.data.local.EncryptedSharedPreference
import com.example.task1_api_transactions.data.network.Api
import com.example.task1_api_transactions.data.network.ApiHelper
import com.example.task1_api_transactions.data.network.ApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.prepstripe.com/").client(okHttpClient).build()

    @Provides
    fun provideApiInterface(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiImpl): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideEncryptedSharedPreference(@ApplicationContext context: Context): EncryptedSharedPreference {
        return EncryptedSharedPreference(context)
    }

    @Provides
    @Singleton
    fun provideMainRepository(
        apiHelper: ApiImpl, encryptedSharedPreference: EncryptedSharedPreference
    ): MainRepository {
        return MainRepository(apiHelper, encryptedSharedPreference)
    }
}