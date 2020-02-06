package com.hsiaoling.bao.master.dailyItem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.data.BaoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MasterDailyItemViewModel() :ViewModel(){

    private val _navigateToAddBao = MutableLiveData<BaoService>()

    val navgateToAddBao:LiveData<BaoService>
    get() = _navigateToAddBao

    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun navgateToAddBao(baoService: BaoService){
        _navigateToAddBao.value = baoService
    }

    fun onAddJobNavigated(){
        _navigateToAddBao.value = null
    }
}