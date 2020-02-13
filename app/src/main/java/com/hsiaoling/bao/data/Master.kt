package com.hsiaoling.bao.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Master(
    var id: String = "",
    var name: String = ""
) : Parcelable