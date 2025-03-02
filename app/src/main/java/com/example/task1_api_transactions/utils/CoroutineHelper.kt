package com.example.task1_api_transactions.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CoroutineHelper {
    fun runInBackground(task: suspend () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            task()
        }
    }
}