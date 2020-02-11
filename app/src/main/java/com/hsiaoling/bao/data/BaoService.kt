package com.hsiaoling.bao.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


    @Parcelize
    data class BaoService(
        val customerSort: Int=0,
        val device: String="",
        val bao_service_0:String="",
        val bao_service_1:String="",
        val price:Int=0
    ): Parcelable

