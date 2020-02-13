package com.hsiaoling.bao.master.dailyItem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MasterDailyItemViewModel(private val repository: BaoRepository) :ViewModel(){



    private val _schedules = MutableLiveData<List<Service>>()

    val schedules: LiveData<List<Service>>
        get() = _schedules


    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus


    private val _navigateToAddBao = MutableLiveData<Service>()

    val navgateToAddBao:LiveData<Service>
    get() = _navigateToAddBao

    private val _refresh = MutableLiveData<Boolean>()

    val refresh: LiveData<Boolean>
        get() = _refresh


    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
//        getDateResult()
    }

    fun getDateResult(date: String){
        coroutineScope.launch{
            _status.value = LoadApiStatus.LOADING
            val result = repository.getDateResult(date)
            _schedules.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    Log.i("HsiaoLing","result.data=${result.data}")

                    result.data


                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = BaoApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false


        }
    }



//    init {
//        getScheduleResult()
//    }



//    fun getScheduleResult(){
//        coroutineScope.launch{
//            _status.value = LoadApiStatus.LOADING
//            val result = repository.getServicesInMaster()
//
//            _schedules.value = when (result) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    result.data
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                else -> {
//                    _error.value = BaoApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//            }
//            _refreshStatus.value = false
//
//
//        }
//    }

    fun refresh() {
        _refresh.value = true
    }

    fun onRefreshed() {
        _refresh.value = null
    }


    fun navgateToAddBao(service: Service){
        _navigateToAddBao.value =service
    }

    fun onAddJobNavigated(){
        _navigateToAddBao.value = null
    }

//    fun removeBaoService(baoService: BaoService){
//        coroutineScope.launch {
//            baoRepository
//        }
//    }


}