package com.demoFirebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessaging : FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification?.title
            ?: message.data["title"]

        val body = message.notification?.body
            ?: message.data["body"]

        showNotification(title, body)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FcmToken", "onNewToken: $token")
    }


    private fun showNotification(title: String?, message: String?) {

        var channelId= "ChannelID_1"

       var notificationManager= getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                "High Priority Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.enableLights(true)
            channel.enableVibration(true)

          notificationManager.createNotificationChannel(channel)

        }

        val notification = NotificationCompat.Builder(this, channelId)
             .setSmallIcon(R.drawable.notification) // must exist
            .setContentTitle(title ?: "New Message")
            .setContentText(message ?: "")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            System.currentTimeMillis().toInt(),
            notification
        )
    }
}