package com.example.farmersecom.features.firebaseNotifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.farmersecom.R
import com.example.farmersecom.base.MainActivity

object NotificationHelper
{

    val NOTIFICATION_CHANNEL_ID = "FARMERS_NOTIFICATION"
    val NOTIFICATION_NAME = "NOTIFYUser"


    fun buildNotification(context: Context, data: MutableMap<String, String>)
    {
        val body = data["body"]
        val title = data["title"]
        val notificationFor = data["for"]?.toInt() // CustomerORBuyer

       // val channelId = data.get("notificationChannelId")
       // val notificationName = data.get("notificationName")
       // val notificationId= data.get("notificationID")


        var notificationChannel: NotificationChannel? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
        } // if closed



        var pendingIntent: PendingIntent? = null

        val forIntent = Intent(context, MainActivity::class.java)
            forIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            forIntent.putExtra("for",notificationFor)
            pendingIntent = getActivity(context, 1, forIntent, FLAG_UPDATE_CURRENT)

        var notification: Notification? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notification = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationManager.createNotificationChannel(notificationChannel!!)
        }


        notificationManager.notify(notificationFor!!, notification)

    } // buildNotification closed


}