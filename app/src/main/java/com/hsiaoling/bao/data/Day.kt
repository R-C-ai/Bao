package com.hsiaoling.bao.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.Month
import java.time.Year

@Parcelize
data class Day(
    val year: Int,
    val month: Int,
    val day:Int
):Parcelable
