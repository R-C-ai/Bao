package com.hsiaoling.bao.util

import android.util.Log
import com.hsiaoling.bao.BuildConfig


object Logger {

    private const val TAG = "H-Bao"

    fun v(content: String) {  Log.v(TAG, content) }
    fun d(content: String) {  Log.d(TAG, content) }
    fun i(content: String) {  Log.i(TAG, content) }
    fun w(content: String) {  Log.w(TAG, content) }
    fun e(content: String) {  Log.e(TAG, content) }



}