package com.example.namozvaqtlari.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.constants.FACEBOOK_PAGE_ID
import com.example.namozvaqtlari.constants.FACEBOOK_URL
import com.example.namozvaqtlari.databinding.FragmentAboutUsBinding


class AboutUsFragment : Fragment() {

    lateinit var binding: FragmentAboutUsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false)

        binding.ibTelegram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/idroktalim"))
            startActivity(intent)
        }
        binding.ibFacebook.setOnClickListener {
            val packageManager = requireContext().packageManager
            var urlFb: String = ""
            urlFb = try {
                val versionCode =
                    packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
                if (versionCode >= 3002850) { //newer versions of fb app
                    "fb://facewebmodal/f?href=$FACEBOOK_URL"
                } else { //older versions of fb app
                    "fb://page/$FACEBOOK_PAGE_ID"
                }
            } catch (e: PackageManager.NameNotFoundException) {
                FACEBOOK_URL //normal web url
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlFb))
            startActivity(intent)
        }

        binding.ibInstagram.setOnClickListener {
            val uri = Uri.parse("https://instagram.com/idrok.talim?igshid=ib0s2rudhxxo")
            val likeIng = Intent(Intent.ACTION_VIEW, uri)

//            likeIng.setPackage("com.instagram.android")

            try {
                startActivity(likeIng)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com")
                    )
                )
            }
        }
        // Inflate the layout for this fragment
        return binding.root

    }


}