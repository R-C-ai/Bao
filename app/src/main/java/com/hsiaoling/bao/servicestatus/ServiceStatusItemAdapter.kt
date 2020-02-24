package com.hsiaoling.bao.servicestatus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hsiaoling.bao.data.Schedule
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.ItemStatusBinding


class ServiceStatusItemAdapter(val onClickListener : OnClickListener) :
    ListAdapter<Service,ServiceStatusItemAdapter.ServiceViewHolder>(DiffCallback){

    class ServiceViewHolder(private var binding : ItemStatusBinding) :
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
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int): ServiceViewHolder{
        return ServiceViewHolder(ItemStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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