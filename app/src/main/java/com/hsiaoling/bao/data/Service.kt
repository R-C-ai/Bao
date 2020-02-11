package com.hsiaoling.bao.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class Service(
    val store_id:String="",
    val salesman_id:String="",
    val master_id:String="",
    var customer_no: String="",
    var date:String="",
    var schedule_sort: String = "",
    var service_id:String="",
    var device: String="",
    var service_0:String="",
    var service_1:String="",
    var price:Int=0,
    var status:String="",
    var status_img: String = "",
    var status_text: String = "",
    var reserve_time:Long =0
//    val reserve_time: Timestamp = Timestamp(Calendar.getInstance().time)
): Parcelable