package com.hsiaoling.bao

import android.icu.util.Calendar
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.ext.toDisplayFormat
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

@BindingAdapter("timeToDisplayFormat")
fun bindDisplayFormatTime(textView: TextView, time: Long?) {

    textView.text = time?.toDisplayFormat()
}

@BindingAdapter("itemScheduleSort")
fun setScheduleSort(imageView: ImageView, position: Int) {

    when {
        position == 0 -> {
            imageView.setImageResource(R.drawable.one)
        }
        position == 1 -> {
            imageView.setImageResource(R.drawable.one)
        }
        position == 2 -> {
            imageView.setImageResource(R.drawable.one)
        }
        position == 3 -> {
            imageView.setImageResource(R.drawable.one)
        }
        position == 4 -> {
            imageView.setImageResource(R.drawable.one)
        }
        position == 5 -> {
            imageView.setImageResource(R.drawable.one)
        }
        position == 6 -> {
            imageView.setImageResource(R.drawable.one)
        }
        position == 7 -> {
            imageView.setImageResource(R.drawable.one)
        }
    }
}

@BindingAdapter("status")
fun scheduleStatus(textView: TextView,status:Int){

    when {
        status == 0 -> {
            textView.text = BaoApplication.instance.getString(R.string.status0)
        }
        status == 1 -> {
            textView.text = BaoApplication.instance.getString(R.string.status1)
        }
        status == 2 -> {
            textView.text = BaoApplication.instance.getString(R.string.status2)
        }
    }
}


