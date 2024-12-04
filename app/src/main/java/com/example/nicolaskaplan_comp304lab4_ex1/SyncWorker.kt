package com.example.nicolaskaplan_comp304lab4_ex1

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SyncWorker(context:Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        try {
            Log.d("SyncWorker", "syncing data in the background...")
            Thread.sleep(2000)
        } catch (e: Exception) {
            Log.e("SyncWorker", "sync failed: ${e.message}")
            return Result.failure()
        }
        Log.d("SyncWorker", "sync successful")
        return Result.success()
    }
}