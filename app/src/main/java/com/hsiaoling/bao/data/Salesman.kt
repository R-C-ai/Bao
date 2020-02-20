package com.hsiaoling.bao.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Salesman(
    var id: String = "",
    var name: String = "",
    var store : Store
) : Parcelable