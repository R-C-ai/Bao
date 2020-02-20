package com.hsiaoling.bao.util

import com.hsiaoling.bao.R
import com.hsiaoling.bao.util.Util.getString

enum class CurrentFragmentType(val value: String) {
    LOGIN(""),
    ADDSERVICE(getString(R.string.addService)),
    SERVICESTATUS(getString(R.string.serviceStatus)),
    SALESAMOUNT(getString(R.string.salesAmount))
}
