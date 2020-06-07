package com.example.info448project.manager

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.info448project.R
import com.example.info448project.activity.MainActivity
import com.example.info448project.model.CountryInfo
import kotlin.properties.Delegates

class NotificationManager(private val context: Context) {

    private val notificationManagerCompat = NotificationManagerCompat.from(context)
    private lateinit var chosenPhrase: String
    private var chosenIndex by Delegates.notNull<Int>()

    init {
        create()
    }

    fun post(countryInfo: CountryInfo) {
        chosenPhrase = "The Updated Information coming from CDC shows the Covid-19 Positive has reached" + countryInfo.positive +
                "and the death caused by Covid-19 reached " + countryInfo.death + ". More Detailed Information are welcome to check inside of App."
        chosenIndex = Math.random().toInt();

        val dealsIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingDealsIntent = PendingIntent.getActivity(context, 0, dealsIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val notification = NotificationCompat.Builder(context, SERIOUS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_brightness_5_black_24dp)
            .setContentTitle("Covid-19 Helper")
            .setContentText(chosenPhrase)
            .setContentIntent(pendingDealsIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManagerCompat.notify(chosenIndex, notification)
    }

    private fun create() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "serious notification"
            val descriptionText = "All Messgaes from app using data from CDC"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(SERIOUS_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

    companion object {
        const val SERIOUS_CHANNEL_ID = "SERIOUS_CHANNEL_ID"
    }
}