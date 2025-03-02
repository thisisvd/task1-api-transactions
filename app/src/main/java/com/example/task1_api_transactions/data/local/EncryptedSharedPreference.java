package com.example.task1_api_transactions.data.local;

import static com.example.task1_api_transactions.utils.Constants.PREFERENCE_TOKEN;

import android.content.Context;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import timber.log.Timber;

@Singleton
public class EncryptedSharedPreference {

    private final Context context;

    @Inject
    public EncryptedSharedPreference(@ApplicationContext Context context) {
        this.context = context;
    }

    private EncryptedSharedPreferences getEncryptedSharedPreferences() {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            return (EncryptedSharedPreferences) EncryptedSharedPreferences.create("secure_prefs", masterKeyAlias, context, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (Exception e) {
            Timber.d("Error : %s", e.getMessage());
            return null;
        }
    }

    // save token
    public void saveToken(String token) {
        EncryptedSharedPreferences sharedPreferences = getEncryptedSharedPreferences();
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(PREFERENCE_TOKEN, token).apply();
        }
    }

    // get token
    public String getToken() {
        EncryptedSharedPreferences sharedPreferences = getEncryptedSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getString(PREFERENCE_TOKEN, null);
        }
        return null;
    }

    // remove token
    public void removeToken() {
        EncryptedSharedPreferences sharedPreferences = getEncryptedSharedPreferences();
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove(PREFERENCE_TOKEN).apply();
        }
    }
}