package com.example.info448project.manager

import android.content.Context
import androidx.work.*
import com.example.info448project.PostMessageWorker
import java.util.concurrent.TimeUnit

class WorkBackgroundManager(private var context: Context) {

    private var workManager: WorkManager = WorkManager.getInstance(context)

    fun startFetchForDaily() {
        if (isRunning()) {
            stopFetchDaily()
        }

        val constrains = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<PostMessageWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(constrains)
            .addTag(AWTY_WORK_REQUEST_TAG)
            .build()

        workManager.enqueue(workRequest)
    }

    private fun isRunning(): Boolean {
        return when (workManager.getWorkInfosByTag(AWTY_WORK_REQUEST_TAG).get().firstOrNull()?.state) {
            WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
        }
    }

    fun stopFetchDaily() {
        workManager.cancelAllWorkByTag(AWTY_WORK_REQUEST_TAG)
    }

    companion object {
        const val AWTY_WORK_REQUEST_TAG = "AWTY_WORK_REQUEST_TAG"
    }
}