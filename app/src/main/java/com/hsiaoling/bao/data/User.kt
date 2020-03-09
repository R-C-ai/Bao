package com.hsiaoling.bao.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    var id: String = "",
    var name: String = "",
    var type : String = ""
) : Parcelable