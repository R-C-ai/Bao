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
    var device:String="",
    var screen:String="",
    var back:String="",
    var price:Long=0,
    var status:Int=-1,
    var statusText:String="",
    var reserveTime: Long=-1,
    var getJobTime:Long=-1,
    var doneTime: Long=-1,
    var finishCheckTime:Long=-1,
    var deleteTime:Long=-1,
    var updateTime: Long=-1

//    val reserve_time: Timestamp = Timestamp(Calendar.getInstance().time)
): Parcelable