package com.example.namozvaqtlari.ui.youTube

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.databinding.FragmentAudioBinding


class YouTubeFragment : Fragment() {

    private lateinit var binding: FragmentAudioBinding

    private var TAG="AudioFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audio, container, false)

        binding.btnPlay.setOnClickListener {
           launchZoomUrl()

            Log.d(TAG, "onCreateView: $it")
        }

        return binding.root
    }
    private fun launchZoomUrl() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=SMNHZR1u6KQ"))
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent)
        }
    }


}