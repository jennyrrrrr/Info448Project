package com.example.info448project

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.info448project.manager.DataManager
import com.example.info448project.manager.NotificationManager

class PostMessageWorker(private val context: Context, workParams: WorkerParameters): Worker(context, workParams) {
    override fun doWork(): Result {
        val dataManager = DataManager(context)
        val notificationManager = NotificationManager(context)

        dataManager.getCountry {
            notificationManager.post(it.first())
        }

        return Result.success()
    }
}