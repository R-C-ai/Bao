package com.hsiaoling.bao.addservice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.data.BaoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddBaoViewModel(): ViewModel() {

    private val _baoService = MutableLiveData<BaoService>()

    val baoService:LiveData<BaoService>
    get() = _baoService

    val customerSort = MutableLiveData<Int>()
    val device = MutableLiveData<String>()
    val bao_service_0 = MutableLiveData<String>()
    val bao_service_1 = MutableLiveData<String>()
    val price = MutableLiveData<Int>()

    private val _navigateToAddedSuccess = MutableLiveData<BaoService>()
    val navigateToAddSuccess : LiveData<BaoService>
    get() = _navigateToAddedSuccess



    private val _navigateToAddedFail = MutableLiveData<BaoService>()
    val navigateToAddFail : LiveData<BaoService>
        get() = _navigateToAddedFail

    private val _leave = MutableLiveData<Boolean>()
    val leave : LiveData<Boolean>
    get() = _leave

    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * track [StylishRepository.getUserProfile]: -> [DefaultStylishRepository] : [StylishRepository] -> [StylishLocalDataSource] : [StylishDataSource]
     */
//    fun insertToMastert() {
//        baoService.value?.let {
//
//                    if (baoRepository.isBaoServiceInMaster()) {
//                        _navigateToAddedFail.value = it
//                    } else {
//                        baoRepository.insertBaoServiceInMaster(it)
//                        _navigateToAddedSuccess.value = it
//                    }
//                }
//            }



    fun onAddedSuccessNavigated() {
        _navigateToAddedSuccess.value = null
    }

    fun onAddedFailNavigated() {
        _navigateToAddedFail.value = null
    }


    fun leave() {
        _leave.value = true
    }

    fun onLeaveCompleted() {
        _leave.value = null
    }

    fun nothing() {}

}