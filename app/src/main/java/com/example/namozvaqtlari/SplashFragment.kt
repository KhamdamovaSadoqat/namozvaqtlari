package com.example.namozvaqtlari

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.namozvaqtlari.constants.FLAG
import com.example.namozvaqtlari.databinding.FragmentSplashBinding
import java.util.*

class SplashFragment : Fragment() {

    private lateinit var timer: Timer
    private lateinit var binding: FragmentSplashBinding
    private  var random: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        random = (0..1).random()
        if(random == 0) binding.animationView.setAnimation(R.raw.prayer_woman)
        else binding.animationView.setAnimation(R.raw.prayer_man)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        waitAndOpenOtherFragment()
    }

    private fun waitAndOpenOtherFragment() {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, 1300)
    }
}
