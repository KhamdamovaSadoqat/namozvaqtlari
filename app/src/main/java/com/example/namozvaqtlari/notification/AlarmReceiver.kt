
package com.example.namozvaqtlari.notification

import android.app.*
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.location.Location
import android.media.RingtoneManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.constants.*
import com.example.namozvaqtlari.helper.TimeHelper
import com.example.namozvaqtlari.ui.home.HomeFragment
import com.example.namozvaqtlari.ui.mainActivity.MainActivity
import com.example.namozvaqtlari.utils.DateUtils
import com.example.namozvaqtlari.utils.PartOfDayUtils
import java.util.*
import kotlin.time.days


const val CHANNEL_ID = "alarm_channel"
const val CHANNEL_NAME = "prayer_time"

const val BUNDLE_EXTRA = "bundle_extra"
const val ALARM_KEY = "alarm_key"

const val NOTIFICATION_ID = 1000

class AlarmReceiver : BroadcastReceiver() {

    private val TAG = AlarmReceiver::class.java.simpleName
    val timeNotification: Long = 0
    
    override fun onReceive(context: Context?, intent: Intent?) {

//        val alarm = intent?.getBundleExtra(BUNDLE_EXTRA)?.getString(ALARM_KEY)
//        if (alarm == null) {
//            Log.e(TAG, "onReceive: ", NullPointerException())
//            return
//        }

        val manager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(context)
        var dataUtils = DateUtils()
        var mBitmap: Bitmap? = null
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        var time = dataUtils.longToHoursAndMinutes(TIME)
        val timepart = PartOfDayUtils()
        val part = timepart.getPart(context)
        Log.d("-------------", "onReceive: time: $time")
        Log.d("-------------", "onReceive: TIME: $TIME")


        mBitmap = ContextCompat.getDrawable(
            context,
            R.drawable.ic_ramadn_azhar
        )?.toBitmap()


        builder.setSmallIcon(R.mipmap.ic_logo)
        builder.setShowWhen(true)

        builder.color = ContextCompat.getColor(context, R.color.white)
        builder.setContentTitle("Namoz Vaqti")

        builder.setVibrate(longArrayOf(1000, 500, 1000, 500, 1000, 500))
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        builder.setContentIntent(launchAlarmLandingPage(context))
        builder.setAutoCancel(true)
        builder.priority = Notification.PRIORITY_HIGH
//        builder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
//        builder.setCustomContentView(notificationLayout)
//        builder.setCustomBigContentView(notificationLayoutExpanded)

        when(part){

            0 -> {
                builder.setContentText("Bomdod  --  $time")
                mBitmap = ContextCompat.getDrawable(context, R.drawable.ic_subah_prayer)?.toBitmap()
            }
            2 -> {
                builder.setContentText("Peshin  --  $time")
                mBitmap = ContextCompat.getDrawable(context, R.drawable.ic_zuhar_prayer)?.toBitmap()
            }
            3 -> {
                builder.setContentText("Asr  --  $time")
                mBitmap = ContextCompat.getDrawable(context, R.drawable.ic_ramadn_azhar)?.toBitmap()
            }
            4 -> {
                builder.setContentText("Shom  --  $time")
                mBitmap = ContextCompat.getDrawable(context, R.drawable.ic_maghrib_prayer)?.toBitmap()
            }
            5 -> {
                builder.setContentText("Xufton  --  $time")
                mBitmap = ContextCompat.getDrawable(context, R.drawable.ic_isha_prayer)?.toBitmap()
            }
        }
        Log.d("-------------", "onReceive: $part")
        builder.setLargeIcon(mBitmap)
        manager.notify(NOTIFICATION_ID, builder.build())

        //Reset Alarm manually
        setRemainderAlarm(context)
//        Log.d("-------------", "onReceive: working")

    }



    private fun createNotificationChannel(context: Context) {

        if (VERSION.SDK_INT < VERSION_CODES.O) return

        val mgr: NotificationManager = context.getSystemService(
            NotificationManager::class.java
        ) ?: return

        if (mgr.getNotificationChannel(CHANNEL_NAME) == null) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 500, 1000, 500, 1000, 500)
            channel.setBypassDnd(true)
            mgr.createNotificationChannel(channel)
        }

    }

    private fun launchAlarmLandingPage(ctx: Context): PendingIntent? {
        return PendingIntent.getActivity(
            ctx, NOTIFICATION_ID, launchIntent(ctx), PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun launchIntent(context: Context?): Intent {
        val i = Intent(
            context,
            MainActivity::class.java
        )
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        return i
    }

    companion object {

        fun setAlarm(context: Context) {
            if(NOTIFICATION_ENABLED){
//                Log.d("------------", "setAlarm: working")
                cancelReminderAlarm(context)
                setRemainderAlarm(context)
            }
        }

        private fun setRemainderAlarm(context: Context) {
            //do some code
            val time = TimeHelper(getLocFromPrefs(context)).getAlarmTime()
            TIME = time
            val intent = Intent(context, AlarmReceiver::class.java)
            val bundle = Bundle()
            bundle.putLong(ALARM_KEY, time)
            intent.putExtra(BUNDLE_EXTRA, bundle)
            val pIntent = PendingIntent.getBroadcast(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            var data = Date(time)
//            Log.d("-----------", "setRemainderAlarm: time: ${data.hours}: ${data.minutes}, day: ${data.date}")
            ScheduleAlarm.with(context).schedule(time, pIntent)
//            Log.d("-------------", "setRemainderAlarm: ")

//            val currentTime = System.currentTimeMillis()+2000
//            ScheduleAlarm.with(context).schedule(currentTime, pIntent)
        }

        private fun getLocFromPrefs(context: Context): Location {
            val prefs = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
            val lat = prefs.getString(LATITUDE, "")
            val long = prefs.getString(LONGITUDE, "")

//            Log.d("AlarmReceiver", "getLocFromPrefs: lat: $lat long: $long")

            if (!lat.isNullOrEmpty() && !long.isNullOrEmpty()) {
                val location = Location("")

                location.latitude = lat.toDouble()
                location.longitude = long.toDouble()

                return location
            }

            return DEFAULT_LOCATION

        }

        private fun cancelReminderAlarm(context: Context) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pIntent = PendingIntent.getBroadcast(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            manager.cancel(pIntent)
        }

        fun cancelAlarm(context: Context){
            cancelReminderAlarm(context)
        }

    }

    private class ScheduleAlarm private constructor(
        private val am: AlarmManager
    ) {
        fun schedule(time: Long, pi: PendingIntent?) {
//            Log.d("--------", "schedule: working")
//            Log.d("--------", "schedule: time: $time")
            am.setExact(AlarmManager.RTC_WAKEUP, time, pi)
        }

        companion object {
            fun with(context: Context): ScheduleAlarm {
                val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    ?: throw IllegalStateException("alarm manager not found!")
                return ScheduleAlarm(am)
            }
        }
    }

}

