package com.hsiaoling.bao.addservice



import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import com.hsiaoling.bao.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.hsiaoling.bao.data.Result

class AddBaoViewModel(private val repository:BaoRepository): ViewModel() {


    private val _service = MutableLiveData<Service>()
    val service:LiveData<Service>
    get() = _service

    fun setService(service: Service) {
        _service.value = service
    }

    private val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error


//    val _customer_no = MutableLiveData<String  >()
//        val customer_no:LiveData<String>
//        get()=_customer_no
//
//    val _device = MutableLiveData<String>()
//        val device:LiveData<String>
//        get()=_device
//
//    val _bao_service_0 = MutableLiveData<String>()
//        val bao_service_0:LiveData<String>
//        get()=_bao_service_0
//
//    val _bao_service_1 = MutableLiveData<String>()
//        val bao_service_1:LiveData<String>
//        get()=_bao_service_1
//
//    val _price = MutableLiveData<Int>()
//        val price : LiveData<Int>
//        get()=_price

    private val _navigateToAddedSuccess = MutableLiveData<Service>()
        val navigateToAddSuccess : LiveData<Service>
        get() = _navigateToAddedSuccess

    private val _navigateToAddedFail = MutableLiveData<Service>()
        val navigateToAddFail : LiveData<Service>
        get() = _navigateToAddedFail

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

//    init {
//        Logger.i("------------------------------------")
//        Logger.i("[${this::class.simpleName}]${this}")
//        Logger.i("------------------------------------")
//    }

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


fun click (){
    if (service.value != null ){
        insertServiceToMastert(service.value!!)
    }

}






    fun onAddedSuccessNavigated() {
        _navigateToAddedSuccess.value = null
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



    fun leave(needRefresh:Boolean = false) {
        _leave.value = true
    }

    fun onLeft() {
        _leave.value = null
    }

    fun refresh() {
        _refresh.value = true
    }

    fun onRefreshed() {
        _refresh.value = null
    }

    fun onLeaveCompleted() {
        _leave.value = null
    }

    fun nothing() {}

}