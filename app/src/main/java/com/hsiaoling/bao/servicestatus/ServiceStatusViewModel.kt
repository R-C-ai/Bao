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

    // put selscted status card data into service
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
    var  liveStatuses = MutableLiveData<List<Service>>()
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


    private val _navigateToUpdateStatus = MutableLiveData<Service>()
    val navgateToUpdateStatus:LiveData<Service>
        get() = _navigateToUpdateStatus

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
//        getLiveStatus()
    }

    // get live status from firebase
    fun getLiveStatus(){
        repository.getLiveStatus(SalesmanManager.salesman!!.id) {
            liveStatuses.value = it
        }
//        liveStatuses =
//            repository.getLiveStatus(SalesmanManager.salesman!!.id) as MutableLiveData<List<Service>>
//        Log.i("HsiaoLing","getLiveStatus=${liveStatuses.value}")
    }

    // filter the status livedata  type to print different info
    val filterStatus = MutableLiveData<Int>()

   fun filterList(type: Int) {
       filterStatus.value = type
   }


    fun refresh() {
        _refresh.value = true
    }

    fun onRefreshed() {
        _refresh.value = null
    }


    fun navgateToUpdateStatus(service: Service){
        _navigateToUpdateStatus.value =service
    }

    fun onUpdateStatusNavigated(){
        _navigateToUpdateStatus.value = null
    }





}
