package com.example.task1_api_transactions.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task1_api_transactions.R;
import com.example.task1_api_transactions.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set status bar color according to theme
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_color, this.getTheme()));
    }
}