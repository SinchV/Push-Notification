package com.example.message

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig.Flag
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val channelId = "123"
const val channelName = "com.example.message"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    //generate the notification
    //attach the notification created with the custom layout
    // show the notification

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            message.notification!!.body?.let {
                message.notification!!!!.title?.let { it1 ->
                    generateNotification(
                        it1, it
                    )
                }
            }
        }
    }

    fun generateNotification(Title: String, Message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        //channel id,channel name


        var builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.article)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
        builder = builder.setContent(getRemoteView(Title, Message))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())

    }

    private fun getRemoteView(title: String, message: String): RemoteViews? {
        val remoteView = RemoteViews("com.example.message", R.layout.actvity_notification)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.article)
        return remoteView
    }
}