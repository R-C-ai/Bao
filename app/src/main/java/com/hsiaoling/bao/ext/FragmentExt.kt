package com.hsiaoling.bao.ext

import android.app.Activity
import androidx.fragment.app.Fragment
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.ViewModelFactory

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as BaoApplication).repository
    return ViewModelFactory(repository)
}

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as BaoApplication).repository
    return ViewModelFactory(repository)
}
