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
import com.hsiaoling.bao.data.User
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
    Log.i("Hsiao", "updateTime, time=$time")

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

    when {
        sort == 0 -> { imageView.setImageResource(R.drawable.ic_looks_1_white_24dp)}
        sort == 1 -> { imageView.setImageResource(R.drawable.ic_looks_2_white_24dp)}
        sort == 2 -> { imageView.setImageResource(R.drawable.ic_looks_3_white_24dp)}
        sort == 3 -> { imageView.setImageResource(R.drawable.ic_looks_4_white_24dp)}
        sort == 4 -> { imageView.setImageResource(R.drawable.ic_looks_5_white_24dp)}
        sort == 5 -> { imageView.setImageResource(R.drawable.ic_looks_6_white_24dp)}
        sort == 6 -> { imageView.setImageResource(R.drawable.ic_filter_7_white_24dp)}

        else ->{
            imageView.setImageResource(R.drawable.ic_av_timer_black_24dp)
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
// in Fragment master status , after fliter by status , show the image
@BindingAdapter("masterJobstatus")
fun masterJobstatus(imageView: ImageView,status:Int){
    when {
        status == 1 -> {imageView.setImageResource(R.drawable.endure5)}


        status == 6 -> {imageView.setImageResource(R.drawable.endure5)}
    }
}

// set text when master update job status
// in Fragment master status , after fliter by status , show the text
@BindingAdapter("masterJobstatusText")
fun masterJobstatusText(textView: TextView,status:Int){
    when {
//        status == 1 -> {textView.text ="開始包膜囉"}
        status == 1 -> {textView.text ="完成包膜 按我確認喔"}
//        status == 2 -> {textView.text ="我已完成囉"}
        status == 6 -> {textView.text ="完成包膜 按我確認喔"}
    }
}

// set image when salesman update service reserve status
@BindingAdapter("salesmanJobstatus")
fun salesmanJobstatus(imageView: ImageView,status:Int){

    when {
        status == 1 -> {imageView.setImageResource(R.drawable.endure7)}
        status == 3 -> {imageView.setImageResource(R.drawable.endure8)}
    }
}


// set text when master update job status
@BindingAdapter("salesmanJobstatusText")
fun salesmanJobstatusText(textView: TextView,status:Int){
    when {
        status == 1 -> {textView.text ="我要取消囉"}
        status == 3 -> {textView.text ="我要驗收囉"}
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
        status == 6 -> {
            textView.text = BaoApplication.instance.getString(R.string.status6)
        }

        status == 7 -> {
            textView.text = BaoApplication.instance.getString(R.string.status7)
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
        status == 6 -> {
            imageView.setImageResource(R.drawable.bg_status6)
        }
        status == 7 -> {
            imageView.setImageResource(R.drawable.bg_status7)
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
        status == 6 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_page6)
        }
        status == 7 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_page7)
        }

    }
}

@BindingAdapter("statusImage")
fun statusImage(imageView: ImageView, status: Int) {

    when {
        status == 1 -> {
            imageView.setImageResource(R.drawable.endure1)
        }
        status == 2 -> {
            imageView.setImageResource(R.drawable.endure6)
        }
        status == 3 -> {
            imageView.setImageResource(R.drawable.endure4)
        }

        status == 4 -> {
            imageView.setImageResource(R.drawable.endure8)
        }

        status == 5 -> {
            imageView.setImageResource(R.drawable.endure3)
        }

        status == 6 -> {
            imageView.setImageResource(R.drawable.endure6)
        }

        status == 7 -> {
            imageView.setImageResource(R.drawable.endure8)
        }


    }
}

@BindingAdapter("statusInfoImage")
fun statusInfoImage(imageView: ImageView, status: Int) {

    when {
        status == 1 -> {
            imageView.setImageResource(R.drawable.endure6)
        }
        status == 2 -> {
            imageView.setImageResource(R.drawable.endure2)
        }
        status == 3 -> {
            imageView.setImageResource(R.drawable.endure4)
        }
        status == 4 -> {
            imageView.setImageResource(R.drawable.endure8)
        }
        status == 5 -> {
            imageView.setImageResource(R.drawable.endure3)
        }
        status == 6 -> {
            imageView.setImageResource(R.drawable.endure6)
        }
        status == 7 -> {
            imageView.setImageResource(R.drawable.endure8)
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
        status == 6 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_info6)
        }

        status == 7 -> {
            textView.text = BaoApplication.instance.getString(R.string.status_info7)
        }



    }
}

@BindingAdapter("userText")
fun userText(textView: TextView,user: User){

    when {
         user.type == "salesman" -> {
             textView.text = "門市人員"
        }

        user.type == "master" -> {
            textView.text = "包膜師"
        }
    }
}

@BindingAdapter("itemStatusUser","service")
fun itemStatusUser(textView: TextView,user: User?,service: Service?){
    service?.let {
        user?.let {
            when {
                user.type == "salesman" -> {
                    textView.text = service.masterName
                }
                user.type == "master" -> {
                    textView.text = service.salesmanName
                }
            }
        }

    }
}