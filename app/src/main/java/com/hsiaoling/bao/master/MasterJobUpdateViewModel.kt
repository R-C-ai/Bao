package com.hsiaoling.bao.master


import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.*
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import com.hsiaoling.bao.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.hsiaoling.bao.data.source.remote.BaoRemoteDataSource.getDateResult
import com.hsiaoling.bao.login.SalesmanManager.salesman
import com.hsiaoling.bao.login.UserManager
import com.hsiaoling.bao.servicestatus.ServiceAction

class MasterJobUpdateViewModel(private val repository: BaoRepository) : ViewModel() {


    // Get Input Update Service Status  LiveData
    private val _service = MutableLiveData<Service>()
    val service: LiveData<Service>
        get() = _service as LiveData<Service>

    // put selscted status card data into service
    fun updateMasterJob(service: Service) {
        _service.value = service

        Log.i("Hsiao", " StatusUpdateViewModel_service=${_service}")
    }

    // put loginsalesman data into service
    fun setMsaterForJob(user: User) {
            _service.value?.let {
                it.masterId = user.id
                it.masterName = user.name
            }
            _service.value = _service.value
        }


    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    // get uodate Service
    private val _oneService = MutableLiveData<Service>()
    val oneService: LiveData<Service>
        get() = _service as LiveData<Service>


    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus


    private val _navigateToAddSuccess = MutableLiveData<Service>()
    val navigateToAddSuccess: LiveData<Service>
        get() = _navigateToAddSuccess

    private val _navigateToAddedFail = MutableLiveData<Service>()
    val navigateToAddedFail: LiveData<Service>
        get() = _navigateToAddedFail

    private val _navigateToDeleteSuccess = MutableLiveData<Service>()
    val navigateToDeleteSuccess: LiveData<Service>
        get() = _navigateToDeleteSuccess

    private val _navigateToDeletedFail = MutableLiveData<Service>()
    val navigateToDeletedFail: LiveData<Service>
        get() = _navigateToDeletedFail


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


    fun updateStatus(service: Service, serviceAction: ServiceAction) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            Log.i("HsiaoLingUpdate", " _status.value=${service}")
            when (val result = repository.updateStatus(service, serviceAction)) {

                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
//                    refresh()
                    Log.i("Hsiao", "refreshData=${result.data}")

                    _navigateToAddSuccess.value = service
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
            _refreshStatus.value = false
        }
    }

//    fun refresh() {
//        if (status.value != LoadApiStatus.LOADING) {
//            Log.i("Hsiao", "")
//            getOneServiceResult(
//                service.value!!.date,
//                service.value!!.masterId,
//                service.value!!.serviceId
//            )
//        }
//    }


//    fun getOneServiceResult(date: String, masterId: String, serviceId: String) {
//
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//
//            val result = repository.getOneServiceResult(date, masterId, serviceId)
//
//            _oneService.value = when (result) {
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
//        }
//    }

//    master grab job (status1) can update to doneJob (status 3)
fun updateMasterJob() {
    if (service.value != null) {
        when{
            service.value!!.status == 1 ->{
                service.value!!.status = 3
                updateStatus(service.value!!, serviceAction = ServiceAction.DONE)
                Log.i("HsiaoLingStatus", "selectGetJob=${service.value}")
            }

            service.value!!.status == 2 ->{
                service.value!!.status = 3
                updateStatus(service.value!!, serviceAction = ServiceAction.DONE)
                Log.i("HsiaoLingStatus", "selectFinish=${service.value}")
            }
        }

    }
}


    //    master  has been reserve (status1) can update to start getJob (status 2) , and than can from getJob(status2) to doneJob(status3)
//    ------------------------------------------------------------------------------------------------------------------------------------------------------------
//    fun updateMasterJob() {
//         if (service.value != null) {
//             when{
//                 service.value!!.status == 1 ->{
//                     service.value!!.status = 2
//                     updateStatus(service.value!!, serviceAction = ServiceAction.GETJOB)
//                     Log.i("HsiaoLingStatus", "selectGetJob=${service.value}")
//                 }
//
//                 service.value!!.status == 2 ->{
//                     service.value!!.status = 3
//                     updateStatus(service.value!!, serviceAction = ServiceAction.DONE)
//                     Log.i("HsiaoLingStatus", "selectFinish=${service.value}")
//                 }
//             }
//
//         }
//    }

//  -------------------------------------------------------------------------------------------------------------------



    //
//    fun selectGetJob() {
//        if (service.value != null) {
//            service.value!!.status = 2
//            updateStatus(service.value!!, serviceAction = ServiceAction.GETJOB)
//            Log.i("HsiaoLingStatus", "selectGetJob=${service.value}")
//        }
//    }
//
//    fun selectDone() {
//        if (service.value != null) {
//            service.value!!.status = 3
//            updateStatus(service.value!!, serviceAction = ServiceAction.DONE)
//            Log.i("HsiaoLingStatus", "selectFinish=${service.value}")
//        }
//    }




    fun onAddedSuccessNavigated() {
        _navigateToAddSuccess.value = null
    }

    fun onAddedFailNavigated() {
        _navigateToAddedFail.value = null
    }

    @InverseMethod("convertLongToString")
    fun convertStringToLong(value: String): Long {
        return try {
            value.toLong().let {
                when (it) {
                    0L -> 1
                    else -> it
                }
            }
        } catch (e: NumberFormatException) {
            1
        }
    }

    fun convertLongToString(value: Long): String {
        return value.toString()
    }


    fun leave() {
        _leave.value = true
    }


    fun onLeft() {
        _leave.value = null
    }


    fun onRefreshed() {
        _refreshStatus.value = null
    }


    fun onLeaveCompleted() {
        _leave.value = null
    }

    fun nothing() {}



}

