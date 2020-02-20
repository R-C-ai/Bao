package com.hsiaoling.bao.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
data class Schedule(
    var sortNumber: String = "",
    var statusImg: String = "",
    var statusText: String = "",
    var reserveTime: Long = -1
) : Parcelable