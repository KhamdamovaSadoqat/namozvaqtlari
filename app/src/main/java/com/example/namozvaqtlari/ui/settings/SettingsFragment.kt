package com.example.namozvaqtlari.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.constants.MY_PREFS
import com.example.namozvaqtlari.constants.NOTIFICATION_ENABLED
import com.example.namozvaqtlari.databinding.FragmentSettingsBinding
import com.example.namozvaqtlari.notification.AlarmReceiver

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.switchCompatNotification.isChecked = NOTIFICATION_ENABLED
        binding.switchCompatNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            NOTIFICATION_ENABLED = isChecked
            setNotificationToSharedPref(isChecked)
            if (!isChecked) AlarmReceiver.cancelAlarm(requireContext())
        }

        binding.secondConstraint.setOnClickListener {

        }
        binding.thirdConstraint.setOnClickListener {

        }
    }

    private fun setNotificationToSharedPref(status: Boolean) {
        val prefs = requireContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean("NOTIFICATION_ENABLED", status)
            .apply()
    }

}