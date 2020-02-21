package com.hsiaoling.bao.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.Store
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [LoginDialog].
 */
class LoginViewModel(private val repository: BaoRepository) : ViewModel() {

    val mockStore = Store("4d7yMjfPO5lw66u8sHnt", "松菸文創店")

    // Get Firebase salesman  Data
    private val _salesmans = MutableLiveData<List<Salesman>>()
    val salemans: LiveData<List<Salesman>>
        get() = _salesmans



    //SalesmanChosen spinner
    val selectedSalesmanPosition = MutableLiveData<Int>()
    val selectedSalesman: LiveData<Salesman> = Transformations.map(selectedSalesmanPosition) {
        salemans.value!![it]
    }

    private val _navigateToCalendar = MutableLiveData<Salesman>()
    val navigateToCalendar : LiveData<Salesman>
        get() = _navigateToCalendar



    // Handle navigation to login success
    private val _navigateToLoginSuccess = MutableLiveData<Salesman>()

    val navigateToLoginSuccess: LiveData<Salesman>
        get() = _navigateToLoginSuccess

    // Handle leave login
    private val _loginTWM = MutableLiveData<Boolean>()

    val loginTWM: LiveData<Boolean>
        get() = _loginTWM

    // Handle leave login
    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

//    lateinit var twmCallbackManager: CallbackManager

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {

    }

    init {
        getSalesmansResult()
    }

    fun getSalesmansResult(){
        coroutineScope.launch{
            _status.value = LoadApiStatus.LOADING
            val result = repository.getSalesmansResult()
            _salesmans.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    Log.i("HsiaoLing","salesmans=${result.data}")
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





//    fun click (){
//        if (salesman.value != null ){
////            getSalesman(salesman.value!!)
//            Log.i("HsiaoLingUpdate", "UpateNewData=${salesman.value}")
//
//        }
//    }

    fun navigateToCalendar(salesman: Salesman) {
        _navigateToCalendar.value = salesman
    }


    fun leave() {
        _leave.value = true
    }

    fun onLeaveCompleted() {
        _leave.value = null
    }

    fun nothing() {}

//    fun onLoginFacebookCompleted() {
//        _loginFacebook.value = null
//    }
}