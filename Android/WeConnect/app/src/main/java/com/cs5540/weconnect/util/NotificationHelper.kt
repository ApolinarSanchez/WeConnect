package com.cs5540.weconnect.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.gson.Gson
import com.cs5540.weconnect.R
import com.cs5540.android.model.User

class NotificationHelper(val context: Context) {

    companion object {
        private const val CHANNEL_ID_USER = "channel_user"
        private const val CHANNEL_NAME_USER = "user"
        private const val EXTRA_USER = "user"
        private const val NOTIFICATION_ID_USER = 1
    }

    private val gson by lazy { Gson() }

    fun sendLocalNotification(letter: USER) {
        val pendingIntent = buildPendingIntentFromNavigation(letter)
        val notification = buildLetterNotification(letter, pendingIntent!!)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID_USER,
                    CHANNEL_NAME_USER,
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        notificationManager.notify(NOTIFICATION_ID_USER, notification)
    }

    private fun buildLetterNotification(
        user: User,
        pendingIntent: PendingIntent
    ): Notification? {
        val contentText = "Welcome back ${user.name}!"
        return NotificationCompat.Builder(context, CHANNEL_ID_USER)
            .setContentTitle("Another day, another moment to hustle!")
            .setContentText(contentText)
//            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(contentText)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()
    }

    private fun buildPendingIntentFromNavigation(user: User): PendingIntent? {
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, gson.toJson(user))
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.nav_home)
            .setGraph(R.navigation.mobile_navigation)
            .setArguments(bundle)
            .createPendingIntent()
    }
}