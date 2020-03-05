package com.hsiaoling.bao.servicestatus


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
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import com.hsiaoling.bao.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.source.remote.BaoRemoteDataSource.getDateResult
import com.hsiaoling.bao.login.SalesmanManager.salesman

class StatusInfoViewModel(private val repository: BaoRepository) : ViewModel() {


    // Get Input Update Service Status  LiveData
    private val _service = MutableLiveData<Service>()
    val service: LiveData<Service>
        get() = _service as LiveData<Service>

    // put selscted status card data into service
    fun updateStatus(service: Service) {
        _service.value = service

        Log.i("Hsiao", " StatusInfoViewModel_service=${_service}")
    }

    // put loginsalesman data into service
    fun setSalesmanForService(salesman: Salesman) {
        _service.value?.let {
            it.salesmanId = salesman.id
            it.salesmanName = salesman.name
        }
        _service.value = _service.value
    }


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
//        Logger.i("------------------------------------")
//        Logger.i("[${this::class.simpleName}]${this}")
//        Logger.i("------------------------------------")
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

