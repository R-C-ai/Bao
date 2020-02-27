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

@BindingAdapter("updateTime", "status")
fun updateTime(textView: TextView, time: Long?,status:Int) {
    Log.i("Hsiao", "doneTime, time=$time")

    when {
        status == 1 ->    textView.visibility = View.INVISIBLE
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
        sort == 0 -> {
            imageView.setImageResource(R.drawable.one)
        }
        sort == 1 -> {
            imageView.setImageResource(R.drawable.two)
        }
        sort == 2 -> {
            imageView.setImageResource(R.drawable.three)
        }
        sort == 3 -> {
            imageView.setImageResource(R.drawable.four)
        }
        sort == 4 -> {
            imageView.setImageResource(R.drawable.five)
        }
        sort == 5 -> {
            imageView.setImageResource(R.drawable.six)
        }
        sort == 6 -> {
            imageView.setImageResource(R.drawable.seven)
        }
        sort == 7 -> {
            imageView.setImageResource(R.drawable.eight)
        }
    }
}

@BindingAdapter("itemScheduleTime")
fun itemScheduleTime(textView:TextView, sort: Int) {

    when {
        sort == 0 -> {
            textView.text = BaoApplication.instance.getString(R.string.time0)
        }
        sort == 1 -> {
            textView.text = BaoApplication.instance.getString(R.string.time1)
        }
        sort == 2 -> {
            textView.text = BaoApplication.instance.getString(R.string.time2)
        }
        sort == 3 -> {
            textView.text = BaoApplication.instance.getString(R.string.time3)
        }
        sort == 4 -> {
            textView.text = BaoApplication.instance.getString(R.string.time4)
        }
        sort == 5 -> {
            textView.text = BaoApplication.instance.getString(R.string.time5)
        }
        sort == 6 -> {
            textView.text = BaoApplication.instance.getString(R.string.time6)
        }
        sort == 7 -> {
            textView.text = BaoApplication.instance.getString(R.string.time7)
        }
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
            textView.text = BaoApplication.instance.getString(R.string.status3)
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

    }
}


//@BindingAdapter("editorControllerStatus")
//fun bindEditorControllerStatus(imageButton: ImageButton, enabled: Boolean) {
//
//    imageButton.apply {
//        foreground = ShapeDrawable(object : Shape() {
//            override fun draw(canvas: Canvas, paint: Paint) {
//
//                paint.color = Color.BLACK
//                paint.style = Paint.Style.STROKE
//                paint.strokeWidth = BaoApplication.instance.resources
//                    .getDimensionPixelSize(R.dimen.edge_add2cart_select).toFloat()
//                canvas.drawRect(0f, 0f, this.width, this.height, paint)
//            }
//        })
//        isClickable = enabled
//        backgroundTintList = ColorStateList.valueOf(
//            getColor(
//                when (enabled) {
//                    true -> R.color.black_3f3a3a
//                    false -> R.color.gray_999999
//                }))
//        foregroundTintList = ColorStateList.valueOf(
//            getColor(
//                when (enabled) {
//                    true -> R.color.black_3f3a3a
//                    false -> R.color.gray_999999
//                }))
//    }
//}
