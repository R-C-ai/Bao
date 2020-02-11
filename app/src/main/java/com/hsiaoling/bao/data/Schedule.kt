package com.hsiaoling.bao.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
data class Schedule(
    var sort_number: String = "",
    var status_img: String = "",
    var status_text: String = "",
    var reserve_time: Long = -1
) : Parcelable