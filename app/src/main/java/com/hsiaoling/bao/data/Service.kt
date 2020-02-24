package com.hsiaoling.bao.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class Service(
    var storeId:String="",
    var storeName:String="",
    var salesmanId:String="",
    var salesmanName:String="",
    var masterId:String="",
    var masterName:String="",
    var customerNo: String="",
    var date:String="",
    var scheduleSort: Int = -1,
    var serviceId:String="",
    var device: String="",
    var service0:String="",
    var service1:String="",
    var price:Long=0,
    var status:Int=-1,
    var statusImg: String = "",
    var statusText: String = "",
    var reserveTime: Long=-1,
    var doneTime: Long=-1,
    var master: Master = Master("","")
//    val reserve_time: Timestamp = Timestamp(Calendar.getInstance().time)
): Parcelable