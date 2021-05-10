package com.example.namozvaqtlari.ui.prayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.namozvaqtlari.PRAYER_ID
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.databinding.FragmentPrayerListBinding
import com.example.namozvaqtlari.model.PrayerText
import com.google.gson.Gson

class PrayerList :Fragment(){
    private lateinit var binding: FragmentPrayerListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prayer_list, container, false)
        val list= DataPrayer().getList()
        val _u = arguments?.getString(PRAYER_ID)
        val prayerText=Gson().fromJson(_u, PrayerText::class.java)
        binding.prayer=prayerText
        return binding.root
    }
}