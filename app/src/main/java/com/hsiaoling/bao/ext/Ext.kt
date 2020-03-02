package com.hsiaoling.bao.ext

import android.graphics.Rect
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.view.TouchDelegate
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

fun Long.toDisplayFormat(): String {

    return SimpleDateFormat("MM/dd hh:mm",Locale.TAIWAN).format(this)
}

fun Long.toTodayFormat():String {
    return SimpleDateFormat ("yyyy.MM.dd",Locale.TAIWAN).format(this)
}

fun View.setTouchDelegate() {
    val parent = this.parent as View  // button: the view you want to enlarge hit area
    parent.post {
        val rect = Rect()
        this.getHitRect(rect)
        rect.top -= 100    // increase top hit area
        rect.left -= 100   // increase left hit area
        rect.bottom += 100 // increase bottom hit area
        rect.right += 100  // increase right hit area
        parent.touchDelegate = TouchDelegate(rect, this)
    }
}




//fun addData() {
//    val articles = FirebaseFirestore.getInstance()
//        .collection("articles")
//
//    val document = articles.document()
//
//    val data = hashMapOf(
//        "author" to hashMapOf(
//            "email" to "wayne@school.appworks.tw",
//            "id" to "waynechen323",
//            "name" to "AKA小安老師"
//        ),
//        "title" to "IU「亂穿」竟美出新境界！笑稱自己品味奇怪　網笑：靠顏值撐住女神氣場",
//        "content" to "南韓歌手IU（李知恩）無論在歌唱方面或是近期的戲劇作品都有亮眼的成績，但俗話說人無完美、美玉微瑕，曾再跟工作人員的互動影片中坦言自己品味很奇怪，近日在IG上分享了宛如「媽媽們青春時代的玉女歌手」超復古穿搭造型，卻意外美出新境界。",
//        "createdTime" to Calendar.getInstance().timeInMillis,
//        "id" to document.id,
//        "tag" to "Beauty"
//    )
//    document.set(data)
//}