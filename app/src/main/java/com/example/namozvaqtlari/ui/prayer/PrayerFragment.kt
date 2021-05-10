package com.example.namozvaqtlari.ui.prayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.namozvaqtlari.PRAYER_ID
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.TITLE_ID
import com.example.namozvaqtlari.databinding.FragmentPrayerBinding
import com.example.namozvaqtlari.model.PrayerText
import com.google.gson.Gson

class PrayerFragment : Fragment(), AdapterPrayer.RvItemListener {

    private lateinit var binding: FragmentPrayerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prayer, container, false)
        setRv()
        return binding.root
    }

    private fun setRv() {
        val oldList = DataPrayer().getList()
        val newList = separateListByTitleId(oldList)
        val title = arguments?.getInt(TITLE_ID)
        val adapter = title?.let { AdapterPrayer(this, it) }
        if(title == 0) adapter?.setData(oldList)
        else adapter?.setData(newList)
        binding.rvPrayerName.adapter = adapter
    }

    private fun separateListByTitleId(list: List<PrayerText>): ArrayList<PrayerText> {
        val changedList = arrayListOf<PrayerText>()
        val title = arguments?.getInt(TITLE_ID)
        for (i in list.indices) {
            if (list[i].titleId == title){
                changedList.add(list[i])
            }
        }
        return changedList
    }

    override fun onClicked(prayerText: PrayerText) {

        val jsonString = Gson().toJson(prayerText)
        findNavController().navigate(
                R.id.prayerList, bundleOf(PRAYER_ID to jsonString)
            )
    }
}


