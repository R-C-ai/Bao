package com.hsiaoling.bao.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.data.Salesman

/**
 * Created by Wayne Chen in Jul. 2019.
 */
object SalesManManager {

    private const val SALESMAN_DATA = "salesman_data"
    private const val SALESMAN_TOKEN = "salesman_token"

    private val _salesman = MutableLiveData<Salesman>()

    val salesman: LiveData<Salesman>
        get() = _salesman

    var salesmanToken: String? = null
        get() = BaoApplication.instance
            .getSharedPreferences(SALESMAN_DATA, Context.MODE_PRIVATE)
            .getString(SALESMAN_TOKEN, null)
        set(value) {
            field = when (value) {
                null -> {
                    BaoApplication.instance
                        .getSharedPreferences(SALESMAN_DATA, Context.MODE_PRIVATE).edit()
                        .remove(SALESMAN_TOKEN)
                        .apply()
                    null
                }
                else -> {
                    BaoApplication.instance
                        .getSharedPreferences(SALESMAN_DATA, Context.MODE_PRIVATE).edit()
                        .putString(SALESMAN_TOKEN, value)
                        .apply()
                    value
                }
            }
        }

    /**
     * It can be use to check login status directly
     */
    val isLoggedIn: Boolean
        get() = salesmanToken != null

    /**
     * Clear the [userToken] and the [user]/[_user] data
     */
    fun clear() {
        salesmanToken = null
        _salesman.value = null
    }
}

