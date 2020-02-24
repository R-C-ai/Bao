package com.hsiaoling.bao.servicestatus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.*
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.login.SalesmanManager.salesman
import com.hsiaoling.bao.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ServiceStatusViewModel(private val repository: BaoRepository) : ViewModel() {

    private val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    fun selectedDate (date: String){
        _date.value = date
    }

    // set salesman in serviceStatusViiewModel
    private val _salesman = MutableLiveData<Salesman>()
    val salesman: LiveData<Salesman>
        get() = _salesman

    fun setSalesman (salesman: Salesman){
        _salesman.value = salesman
    }

    //update newStatus
    private val _newStatuses = MutableLiveData<Service>()
    val newStatuses: LiveData<Service>
        get() = _newStatuses

    // put selscted schedule data into service
    fun setNewstatus(service: Service) {
        _newStatuses.value = service
    }

    // put loginsalesman data into service
    fun setSalesmanForNewStatus(salesman: Salesman) {
        _newStatuses.value?.let {
            it.salesmanId = salesman.id
            it.salesmanName = salesman.name
        }
        _newStatuses.value = _newStatuses.value
    }


    //get exit reserved service
//    private var _liveStatuses = MutableLiveData<List<Service>>()
    var liveStatuses = MutableLiveData<List<Service>>()
//        get() = _liveStatuses


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



    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
//        getLiveStatus()
    }

    fun getLiveStatus(){
        liveStatuses =
            repository.getLiveStatus(SalesmanManager.salesman!!.id) as MutableLiveData<List<Service>>
        Log.i("HsiaoLing","getLiveStatus=${liveStatuses.value}")
    }


//
//    fun getDateResult(date: String){
//        coroutineScope.launch{
//            _status.value = LoadApiStatus.LOADING
//            val result = repository.getDateResult("")
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



}
