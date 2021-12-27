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


        Timber.tag(TAG).d(remoteMessage.toString())
        Timber.tag(TAG).d(remoteMessage.data.toString())

//        val map = mutableMapOf<String,String>()
//        map.put("body","You got an Order")
//        map.put("title","Order Received")
//        map.put("for","Seller")
//        map.put("notificationChannelId","FAMECOMCHANNEEL")
//        map.put("notificationName","FAMECOM")
//        map.put("notificationID","1")

        Timber.tag(TAG).d(remoteMessage.data.toString())
        NotificationHelper.buildNotification(applicationContext,remoteMessage.data)

    } // remoteMessage closed

}