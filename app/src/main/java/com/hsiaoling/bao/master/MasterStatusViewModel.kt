package com.hsiaoling.bao.master

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.*
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.login.DayManager
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.login.SalesmanManager.salesman
import com.hsiaoling.bao.login.UserManager
import com.hsiaoling.bao.login.UserManager.user
import com.hsiaoling.bao.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MasterStatusViewModel(private val repository: BaoRepository) : ViewModel() {

    private val _service = MutableLiveData<Service>()
    val service: LiveData<Service>
        get() = _service as LiveData<Service>


    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus



    // Handle leave
    private val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave


    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {

    }



    fun leave() {
        _leave.value = true
    }
}
