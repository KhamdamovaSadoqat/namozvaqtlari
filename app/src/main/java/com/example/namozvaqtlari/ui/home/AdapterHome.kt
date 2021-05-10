package com.example.namozvaqtlari.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.namozvaqtlari.R
import com.example.namozvaqtlari.databinding.ItemRvBinding
import com.example.namozvaqtlari.model.HomeItem

class AdapterHome(private val listener:RvItemListener):RecyclerView.Adapter<AdapterHome.VH>() {
    private var list= listOf<HomeItem>()

    interface RvItemListener{
        fun onClicked(HomeItem: HomeItem)
    }

    fun setData(list: List<HomeItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding:ItemRvBinding = DataBindingUtil.inflate(inflater, R.layout.item_rv,parent,false)
        return VH(binding)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onClicked(list[position])
        }
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size


    class VH(private val binding:ItemRvBinding) :RecyclerView.ViewHolder(binding.root){
        fun onBind(HomeItem: HomeItem){
            with(binding) {
                this.tvItem.text =HomeItem.text
                this.ivMain.setImageDrawable(HomeItem.image)

            }
        }

    }
}