package com.hsiaoling.bao.master.dailyItem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hsiaoling.bao.data.Schedule
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.ItemScheduleBinding


class MasterDailyItemAdapter(val onClickListener : OnClickListener) :
    ListAdapter<Service,MasterDailyItemAdapter.ServiceViewHolder>(DiffCallback){

    class ServiceViewHolder(private var binding : ItemScheduleBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(service: Service){
            binding.service = service
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback:DiffUtil.ItemCallback<Service>(){
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.schedule_sort == newItem.schedule_sort
        }
    }

    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int): ServiceViewHolder{
        return ServiceViewHolder(ItemScheduleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(service)
        }
        holder.bind(service)
    }

    class OnClickListener (val clickListener: (service:Service) -> Unit) {
        fun onClick(service: Service) = clickListener(service)
    }

}