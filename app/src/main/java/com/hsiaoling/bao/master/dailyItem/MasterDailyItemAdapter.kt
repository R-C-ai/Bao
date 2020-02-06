package com.hsiaoling.bao.master.dailyItem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hsiaoling.bao.data.BaoService
import com.hsiaoling.bao.databinding.ItemTimeReserveBinding

class MasterDailyItemAdapter(val onClickListener : OnClickListener) :
    ListAdapter<BaoService,MasterDailyItemAdapter.BaoServiceViewHolder>(DiffCallback){

    class BaoServiceViewHolder(private var binding : ItemTimeReserveBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(baoService: BaoService){
            binding.baoService = baoService
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback:DiffUtil.ItemCallback<BaoService>(){
        override fun areItemsTheSame(oldItem: BaoService, newItem: BaoService): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BaoService, newItem: BaoService): Boolean {
            return oldItem.customerSort == newItem.customerSort
        }
    }

    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int): BaoServiceViewHolder{
        return BaoServiceViewHolder(ItemTimeReserveBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BaoServiceViewHolder, position: Int) {
        val baoService = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(baoService)
        }
        holder.bind(baoService)
    }

    class OnClickListener (val clickListener: (baoService:BaoService) -> Unit) {
        fun onClick(baoService: BaoService) = clickListener(baoService)
    }

}