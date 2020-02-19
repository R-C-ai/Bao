package com.hsiaoling.bao.data.source

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Salesman(
    var id: String = "",
    var name: String = ""
) : Parcelable