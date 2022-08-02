package com.test.dailyroutine

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.delay

var notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"
const val notiIdExtra = "notiIdExtra"

class AlarmNotification : BroadcastReceiver()
{

    override fun onReceive(context: Context, intent: Intent)
    {
        /*var attributes: AudioAttributes.Builder? = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        */
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/${R.raw.iphone_ringtone}"))
            .setAutoCancel(true)
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(notificationID, notification)

        if(intent.getLongExtra(notiIdExtra,0) != 0L){
            Log.d("###Deleted noti", notificationID.toString())
            manager.cancel(notificationID)
        }

    }

}