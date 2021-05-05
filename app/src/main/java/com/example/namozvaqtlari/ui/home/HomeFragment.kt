package com.example.namozvaqtlari.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.constants.LATITUDE
import com.example.namozvaqtlari.constants.LONGITUDE
import com.example.namozvaqtlari.constants.MY_PREFS
import com.example.namozvaqtlari.constants.NOTIFICATION_ENABLED
import com.example.namozvaqtlari.databinding.FragmentHomeBinding
import com.example.namozvaqtlari.helper.*
import com.example.namozvaqtlari.model.HomeItem
import com.example.namozvaqtlari.model.Times
import com.example.namozvaqtlari.notification.AlarmReceiver
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Calendar.*
import java.util.concurrent.TimeUnit
import kotlin.math.log


class HomeFragment : Fragment(), AdapterHome.RvItemListener {


    private var TAG = "HomeFragment"

    @SuppressLint("SimpleDateFormat")
    private var timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm")
    private var dateFormat: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private var calendar = Calendar.getInstance(Locale.getDefault())
    private var location: Location? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mChronometer: Chronometer
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var timeHelper: TimeHelper
    private lateinit var prefs: SharedPreferences

    //   " private lateinit var locationHelper2: LocationHelper2
//   " private lateinit var prefs: SharedPreferences
    private lateinit var locationManager: LocationManager

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        prefs = requireActivity().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
        val location = getSavedLocation()
        timeHelper = location?.let { TimeHelper(it) }!!

//        mChronometer = binding.chronometer
//        mChronometer.base = SystemClock.elapsedRealtime()
//        mChronometer.start()
//        mChronometer.setOnChronometerTickListener { setTime() }


        NOTIFICATION_ENABLED = getNotificationStatus()
        Log.d("-------------", "onCreateView: notificationstatus: $NOTIFICATION_ENABLED")

        if (NOTIFICATION_ENABLED) {
            AlarmReceiver.setAlarm(requireContext())
        }

        setRv()
        setIcon()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.linear.setOnClickListener {
            findNavController().navigate(R.id.prayerTimeFragment)
        }
        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
    }

    override fun onClicked(HomeItem: HomeItem) {
        when (HomeItem.id) {
            1 -> findNavController().navigate(
                R.id.tasbeehFragment
            )
            2 -> findNavController().navigate(
                R.id.prayerFragment
            )
            3 -> findNavController().navigate(
                R.id.compassFragment
            )
            4 -> findNavController().navigate(
                R.id.mosqueFragment
            )
            5 -> findNavController().navigate(
                R.id.mediaFragment
            )
            6 -> findNavController().navigate(
                R.id.calendarFragment
            )
        }


    }

    private fun getList(): List<HomeItem> {
        return listOf(

            HomeItem(
                1,
                "Tasbeh",
                requireContext().let { ContextCompat.getDrawable(it, R.drawable.ic_prayer_beads) }!!
            ),
            HomeItem(
                2,
                "Duo",
                requireContext().let { ContextCompat.getDrawable(it, R.drawable.ic_quran_rehal) }!!
            ),
            HomeItem(
                3,
                "Qibla",
                requireContext().let { ContextCompat.getDrawable(it, R.drawable.ic_compass_ui) }!!
            ),
            HomeItem(
                4,
                "Masjid",
                requireContext().let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_ramadan_crescent_moon
                    )
                }!!
            ),
            HomeItem(
                5,
                "Ma'ruza",
                requireContext().let { ContextCompat.getDrawable(it, R.drawable.ic_cassette) }!!
            ),
            HomeItem(
                6,
                "Kalendar",
                requireContext().let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_calendar_ultrathin
                    )
                }!!
            )
        )
    }

    private fun setRv() {
        val list = getList()
        val adapter = AdapterHome(this)
        adapter.setData(list)
        binding.rvMain.layoutManager = GridLayoutManager(activity, 3)
        binding.rvMain.adapter = adapter
    }

    fun getNotificationStatus(): Boolean {
        val prefs = requireContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
        return prefs.getBoolean("NOTIFICATION_ENABLED", true)
    }

    fun getIcon(): Int {
        var exactTime = timeHelper.getAlarmTime()
        var allTimes = timeHelper.getAllTimes()

        val date = Calendar.getInstance()
        date.timeInMillis = exactTime

        val timeString = "${date.get(Calendar.HOUR_OF_DAY)}:${date.get(Calendar.MINUTE)}:00"
        Log.d("-------------", "getIcon: exactTime: $exactTime")
        Log.d("-------------", "getIcon: timeString: $timeString")
        Log.d("-------------", "getIcon: allTime: $allTimes")

        if (allTimes.fajr == timeString) return 0
        else if (allTimes.thuhr == timeString) return 2
        else if (allTimes.assr == timeString) return 3
        else if (allTimes.maghrib == timeString) return 4
        else if (allTimes.ishaa == timeString) return 5

        return 0
    }

    fun setIcon() {
        getIcon()
        var icon = getIcon()
        Log.d("-------------", "onResume: icon $icon")

        when (icon) {
            0 -> {
                binding.prayerIconImg.setImageResource(R.drawable.ic_subah_prayer)
                binding.prayerTimeName.text = "Bomdod"
            }
            2 -> {
                binding.prayerIconImg.setImageResource(R.drawable.ic_zuhar_prayer)
                binding.prayerTimeName.text = "Peshin"
            }
            3 -> {
                binding.prayerIconImg.setImageResource(R.drawable.ic_ramadn_azhar)
                binding.prayerTimeName.text = "Asr"
            }
            4 -> {
                binding.prayerIconImg.setImageResource(R.drawable.ic_maghrib_prayer)
                binding.prayerTimeName.text = "Shom"
            }
            5 -> {
                binding.prayerIconImg.setImageResource(R.drawable.ic_isha_prayer)
                binding.prayerTimeName.text = "Xufton"
            }
        }
    }

    private fun setCalendarTime(time: Times) {
        val fajr = time.fajr.split(":")
        val shuruq = time.shuruq.split(":")
        val thuhr = time.thuhr.split(":")
        val assr = time.assr.split(":")
        val maghrib = time.maghrib.split(":")
        val ishaa = time.ishaa.split(":")

        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(HOUR_OF_DAY, fajr[0].toInt())
        calendar.set(MINUTE, fajr[1].toInt())
        calendar.set(SECOND, 0)
        calendar.set(MILLISECOND, 0)
    }

    private fun getSavedLocation(): Location? {
        val latitudeSt = prefs.getString(LATITUDE, null)
        val longtitudeSt = prefs.getString(LONGITUDE, null)
        var location: Location? = null
        if (latitudeSt != null && longtitudeSt != null) {
            location = Location("")
            location.latitude = latitudeSt.toDouble()
            location.longitude = longtitudeSt.toDouble()
        }
        Log.d(TAG, "getSavedLocation: ${location?.latitude}")
        return location
    }

//    private fun setTime() {
//        val curentTime = System.currentTimeMillis()
//        binding.chronometer.text = timeFormat.format(curentTime)
//        binding.date.text = dateFormat.format(curentTime)
//    }

}