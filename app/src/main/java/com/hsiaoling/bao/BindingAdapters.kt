package com.hsiaoling.bao

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hsiaoling.bao.data.BaoService
import com.hsiaoling.bao.data.Schedule
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.master.dailyItem.MasterDailyItemAdapter


@BindingAdapter("schedules")
fun bindRecyclerViewWithSchedule(recyclerView: RecyclerView, schedules: List<Service>?) {
    schedules?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is MasterDailyItemAdapter -> submitList(it)
            }
        }
    }
}

//@BindingAdapter("schedule_sort")
//fun showSchedule_sort(imageView: ImageView,)