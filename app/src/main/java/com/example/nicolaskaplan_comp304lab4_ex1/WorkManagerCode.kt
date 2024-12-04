package com.example.nicolaskaplan_comp304lab4_ex1

import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkManagerCode {
    fun setupWorkManager(context: Context) {
        val syncRequest = PeriodicWorkRequest.Builder(SyncWorker::class.java, 15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(context).enqueue(syncRequest)
    }
}