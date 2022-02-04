package com.openclassrooms.realestatemanager.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.openclassrooms.realestatemanager.R

class Notification(context: Context) {
    val context = context
    val addProperty = context.getString(R.string.addProperty)
    val descAdd = context.getString(R.string.descAdd)

    fun showNotification() {
        createNotificationChannel()
        val builder = NotificationCompat.Builder(context, addProperty)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(addProperty)
            .setContentText(descAdd)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, builder.build())
    }

    private fun createNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                addProperty,
                addProperty,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = descAdd
            channel.enableVibration(true)

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager: NotificationManager =
                context.getSystemService<NotificationManager>(
                    NotificationManager::class.java
                )
            notificationManager.createNotificationChannel(channel)

        }
    }
}