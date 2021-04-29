package com.example.namozvaqtlari.ui.calendar


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.databinding.FragmentCalendarBinding


class CalendarFragment : Fragment() {
    private lateinit var binding:FragmentCalendarBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_calendar, container, false)
        binding.btnCalendar.setOnClickListener {
            if (binding.btnCalendar.text=="Hijri")
            {  binding.calendarGregorian.visibility=View.GONE
                binding.calendarHijri.visibility=View.VISIBLE
                binding.btnCalendar.text="Gregorian"
                binding.calendarText.text="Hijri"
            }
            else{
                binding.calendarGregorian.visibility=View.VISIBLE
                binding.calendarHijri.visibility=View.GONE
                binding.btnCalendar.text="Hijri"
                binding.calendarText.text="Gregorian"
            }
        }

        return binding.root
    }
}