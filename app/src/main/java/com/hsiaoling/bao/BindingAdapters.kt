package com.hsiaoling.bao

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.ext.toDisplayFormat
import com.hsiaoling.bao.master.dailyItem.MasterDailyItemAdapter
import com.hsiaoling.bao.salesaomunt.UserType


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


// set time display when the schedule service has been reserve
@BindingAdapter("timeToDisplayFormat")
fun bindDisplayFormatTime(textView: TextView, time: Long?) {
        Log.i("Hsiao","bindDisplayFormatTime, time=$time")
    time?.let {
        if (it<=0L){
            textView.visibility = View.INVISIBLE
        }else{
            textView.visibility = View.VISIBLE
            Log.i("HsiaoTime","${it.toDisplayFormat()}")
            textView.text = it.toDisplayFormat()
        }
    }

}

// set descriptuion for cumRev
@BindingAdapter("revenue")
fun bindRev(textView: TextView, revenue: Long?) {
    revenue?.let { textView.text = BaoApplication.instance.getString(R.string.cum_rev, it) }
}

// set NT$ for Price
@BindingAdapter("statusPrice")
fun bindPrice(textView: TextView, price: Int?) {
    price?.let { textView.text = BaoApplication.instance.getString(R.string.nt_dollars_, it) }
}


// set time text for status update
@BindingAdapter("updateTime", "status")
fun updateTime(textView: TextView, time: Long?,status:Int) {
    Log.i("Hsiao", "doneTime, time=$time")

    when {
        status == 1 ->    textView.visibility = View.GONE
        else ->            textView.visibility = View.VISIBLE
    }
    time?.let {
        Log.i("HsiaoTime","${it.toDisplayFormat()}")
        textView.text = it.toDisplayFormat()
    }
}


@BindingAdapter("itemScheduleSort")
fun setScheduleSort(imageView: ImageView, sort: Int) {
    val drawable1 = when {
        sort == 0 -> {
            R.drawable.one
        }
        sort == 1 -> {
            R.drawable.two
        }
        sort == 2 -> {
            R.drawable.three
        }
        sort == 3 -> {
            R.drawable.four
        }
        sort == 4 -> {
            R.drawable.five
        }
        sort == 5 -> {
            R.drawable.six
        }
        sort == 6 -> {
            R.drawable.seven
        }
        sort == 7 -> {
            R.drawable.eight
        }
        else ->{
            R.drawable.eight
        }
    }


}

@BindingAdapter("itemScheduleTime")
fun itemScheduleTime(textView:TextView, sort: Int) {
    textView.text =
    when( sort) {

        0 -> {
             BaoApplication.instance.getString(R.string.time0)
        }
         1 -> {
             BaoApplication.instance.getString(R.string.time1)
        }
          2 -> {
             BaoApplication.instance.getString(R.string.time2)
        }
          3 -> {
             BaoApplication.instance.getString(R.string.time3)
        }
          4 -> {
             BaoApplication.instance.getString(R.string.time4)
        }
          5 -> {
             BaoApplication.instance.getString(R.string.time5)
        }
          6 -> {
             BaoApplication.instance.getString(R.string.time6)
        }
          7 -> {
             BaoApplication.instance.getString(R.string.time7)
        }
        else-> {
            "nothing"
        }
    }
}


// set image when master update job status
@BindingAdapter("masterJobstatus")
fun masterJobstatus(imageView: ImageView,status:Int){
    when {
        status == 1 -> {imageView.setImageResource(R.drawable.go1)}
        status == 2 -> {imageView.setImageResource(R.drawable.go2)}
    }
}

// set text when master update job status
@BindingAdapter("masterJobstatusText")
fun masterJobstatusText(textView: TextView,status:Int){
    when {
        status == 1 -> {textView.text ="開始包膜囉"}
        status == 2 -> {textView.text ="包膜完成囉"}
    }
}

// set image when salesman update service reserve status
@BindingAdapter("salesmanJobstatus")
fun salesmanJobstatus(imageView: ImageView,status:Int){

    when {
        status == 1 -> {imageView.setImageResource(R.drawable.no0)}
        status == 3 -> {imageView.setImageResource(R.drawable.ok0)}
    }
}


// set text when master update job status
@BindingAdapter("salesmanJobstatusText")
fun salesmanJobstatusText(textView: TextView,status:Int){
    when {
        status == 1 -> {textView.text ="確認取消囉"}
        status == 3 -> {textView.text ="確認完成囉"}
    }
}




@BindingAdapter("status")
fun status(textView: TextView,status:Int){

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
        status == 3 -> {
            textView.text = BaoApplication.instance.getString(R.string.status3)
        }
        status == 4 -> {
            textView.text = BaoApplication.instance.getString(R.string.status4)
        }

        status == 5 -> {
            textView.text = BaoApplication.instance.getString(R.string.status5)
        }

    }
}

@BindingAdapter("statusBackground")
fun statusBackground(imageView: ImageView, status: Int) {

    when {
        status == 0 -> {
            imageView.setImageResource(R.drawable.bg_status0)
        }
        status == 1 -> {
            imageView.setImageResource(R.drawable.bg_status1)
        }
        status == 2 -> {
            imageView.setImageResource(R.drawable.bg_status2)
        }
        status == 3 -> {
            imageView.setImageResource(R.drawable.bg_status3)
        }
        status == 4 -> {
            imageView.setImageResource(R.drawable.bg_status4)
        }
        status == 5 -> {
            imageView.setImageResource(R.drawable.bg_status5)
        }

    }
}

@BindingAdapter("statusPage")
fun statusPage(textView: TextView,status:Int){

    when {

        status == 1 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_page1)
        }
        status == 2 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_page2)
        }
        status == 3 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_page3)
        }
        status == 4 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_page4)
        }
        status == 5 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_page5)
        }

    }
}

@BindingAdapter("statusImage")
fun statusImage(imageView: ImageView, status: Int) {

    when {
        status == 1 -> {
            imageView.setImageResource(R.drawable.waitforservice)
        }
        status == 2 -> {
            imageView.setImageResource(R.drawable.go1)
        }
        status == 3 -> {
            imageView.setImageResource(R.drawable.doneforservice)
        }

        status == 4 -> {
            imageView.setImageResource(R.drawable.confirmok)
        }

        status == 5 -> {
            imageView.setImageResource(R.drawable.delete3)
        }

    }
}

@BindingAdapter("statusInfoImage")
fun statusInfoImage(imageView: ImageView, status: Int) {

    when {
        status == 1 -> {
            imageView.setImageResource(R.drawable.waitforservice1)
        }
        status == 2 -> {
            imageView.setImageResource(R.drawable.process)
        }
        status == 3 -> {
            imageView.setImageResource(R.drawable.ok0)
        }
        status == 4 -> {
            imageView.setImageResource(R.drawable.okconfirm0)
        }

        status == 5 -> {
            imageView.setImageResource(R.drawable.nochange)
        }

    }
}

@BindingAdapter("statusInfoText")
fun statusInfoText(textView: TextView,status:Int){

    when {
        status == 1 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_info1)
        }
        status == 2 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_info2)
        }
        status == 3 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_info3)
        }

        status == 4 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_info4)
        }

        status == 5 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_info5)
        }

    }
}