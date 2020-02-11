package com.hsiaoling.bao.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.Month
import java.time.Year

@Parcelize
data class Date(
    val year: Int=0,
    val month: Int=0,
    val day:Int=0
):Parcelable
