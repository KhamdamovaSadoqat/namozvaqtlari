package com.example.namozvaqtlari.ui.prayerTime

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import android.content.res.ColorStateList


class PrayerTimeFragment : Fragment() {
    private val TAG = "PrayerTimeFragment"

    private lateinit var binding: FragmentPrayerTimeBinding
    private lateinit var viewModel: PrayerTimeViewModel
    private lateinit var locHelper: LocationHelper
    private lateinit var prefs: SharedPreferences
    private lateinit var timeHelper: TimeHelper
    private val date = DateUtils()

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
//        Log.d("-------------", "onCreateView: location: $location")
        location?.let { loc ->
//            Log.d("------------", "onCreateView: ${loc.latitude}, ${loc.longitude}")
            val time = viewModel.getDate(loc)
//            Log.d("------------", "onCreateView: ${time}")
            setTime(time)
        }

        timeHelper = location?.let { TimeHelper(it) }!!

//        Log.d("-------------", "onCreateView: getAlarmTime: ${timeHelper.getAlarmTime()}")
//        Log.d("-------------", "onCreateView: currentTimeM: ${System.currentTimeMillis()}")
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getIcon()
        val allTimes = timeHelper.getAllTimes()
        val icon = getIcon()

        binding.constrainLayout
//        Log.d("-------------", "onResume: icon $icon")

        when (icon) {
            0 -> {
                binding.constrainLayout.setBackgroundResource(R.drawable.bg_5)
                binding.prayerTimeIcon.setImageResource(R.drawable.ic_subah_prayer)
                binding.prayerTime.text = date.timeToTextWithHourAndMinutes(allTimes.fajr)
                binding.prayerTimeName.text = "Bomdod"

                binding.linear.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                binding.prayerTimeName.setTextColor(Color.WHITE)
                binding.prayerTime.setTextColor(Color.WHITE)

                binding.prayerTimeIcon.imageTintList = ColorStateList.valueOf(Color.WHITE)
                binding.iconAssr.imageTintList = ColorStateList.valueOf(Color.WHITE)
                binding.iconFajr.imageTintList = ColorStateList.valueOf(Color.WHITE)
                binding.iconIshaa.imageTintList = ColorStateList.valueOf(Color.WHITE)
                binding.iconMaghrib.imageTintList = ColorStateList.valueOf(Color.WHITE)
                binding.iconShuruq.imageTintList = ColorStateList.valueOf(Color.WHITE)
                binding.iconThuhr.imageTintList = ColorStateList.valueOf(Color.WHITE)

                binding.timeAssr.setTextColor(Color.WHITE)
                binding.timeFajr.setTextColor(Color.WHITE)
                binding.timeIshaa.setTextColor(Color.WHITE)
                binding.timeMaghrib.setTextColor(Color.WHITE)
                binding.timeShuruq.setTextColor(Color.WHITE)
                binding.timeThuhr.setTextColor(Color.WHITE)

                binding.nameAssr.setTextColor(Color.WHITE)
                binding.nameFajr.setTextColor(Color.WHITE)
                binding.nameIshaa.setTextColor(Color.WHITE)
                binding.nameMaghrib.setTextColor(Color.WHITE)
                binding.nameShuruq.setTextColor(Color.WHITE)
                binding.nameThuhr.setTextColor(Color.WHITE)

            }
            2 -> {
                binding.constrainLayout.setBackgroundResource(R.drawable.bg_1)
                binding.prayerTimeIcon.setImageResource(R.drawable.ic_zuhar_prayer)
                binding.prayerTime.text = date.timeToTextWithHourAndMinutes(allTimes.thuhr)
                binding.prayerTimeName.text = "Peshin"
            }
            3 -> {
                binding.constrainLayout.setBackgroundResource(R.drawable.bg_2)
                binding.prayerTimeIcon.setImageResource(R.drawable.ic_ramadn_azhar)
                binding.prayerTime.text = date.timeToTextWithHourAndMinutes(allTimes.assr)
                binding.prayerTimeName.text = "Asr"

            }
            4 -> {
                binding.constrainLayout.setBackgroundResource(R.drawable.bg_3)
                binding.prayerTimeIcon.setImageResource(R.drawable.ic_maghrib_prayer)
                binding.prayerTime.text = date.timeToTextWithHourAndMinutes(allTimes.maghrib)
                binding.prayerTimeName.text = "Shom"
            }
            5 -> {
                binding.constrainLayout.setBackgroundResource(R.drawable.bg_4)
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



        binding.timeFajr.text = date.timeToTextWithHourAndMinutes("${fajr[0]}:${fajr[1]}")
        binding.timeShuruq.text = date.timeToTextWithHourAndMinutes("${shuruq[0]}:${shuruq[1]}")
        binding.timeThuhr.text = date.timeToTextWithHourAndMinutes("${thuhr[0]}:${thuhr[1]}")
        binding.timeAssr.text = date.timeToTextWithHourAndMinutes("${assr[0]}:${assr[1]}")
        binding.timeMaghrib.text = date.timeToTextWithHourAndMinutes("${maghrib[0]}:${maghrib[1]}")
        binding.timeIshaa.text = date.timeToTextWithHourAndMinutes("${ishaa[0]}:${ishaa[1]}")
    }

    private fun getSavedLocation(): Location? {
        val latitudeSt = prefs.getString(LATITUDE, null)
        val longtitudeSt = prefs.getString(LONGITUDE, null)
        var location: Location? = null
        location = Location("")
        if (latitudeSt != null && longtitudeSt != null) {
            location.latitude = latitudeSt.toDouble()
            location.longitude = longtitudeSt.toDouble()
        } else{
            location.latitude = 41.311081
            location.longitude = 69.240562
        }
//        Log.d(TAG, "getSavedLocation: ${location.latitude}")
        return location
    }

    private fun getIcon(): Int {
        val exactTime = timeHelper.getAlarmTime()
        val allTimes = timeHelper.getAllTimes()
        val dataUtils = DateUtils()
        val date = Calendar.getInstance()

        date.timeInMillis = exactTime
        val fullTime = dataUtils.timeToTextWithoutMinus(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), 0)

//        Log.d("-------------", "getIcon: exactTime: $exactTime")
//        Log.d("-------------", "getIcon: timeString: $fullTime")
//        Log.d("-------------", "getIcon: allTime: $allTimes")

        return when {
            allTimes.fajr == fullTime -> 0
            allTimes.thuhr == fullTime -> 2
            allTimes.assr == fullTime -> 3
            allTimes.maghrib == fullTime -> 4
            allTimes.ishaa == fullTime -> 5
            else -> 0
        }

    }
}