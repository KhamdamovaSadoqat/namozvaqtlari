package com.example.namozvaqtlari.ui.prayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.TITLE_ID
import com.example.namozvaqtlari.databinding.FragmentPrayerNameBinding
import com.example.namozvaqtlari.model.HomeItem
import com.example.namozvaqtlari.ui.home.AdapterHome

class PrayerNameFragment : Fragment(), AdapterHome.RvItemListener {

    private lateinit var binding: FragmentPrayerNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prayer_name, container, false)
        setRv()
        return binding.root
    }

    override fun onClicked(HomeItem: HomeItem) {
        findNavController().navigate(
            R.id.prayerFragment, bundleOf(TITLE_ID to HomeItem.id)
        )
    }

    private fun setRv() {
        val list = getList()
        val adapter = AdapterHome(this)
        adapter.setData(list)
        binding.rvPrayer.layoutManager = GridLayoutManager(activity, 3)
        binding.rvPrayer.adapter = adapter
    }

    private fun getList(): List<HomeItem> {
        return listOf(
            HomeItem(
                0,
                "Hammasi",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_quran_rehal)
                }!!
            ),
            HomeItem(
                1,
                "Tonggi va kechki ",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_ramadan_month)
                }!!
            ),
            HomeItem(
                2,
                "Namozdagi duolar",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_islamic_prayer)
                }!!
            ),
            HomeItem(
                3,
                "Uy va oilada",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_islamic_friday_prayer)
                }!!
            ),
            HomeItem(
                4,
                "Qiyinchilik kelganda",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_rub_el_hizb)
                }!!
            ),
            HomeItem(
                5,
                "Kasal bo'lganda",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_ambulance)
                }!!
            ),
            HomeItem(
                6,
                "Tabiat",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_ramadn_azhar)
                }!!
            ),
            HomeItem(
                7,
                "Xaj va Umra",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_kaaba_building)
                }!!
            ),
            HomeItem(
                8,
                "Sayohat",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_treasure_map)
                }!!
            ),
            HomeItem(
                9,
                "Sahobalarning duolari",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_islamic_pray)
                }!!
            ),
            HomeItem(
                10,
                "Boshqalar",
                requireContext().let {
                    ContextCompat.getDrawable(it, R.drawable.ic_quran_book)
                }!!
            ),
        )
    }
}