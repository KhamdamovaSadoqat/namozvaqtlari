
package com.example.namozvaqtlari.notification

import android.app.*
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.media.RingtoneManager
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.constants.*
import com.example.namozvaqtlari.helper.TimeHelper
import com.example.namozvaqtlari.ui.mainActivity.MainActivity
import com.example.namozvaqtlari.utils.DateUtils
import com.example.namozvaqtlari.utils.PartOfDayUtils
import java.util.*


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
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        var mBitmap: Bitmap? = null
        val timeHelper = TimeHelper(getLocFromPrefs(context))
        createNotificationChannel(context)


        val timeFlag = getDate(context)
        val time = timeHelper.getAllTimes()
        val dataUtil = DateUtils()
//        val timepart = PartOfDayUtils()
//        val part = timepart.getPartNotification(context)
        Log.d("-------------", "onReceive: time: $time")
//        Log.d("-------------", "onReceive: part: $part")

        //        return when(timeFlag){
//            //fajr=3:29:00, shuruq=5:01:00, thuhr=12:20:00, assr=17:26:00, maghrib=19:38:00, ishaa=21:10:00
//            0 ->
//            2 ->
//            3 ->
//            4 ->
//            5 ->
//            else -> dataUtil.timeToTextWithHourAndMinutes(time.fajr)
//        }

        mBitmap = ContextCompat.getDrawable(
            context,
            R.mipmap.ic_logo_foreground
        )?.toBitmap()



        builder.setSmallIcon(R.mipmap.ic_logo_foreground)
        builder.setLargeIcon(mBitmap)
        builder.setShowWhen(true)
        builder.color = ContextCompat.getColor(context, R.color.white)
        builder.setContentTitle("Namoz Vaqti")
        builder.setChannelId(CHANNEL_ID)
        builder.setVibrate(longArrayOf(1000, 500, 1000, 500, 1000, 500))
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        builder.setContentIntent(launchAlarmLandingPage(context))
        builder.setAutoCancel(true)
        builder.priority = Notification.PRIORITY_HIGH
//        builder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
//        builder.setCustomContentView(notificationLayout)
//        builder.setCustomBigContentView(notificationLayoutExpanded)

        when(timeFlag){
            0 -> {
                builder.setContentText("Bomdod  --  ${dataUtil.timeToTextWithHourAndMinutes(time.fajr)}")
                mBitmap = ContextCompat.getDrawable(context, R.drawable.ic_subah_prayer)?.toBitmap()
            }
            2 -> {
                builder.setContentText("Peshin  --  ${dataUtil.timeToTextWithHourAndMinutes(time.thuhr)}")
                mBitmap = ContextCompat.getDrawable(context, R.drawable.ic_zuhar_prayer)?.toBitmap()
            }
            3 -> {
                builder.setContentText("Asr  --  ${dataUtil.timeToTextWithHourAndMinutes(time.assr)}")
                mBitmap = ContextCompat.getDrawable(context, R.drawable.ic_ramadn_azhar)?.toBitmap()
            }
            4 -> {
                builder.setContentText("Shom  --  ${dataUtil.timeToTextWithHourAndMinutes(time.maghrib)}")
                mBitmap = ContextCompat.getDrawable(context, R.drawable.ic_maghrib_prayer)?.toBitmap()
            }
            5 -> {
                builder.setContentText("Xufton  --  ${dataUtil.timeToTextWithHourAndMinutes(time.ishaa)}")
                mBitmap = ContextCompat.getDrawable(context, R.drawable.ic_isha_prayer)?.toBitmap()
            }
        }
//        Log.d("-------------", "onReceive: $part")
        builder.setLargeIcon(mBitmap)
        manager.notify(NOTIFICATION_ID, builder.build())

        //Reset Alarm manually
        setRemainderAlarm(context)
//        Log.d("-------------", "onReceive: working")

    }

    private fun getDate(context: Context): Int{
        val timeHelper = TimeHelper(getLocFromPrefs(context))
        val timeFlag = timeHelper.getAlarmTimeOneBefore()

        Log.d("-------------", "getDate: timflag: $timeFlag")
        return timeFlag
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
            Log.d("-------------", "setRemainderAlarm: time: $time")
            Log.d("-------------", "setRemainderAlarm: TIME: $TIME")
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
//            var data = Date(time)
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

