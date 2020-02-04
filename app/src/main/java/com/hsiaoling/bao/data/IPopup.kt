package com.hsiaoling.bao.data



interface IPopup : ITimeDuration {

    val title: String

    val description: String

    val quote: String

    val imageStart: String

    val imageEnd: String

    val isAutohide: Boolean?
}
