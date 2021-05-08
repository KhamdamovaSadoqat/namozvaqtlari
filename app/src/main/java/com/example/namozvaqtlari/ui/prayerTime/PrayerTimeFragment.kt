package com.example.namozvaqtlari.ui.prayerTime

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.constants.LATITUDE
import com.example.namozvaqtlari.constants.LONGITUDE
import com.example.namozvaqtlari.constants.MY_PREFS
import com.example.namozvaqtlari.databinding.FragmentPrayerTimeBinding
import com.example.namozvaqtlari.helper.LocationHelper
import com.example.namozvaqtlari.helper.TimeHelper
import com.example.namozvaqtlari.model.Times
import com.example.namozvaqtlari.utils.DateUtils
import java.util.*
import kotlin.math.log

class PrayerTimeFragment : Fragment() {
    private val TAG = "PrayerTimeFragment"

    private lateinit var binding: FragmentPrayerTimeBinding
    private lateinit var viewModel: PrayerTimeViewModel
    private lateinit var locHelper: LocationHelper
    private lateinit var prefs: SharedPreferences
    private lateinit var timeHelper: TimeHelper

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prayer_time, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(PrayerTimeViewModel::class.java)
        locHelper = LocationHelper(requireActivity())
        prefs = requireActivity().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
        val location = getSavedLocation()
        location?.let { loc ->
            Log.d("------------", "onCreateView: ${loc.latitude}, ${loc.longitude}")
            val time = viewModel.getDate(loc)
            Log.d("------------", "onCreateView: ${time}")
            setTime(time)
        }

        timeHelper = location?.let { TimeHelper(it) }!!

        Log.d("-------------", "onCreateView: getAlarmTime: ${timeHelper.getAlarmTime()}")
        Log.d("-------------", "onCreateView: currentTimeM: ${System.currentTimeMillis()}")
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getIcon()
        val date = DateUtils()
        val allTimes = timeHelper.getAllTimes()
        val icon = getIcon()
        Log.d("-------------", "onResume: icon $icon")

        when (icon) {
            0 -> {
                binding.constrainLayout.setBackgroundResource(R.drawable.bg_1)
                binding.prayerTimeIcon.setImageResource(R.drawable.ic_subah_prayer)
                binding.prayerTime.text = date.timeToTextWithHourAndMinutes(allTimes.fajr)
                binding.prayerTimeName.text = "Bomdod"
            }
            2 -> {
                binding.constrainLayout.setBackgroundResource(R.drawable.bg_2)
                binding.prayerTimeIcon.setImageResource(R.drawable.ic_zuhar_prayer)
                binding.prayerTime.text = date.timeToTextWithHourAndMinutes(allTimes.thuhr)
                binding.prayerTimeName.text = "Peshin"
            }
            3 -> {
                binding.constrainLayout.setBackgroundResource(R.drawable.bg_3)
                binding.prayerTimeIcon.setImageResource(R.drawable.ic_ramadn_azhar)
                binding.prayerTime.text = date.timeToTextWithHourAndMinutes(allTimes.assr)
                binding.prayerTimeName.text = "Asr"
            }
            4 -> {
                binding.constrainLayout.setBackgroundResource(R.drawable.bg_4)
                binding.prayerTimeIcon.setImageResource(R.drawable.ic_maghrib_prayer)
                binding.prayerTime.text = date.timeToTextWithHourAndMinutes(allTimes.maghrib)
                binding.prayerTimeName.text = "Shom"
            }
            5 -> {
                binding.constrainLayout.setBackgroundResource(R.drawable.bg_5)
                binding.prayerTimeIcon.setImageResource(R.drawable.ic_isha_prayer)
                binding.prayerTime.text = date.timeToTextWithHourAndMinutes(allTimes.ishaa)
                binding.prayerTimeName.text = "Xufton"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTime(time: Times) {
        val fajr = time.fajr.split(":")
        val shuruq = time.shuruq.split(":")
        val thuhr = time.thuhr.split(":")
        val assr = time.assr.split(":")
        val maghrib = time.maghrib.split(":")
        val ishaa = time.ishaa.split(":")
        Log.d(TAG, "time date: $time")

        binding.timeFajr.text = "${fajr[0]}:${fajr[1]}"
        binding.timeShuruq.text = "${shuruq[0]}:${shuruq[1]}"
        binding.timeThuhr.text = "${thuhr[0]}:${thuhr[1]}"
        binding.timeAssr.text = "${assr[0]}:${assr[1]}"
        binding.timeMaghrib.text = "${maghrib[0]}:${maghrib[1]}"
        binding.timeIshaa.text = "${ishaa[0]}:${ishaa[1]}"
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
}