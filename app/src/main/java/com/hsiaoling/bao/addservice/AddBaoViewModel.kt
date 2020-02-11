package com.hsiaoling.bao.addservice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.BaoService
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import com.hsiaoling.bao.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.hsiaoling.bao.data.Result

class AddBaoViewModel(
    private val repository:BaoRepository



): ViewModel() {


    private val _service = MutableLiveData<Service>().apply {
        value =Service(
            )
    }
    val service:LiveData<Service>
    get() = _service

//    val service1 = Service("","","MASTER_幾米哥","",
//        "2020-02-12","1","1","","","",600,"0",
//    "","可預約",100)
//
//    val service2 = Service("","","MASTER_幾米哥","",
//        "2020-02-12","2","1","","","",600,"0",
//        "","可預約",100)
//
//    val service3 = Service("","","MASTER_幾米哥","",
//        "2020-02-12","3","1","","","",600,"0",
//        "","可預約",100)
//
//    val service4 = Service("","","MASTER_幾米哥","",
//        "2020-02-12","4","1","","","",600,"0",
//        "","可預約",100)


    private val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error




    val customer_no = MutableLiveData<String  >()
    val device = MutableLiveData<String>()
    val bao_service_0 = MutableLiveData<String>()
    val bao_service_1 = MutableLiveData<String>()
    val price = MutableLiveData<Int>()

    private val _navigateToAddedSuccess = MutableLiveData<Service>()
    val navigateToAddSuccess : LiveData<Service>
    get() = _navigateToAddedSuccess



    private val _navigateToAddedFail = MutableLiveData<BaoService>()
    val navigateToAddFail : LiveData<BaoService>
        get() = _navigateToAddedFail



    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

    fun insertServiceToMastert(service: Service) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.insertServiceInMaster(service))

            {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    leave(true)
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = BaoApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
      }





    fun onAddedSuccessNavigated() {
        _navigateToAddedSuccess.value = null
    }

    fun onAddedFailNavigated() {
        _navigateToAddedFail.value = null
    }


    fun leave(needRefresh:Boolean = false) {
        _leave.value = true
    }

    fun onLeaveCompleted() {
        _leave.value = null
    }

    fun nothing() {}

}