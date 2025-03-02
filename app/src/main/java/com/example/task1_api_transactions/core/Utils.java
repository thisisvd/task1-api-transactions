package com.example.task1_api_transactions.core;

import android.os.Build;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Utils {

    // get date format
    public static String getDataFormat(String dateValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateValue, inputFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            return date.format(outputFormatter);
        } else {
            return dateValue;
        }
    }
}
