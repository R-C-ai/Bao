package com.hsiaoling.bao.master.dailyItem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.NavigationDirections
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.messageDialog.MessageDialog
import com.hsiaoling.bao.network.LoadApiStatus
import com.hsiaoling.bao.util.Util.getString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.hsiaoling.bao.data.User
import com.hsiaoling.bao.login.UserManager
import com.hsiaoling.bao.login.UserManager.user

class MasterDailyItemViewModel(private val repository: BaoRepository) :ViewModel(){

    val storeId = "4d7yMjfPO5lw66u8sHnt"
    val storeName = "松菸文創店"


    private var _schedules = MutableLiveData<List<Service>>()
    val schedules: LiveData<List<Service>>
        get() = _schedules

    private var _service = MutableLiveData<Service>()
    val service: LiveData<Service>
        get() = _service


    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

//    private val _refreshStatus = MutableLiveData<Boolean>()
//    val refreshStatus: LiveData<Boolean>
//        get() = _refreshStatus

//    private val _isSalesman = MutableLiveData<Boolean>()
//    val isSalesman: LiveData<Boolean>
//        get() = _isSalesman


    private val _navigateToAddBao = MutableLiveData<Service>()
    val navgateToAddBao:LiveData<Service>
    get() = _navigateToAddBao

    private val _navigateToDeleteJob = MutableLiveData<Service>()
    val navgateToDeleteJob:LiveData<Service>
        get() = _navigateToDeleteJob


    private val _navigateToUpdateStatus = MutableLiveData<Service>()
    val navgateToUpdateStatus:LiveData<Service>
        get() = _navigateToUpdateStatus

    private val _navigateToInfoStatus = MutableLiveData<Service>()
    val navgateToInfoStatus:LiveData<Service>
        get() = _navigateToInfoStatus


    private val _navigateToMasterInfo = MutableLiveData<Boolean>()
    val navigateToMasterInfo: LiveData<Boolean>
        get() = _navigateToMasterInfo

    private val _navigateToAddReject = MutableLiveData<Boolean>()
    val navigateToAddReject: LiveData<Boolean>
        get() = _navigateToAddReject


    private val _navigateToMasterJob = MutableLiveData<Service>()
    val navgateToMasterJob:LiveData<Service>
        get() = _navigateToMasterJob





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





    fun getLiveDateServices(date: String,masterId:String) {
        _schedules =
            repository.getLiveDateServices(date, masterId) as MutableLiveData<List<Service>>
    }




    fun newDailyServices (date: String, masterId: String,masterName:String) {

        for (i in 0..7) {
            val service = Service(storeId,"松菸文創店","","",masterId,masterName,"",date,
                i,"","","","",0,0,"可預約",0,0,0,0
                ,0)

            Log.i("HsiaoLing","addNewService=$service")
            addNewDayToMaster(service)
        }

    }

    fun addNewDayToMaster(service: Service) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addNewDayToMaster(service))

            {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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


    fun deleteService(service: Service) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.deleteService(service))

            {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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



    fun refresh() {
        _refresh.value = true
    }

    fun onRefreshed() {
        _refresh.value = null
    }


//    fun navgateToAddBao(service: Service){
//        when(service.salesmanId) {
//            SalesmanManager.salesman!!.id ->_navigateToAddBao.value =service
//            ""->_navigateToAddBao.value =service
//
//            else -> navigateToAddReject()
//        }
//
//    }






        fun navgateToAddBao(service: Service){
            val userType = UserManager.user!!.type
            if (userType == "salesman"){
                when(service.salesmanId) {
                    UserManager.user!!.id ->
                        when(service.status){
                            0 ->  _navigateToAddBao.value =service
                            1 -> _navigateToAddBao.value = service
                            3 -> _navigateToUpdateStatus.value = service

                            else -> navgateToInfoStatus(service)
                        }
                    ""->_navigateToAddBao.value =service
                    else -> navigateToAddReject()
                 }
            }else {
                when(service.status){
                    0 -> navigateToMasterInfo()
                    1 -> _navigateToMasterJob.value = service
                    2 -> _navigateToMasterJob.value = service
                    3 -> navgateToInfoStatus(service)
                    4 -> navgateToInfoStatus(service)
                    5 -> navgateToInfoStatus(service)
                }

//                navigateToAddReject()
            }
         }


//    fun navgateToAddBao(service: Service){
//        when(service.salesmanId) {
//            UserManager.user!!.id ->
//                when(service.status){
//                    0 ->  _navigateToAddBao.value =service
//                    else -> navgateToInfoStatus(service)
//                }
//
//            ""->_navigateToAddBao.value =service
//
//            else -> navigateToAddReject()
//        }
//
//    }

    fun navigateToMasterInfo(){
        _navigateToMasterInfo.value = true
    }

    fun onMasterInfoNavigated() {
        _navigateToMasterInfo.value = null
    }

    fun navigateToDeleteJob(service: Service){
        _navigateToDeleteJob.value = service
    }

    fun onDeleteJobNavigated(){
        _navigateToDeleteJob.value = null
    }

    fun navigateToConfirmDone(service: Service){
        _navigateToUpdateStatus.value = service
    }

    fun onConfirmDoneNavigated(){
        _navigateToUpdateStatus.value = null
    }

    fun navigateToMasterJob(service: Service){
        _navigateToMasterJob.value = service
    }

    fun onMasterJobNavigated(){
        _navigateToMasterJob.value = null
    }


    fun navigateToAddReject(){
        _navigateToAddReject.value = true
    }

    fun onAddedRejectNavigated() {
        _navigateToAddReject.value = null
    }

    fun onAddJobNavigated(){
        _navigateToAddBao.value = null
    }



    fun navgateToInfoStatus(service: Service){
        _navigateToInfoStatus.value = service
    }


    fun onInfoStatusNavigated(){
        _navigateToInfoStatus.value = null
    }





}