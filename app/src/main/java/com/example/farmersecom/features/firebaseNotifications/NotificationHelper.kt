package com.example.farmersecom.features.firebaseNotifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.farmersecom.R

object NotificationHelper
{

//    val NOTIFICATION_CHANNEL_ID = "DRIVER_NOTIFICATION"
//    val NOTIFICATION_NAME = "NOTIFY_DRIVER"


    fun buildNotification(context: Context, data: MutableMap<String, String>)
    {
        val body = data.get("body")
        val title = data.get("title")
        val notificationFor = data.get("for") // CustomerORBuyer
        val channelId = data.get("notificationChannelId")
        val notificationName = data.get("notificationName")
        val notificationId= data.get("notificationID")


        var notificationChannel: NotificationChannel? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationChannel = NotificationChannel(
                channelId,
                notificationName,
                NotificationManager.IMPORTANCE_HIGH
            )
        }

//
//        var pendingIntent: PendingIntent? = null
//        val currentActiveJob: Intent
//
//            currentActiveJob = Intent(context, MainActivity::class.java)
//            currentActiveJob.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            PendingIntent.getActivity(context, 1, currentActiveJob, PendingIntent.FLAG_UPDATE_CURRENT)
//
        var notification: Notification? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notification = Notification.Builder(context,channelId)
                .setContentText(title)
                .setContentTitle(body)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
               // .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationManager.createNotificationChannel(notificationChannel!!)
        }

        notificationManager.notify(notificationId?.toInt()!!, notification)
    } // buildNotification closed


}