package com.example.namozvaqtlari.ui.prayer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.databinding.ItemPrayerBinding
import com.example.namozvaqtlari.model.PrayerText

class AdapterPrayer(private val listener: RvItemListener, private val title: Int) :
    RecyclerView.Adapter<AdapterPrayer.VH>() {

    interface RvItemListener {
        fun onClicked(prayerText: PrayerText)
    }

    private var list = listOf<PrayerText>()

    fun setData(list: List<PrayerText>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemPrayerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_prayer,
            parent,
            false
        )
        return VH(binding, parent.context)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onClicked(list[position])
        }
        holder.onBind(list[position], title)
    }

    override fun getItemCount(): Int = list.size

    class VH(private val binding: ItemPrayerBinding, private val context: Context?) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(prayerText: PrayerText, title: Int) {
            if(title == 0) binding.nameId.text = prayerText.id.toString()
            else binding.nameId.text = prayerText.idInner.toString()
            binding.prayerName = prayerText


        }

    }
}