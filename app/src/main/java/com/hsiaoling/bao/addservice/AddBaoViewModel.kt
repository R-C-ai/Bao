package com.hsiaoling.bao.addservice


import android.util.Log
import androidx.databinding.InverseMethod
import androidx.lifecycle.*
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.User

class AddBaoViewModel(private val repository: BaoRepository) : ViewModel() {

    // Get Input Service  LiveData
    private val _service = MutableLiveData<Service>()
    val service: LiveData<Service>
        get() = _service as LiveData<Service>

    private val _deviceIndex = MutableLiveData<Int>()
    val deviceIndex: LiveData<Int>
        get() = _deviceIndex as LiveData<Int>

    private val _screenIndex = MutableLiveData<Int>()
    val screenIndex: LiveData<Int>
        get() = _screenIndex as LiveData<Int>

    private val _backIndex = MutableLiveData<Int>()
    val backIndex: LiveData<Int>
        get() = _backIndex as LiveData<Int>


    // put selscted schedule data into service
    fun setService(service: Service) {
        _service.value = service

    }



    //DeviceChosen spinner
//    val selectedDevicePosition = MutableLiveData<Int>()
//    // change livedata by Transformation.map to selectedvalue
//    val deviceChosen: LiveData<DeviceChosen> = Transformations.map(selectedDevicePosition) {
//        //put the selected data to service
//        service.value!!.device = DeviceChosen.values()[it].toString()
//        Log.i("Hsiao", "service.value!!.device =$it")
//        // get the DeviceChosen value  by the corresponding position
//        DeviceChosen.values()[it]
//    }

    val selectedDevicePosition = MutableLiveData<Int>()
     val deviceChosen: LiveData<DeviceChosen> = Transformations.map(selectedDevicePosition) {
         service.value!!.device = DeviceChosen.values()[it].toString()
         DeviceChosen.values()[it]
        }

    fun getDevicePosition(){
        val priorDevice = _service.value!!.device
        DeviceChosen.values()?.let {
            for (i in it) {
                 _deviceIndex.value =it.indexOf(DeviceChosen.valueOf(priorDevice))
            }
        }
    }



    //ScreenChosen spinner
    val selectedScreenPosition = MutableLiveData<Int>()
    // get the screenChosen value by the selected position livedata
    val screenChosen: LiveData<ScreenChosen> = Transformations.map(selectedScreenPosition) {
        // put livedata to service
        service.value!!.screen = ScreenChosen.values()[it].toString()
        ScreenChosen.values()[it]
        }

        // for change service
        fun getScreenPosition(){
        val priorScreen = _service.value!!.screen
        ScreenChosen.values()?.let {
            for (i in it) {
                _screenIndex.value =it.indexOf(ScreenChosen.valueOf(priorScreen))
            }
        }
    }

    // get the screenPricevalue by the selected position livedata
    val screenPrice:LiveData<Int> = Transformations.map(selectedScreenPosition){
        when(it) {
            0 -> 1200
            1 -> 800
            2 -> 600
            3 -> 600
            4 -> 400
            5 -> 0
            else -> 0
        }
    }

    //BackChosen spinner
    val selectedBackPosition = MutableLiveData<Int>()
    val backChosen: LiveData<BackChosen> = Transformations.map(selectedBackPosition) {
        service.value!!.back = BackChosen.values()[it].toString()
        BackChosen.values()[it]
    }

    // for change service
    fun getBackPosition(){
        val priorBack = _service.value!!.back
        BackChosen.values()?.let {
            for (i in it) {
                _backIndex.value =it.indexOf(BackChosen.valueOf(priorBack))
            }
        }
    }

    val backPrice: LiveData<Int> = Transformations.map(selectedBackPosition){
        when(it){
            0 ->1650
            1 ->1450
            2 ->1250
            3 ->1050
            4 ->850
            5 -> 0
            else -> 0
        }
    }


    // get two livedata be added by MediatorLiveData
    val totalPrice = MediatorLiveData<Int>().apply {

        addSource(screenPrice){
            it?.let {value = it + (backPrice.value?:0) }
        }
        addSource(backPrice){
            it?.let { value = it + (screenPrice.value?:0) }
        }
    }






    // put loginsalesman data into service
    fun setLoginUserForService(user: User) {
        _service.value?.let {
            when(user.type){
                "salesman" -> {
                    it.salesmanId = user.id
                    it.salesmanName = user.name
                }
                "master" -> {
                    it.masterId = user.id
                    it.masterId = user.id
                }
            }
        }
        _service.value = _service.value
    }

    // get uodate Service
    private val _oneService = MutableLiveData<Service>()
    val oneService: LiveData<Service>
        get() = _oneService as LiveData<Service>


    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus




    private val _navigateToAddSuccess = MutableLiveData<Service>()
    val navigateToAddSuccess: LiveData<Service>
        get() = _navigateToAddSuccess

    private val _navigateToAddedFail = MutableLiveData<Service>()
    val navigateToAddFail: LiveData<Service>
        get() = _navigateToAddedFail


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

    fun click() {
        if (service.value != null) {
            service.value!!.price = totalPrice.value!!
            addMasterService(service.value!!)
            Log.i("HsiaoLingUpdate", "UpateNewData=${service.value}")

        }
    }


    fun addMasterService(service: Service) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            Log.i("HsiaoLingUpdate", " _status.value=${service}")
            when (val result = repository.addMasterService(service)) {

                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    refresh()
                    Log.i("HsiaoLingUpdate", "refreshData=${result.data}")
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

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            Log.i("Hsiao", "")
            getAddServiceResult(
                service.value!!.date,
                service.value!!.masterId,
                service.value!!.serviceId
            )

        }
    }


    fun getAddServiceResult(date: String, masterId: String, serviceId: String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getAddServiceResult(date, masterId, serviceId)

            _oneService.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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


        fun onLeaveCompleted() {
            _leave.value = null
        }

        fun nothing() {}

    }

