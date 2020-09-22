package com.elaj.patient.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.elaj.patient.Model.MemberModel
import com.elaj.patient.Model.MessageEvent
import com.elaj.patient.R
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.UtilityApp
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.EventBus

class fcmService : FirebaseMessagingService() {

    var user: MemberModel? = null

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        UtilityApp.fCMToken = s
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.i("FCMService", "Log from " + remoteMessage.from)
        Log.i("FCMService", "Log data " + remoteMessage.data)
        if (remoteMessage.notification != null) Log.i(
            "FCMService",
            "Log notifcation " + remoteMessage.notification!!.body
        )
        val type = remoteMessage.data["type"]
        if (UtilityApp.isLogin) {
            user = UtilityApp.userData

            var title = remoteMessage.data["title"]
            var content = remoteMessage.data["body"]

            if (title.isNullOrEmpty())
                title = remoteMessage.notification?.title

            if (content.isNullOrEmpty())
                content = remoteMessage.notification?.body

            val userIdVal: Any?
            if (type != null)
                when (type) {
//                    REQUEST -> {
//                        val id = remoteMessage.data["id"]
//                        sendNotification(
//                            title,
//                            content,
//                            RequestDetailsActivity::class.java,
//                            id!!
//                        )
//
//                    }
                    ADMIN_NOTIFICATION, "marketing" -> sendNotification(
                        title,
                        content,
                        Constants.MAIN_ACTIVITY_CLASS
                    )
                } else sendNotification(title, content, Constants.MAIN_ACTIVITY_CLASS)
        }
    }

    private fun sendChatNotification(
        messageTitle: String, messageBody: String, friendId: String
        , friendName: String, friendAvatar: String, requestId: String
    ) {

//        Intent intent = new Intent(this, OrderDe.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("firstOpen", "0");
//        intent.putExtra("friendId", friendId + "");
//        intent.putExtra("passengerName", friendName + "");
//        intent.putExtra("friendAvatar", friendAvatar + "");
//        intent.putExtra("requsetId", requestId + "");

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle(messageTitle)
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify((int) System.currentTimeMillis() /* ID of notification */, notificationBuilder.build());
    }

    private fun sendNotification(
        messageTitle: String?,
        messageBody: String?,
        cls: Class<*>,
        vararg params: Any
    ) {
        var messageBody = messageBody
        var isChatMessage = false
        val intent = Intent(this, cls)
        var uId: String? = null
        var type: String? = null
        val username: String
        val contentMessage = messageBody
        var userId = 0

        if (params.isNotEmpty()) uId = params[0] as String
        if (uId != null) userId = uId.toInt()

        when (cls) {
//            RequestDetailsActivity::class.java -> {
//                intent.putExtra(Constants.KEY_REQUEST_ID, userId)
//                intent.putExtra(Constants.KEY_IS_NOTIFY, true)
//            }
            Constants.MAIN_ACTIVITY_CLASS -> {
                intent.putExtra(Constants.KEY_IS_NOTIFY, true)
            }
            else -> {
                intent.putExtra(Constants.KEY_IS_NOTIFY, true)
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            if (isChatMessage) PendingIntent.FLAG_UPDATE_CURRENT else PendingIntent.FLAG_ONE_SHOT
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "Qulob Notifications"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Qulob Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // Configure the notification channel.
            notificationChannel.description = "Qulob Notifications Service"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.MAGENTA
            //            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(false)
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_lunch_48)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(messageBody)
            ) //                .setNumber(2)
            //     .setPriority(Notification.PRIORITY_MAX)
            .setContentTitle(messageTitle)
            .setContentText(contentMessage)
        notificationManager.notify(
            if (isChatMessage) userId else System.currentTimeMillis().toInt(),
            notificationBuilder.build()
        )

//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_launcher_48)
//                .setContentTitle(messageTitle)
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
////                .setPriority(Notification.PRIORITY_HIGH)
//                .setContentIntent(pendingIntent);

//        Notification notification = createNotification(true);

//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify((int) System.currentTimeMillis() /* ID of notification */,/*notification*/  notificationBuilder.build());
    }

    private fun createNotification(
        messageTitle: String,
        messageBody: String,
        pendingIntent: PendingIntent,
        makeHeadsUpNotification: Boolean
    ) {
        val defaultSoundUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: Notification.Builder =
            Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_lunch_48)
                .setContentTitle(messageTitle)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentText(messageBody)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setCategory(Notification.CATEGORY_MESSAGE)
        }
        if (makeHeadsUpNotification) {
            val push = Intent(this, Constants.MAIN_ACTIVITY_CLASS)
            //            push.putExtra(Constants.KEY_NEWS_ID, id);
//            push.putExtra(Constants.KEY_NEWS_NOTIFIY, true);
            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val fullScreenPendingIntent = PendingIntent.getActivity(
                this, 0,
                push, PendingIntent.FLAG_CANCEL_CURRENT
            )
            notificationBuilder
                .setContentText(messageBody) //                    .setFullScreenIntent(fullScreenPendingIntent, true)
                .setContentIntent(fullScreenPendingIntent)
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            System.currentTimeMillis().toInt() /* ID of notification */,  /*notification*/
            notificationBuilder.build()
        )

//        return notificationBuilder.build();
    }

    companion object {
        const val PROVIDER = "provider"
        const val REQUEST = "request"
        const val NEW_CHAT = "new_chat"
        const val NEW_MESSAGE = "new_message"
        const val ADMIN_NOTIFICATION = "notification_from_admin"
    }
}