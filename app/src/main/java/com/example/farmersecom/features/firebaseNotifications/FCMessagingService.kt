package com.example.farmersecom.features.firebaseNotifications

import com.example.farmersecom.utils.constants.Constants.TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FCMessagingService:FirebaseMessagingService()
{

    override fun onNewToken(newToken: String)
    {
        super.onNewToken(newToken)

        Timber.tag(TAG).d("notification token: $newToken")

    } // newToken
    override fun onMessageReceived(remoteMessage: RemoteMessage)
    {
        super.onMessageReceived(remoteMessage)


            Timber.tag(TAG).d("notification_data"+remoteMessage.data.toString())

        val icon = remoteMessage.notification?.icon
        NotificationHelper.buildNotification(applicationContext,remoteMessage.data,icon)

    } // remoteMessage closed

}