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
import com.hsiaoling.bao.login.UserManager
import com.hsiaoling.bao.login.UserManager.user
import com.hsiaoling.bao.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ServiceStatusViewModel(private val repository: BaoRepository) : ViewModel() {


    // set salesman in serviceStatusViiewModel
    private val _salesman = MutableLiveData<User>()
    val salesman: LiveData<User>
        get() = _salesman

    // set master in serviceStatusViiewModel
    private val _master = MutableLiveData<User>()
    val master: LiveData<User>
        get() = _master

    fun setUser (user: User){
        when (user.type) {
            "salesman" -> _salesman.value = user
            "master" -> _master.value = user
        }
    }



//    fun setMaster (user: User){
//        _master.value = user
//    }

    //update newStatus
    private val _newStatuses = MutableLiveData<Service>()
    val newStatuses: LiveData<Service>
        get() = _newStatuses

    // put selscted status card data into service
    fun setNewstatus(service: Service) {
        _newStatuses.value = service
    }

    // put loginsalesman data into service
    fun setUserForNewStatus(user: User) {
        _newStatuses.value?.let {
            when (user.type){
                "salesman" -> {
                    it.salesmanId = user.id
                    it.salesmanName = user.name
                }
                "master" ->{
                    it.masterId = user.id
                    it.masterName = user.name
                }
            }
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

    private val _navigateToAddBao = MutableLiveData<Service>()
    val navgateToAddBao:LiveData<Service>
        get() = _navigateToAddBao

    private val _navigateToUpdateMasterJob = MutableLiveData<Service>()
    val navgateToUpdateMasterJob:LiveData<Service>
        get() = _navigateToUpdateMasterJob


    private val _navigateToUpdateStatus = MutableLiveData<Service>()
    val navgateToUpdateStatus:LiveData<Service>
        get() = _navigateToUpdateStatus

    private val _navigateToInfoStatus = MutableLiveData<Service>()
    val navgateToInfoStatus:LiveData<Service>
        get() = _navigateToInfoStatus


    // Handle leave
    private val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave



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
        when(user!!.type){
            "master" -> {
                repository.getMasterLiveStatus(UserManager.user!!.id) {
                    liveStatuses.value = it
                }
            }else ->{
            repository.getSalesmanLiveStatus(UserManager.user!!.id) {
                liveStatuses.value = it
            }
        }
        }



//        liveStatuses =
//            repository.getLiveStatus(SalesmanManager.salesman!!.id) as MutableLiveData<List<Service>>
        Log.i("HsiaoLing","getLiveStatus=${liveStatuses.value}")
    }


//  ----------------------------------------------------------------------------
    // get live status from firebase
//    fun getLiveStatus(){
//        repository.getLiveStatus(UserManager.user!!.id) {
//            liveStatuses.value = it
//        }

//        liveStatuses =
//            repository.getLiveStatus(SalesmanManager.salesman!!.id) as MutableLiveData<List<Service>>
//        Log.i("HsiaoLing","getLiveStatus=${liveStatuses.value}")
//    }
//    --------------------------------------------------------


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


//    fun navgateToUpdateStatus(service: Service){
//        when(service.status){
//            1 -> _navigateToUpdateStatus.value =service
//            else -> navgateToInfoStatus(service)
//        }
//    }

    fun navgateToUpdateStatus(service: Service){
        when(user!!.type){
            "master" -> {
                when(service.status){
                    1 -> _navigateToUpdateMasterJob.value = service
                    2 -> _navigateToUpdateMasterJob.value = service
                    else -> navgateToInfoStatus(service)
                }
            }else -> {
                when(service.status){
                    1 -> _navigateToAddBao.value =service
                    3 -> _navigateToUpdateStatus.value =service
                    else -> navgateToInfoStatus(service)
            }
        }
        }
    }

    fun navgateToInfoStatus(service: Service){
        _navigateToInfoStatus.value = service
    }



    fun onUpdateStatusNavigated(){
        _navigateToUpdateStatus.value = null
    }

    fun onInfoStatusNavigated(){
       _navigateToInfoStatus.value = null
    }


    fun leave() {
        _leave.value = true
    }
}
