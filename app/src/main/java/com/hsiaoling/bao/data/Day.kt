package com.hsiaoling.bao.data

import android.icu.util.Calendar
import android.os.Parcelable
import com.hsiaoling.bao.ext.toCurrentFormat
import com.hsiaoling.bao.ext.toDayFormat
import com.hsiaoling.bao.ext.toMonthFormat
import com.hsiaoling.bao.ext.toYearFormat
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat


@Parcelize
data class Day(

  var todayTimeStamp:Long = 0,
  var firstDay:Long = 0,
  var endDay:Long = 0



) : Parcelable
