package com.hsiaoling.bao

import android.app.Service
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.User
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import com.hsiaoling.bao.util.CurrentFragmentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [MainActivity].
 */
class MainViewModel(private val repository: BaoRepository) : ViewModel() {

    private val _loginSalesman = MutableLiveData<User?>()
    val loginSalesman: LiveData<User?>
        get() = _loginSalesman

    // Handle navigation to login success
    private val _navigateToLoginSuccess = MutableLiveData<Salesman>()
    val navigateToLoginSuccess: LiveData<Salesman>
        get() = _navigateToLoginSuccess

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

    // Handle navigation to servicestatus by bottom nav directly which includes icon change
    private val _navigateToAddServiceByBottomNav = MutableLiveData<Service>()
    val navigateToAddServiceByBottomNav: LiveData<Service>
        get() = _navigateToAddServiceByBottomNav

    // Handle navigation to servicestatus by bottom nav directly which includes icon change
    private val _navigateToServiceStatusByBottomNav = MutableLiveData<Service>()
    val navigateToServiceStatusByBottomNav: LiveData<Service>
        get() = _navigateToServiceStatusByBottomNav

    // Handle navigation to servicestatus by bottom nav directly which includes icon change
    private val _navigateToSalesAmountByBottomNav = MutableLiveData<Service>()
    val navigateToSalesAmountByBottomNav: LiveData<Service>
        get() =  _navigateToSalesAmountByBottomNav




    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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
    fun setupSalesman(user: User) {

        _loginSalesman.value = user

        Log.i("Hsiao","| setupl=LoginSalesman |")
        Log.i("Hsiao","user=$user")
        Log.i("Hsiao","MainViewModel=${this}")

    }





    fun navigateToLoginSuccess(salesman: Salesman) {
        _navigateToLoginSuccess.value = salesman
    }

    fun onLoginSuccessNavigated() {
        _navigateToLoginSuccess.value = null
    }



    fun navigateToAddServiceByBottomNav(service: Service) {
        _navigateToAddServiceByBottomNav.value = service
    }
    fun onAddServiceNavigated() {
        _navigateToAddServiceByBottomNav.value = null
    }

    fun navigateToServiceStatusByBottomNav(service: Service) {
        _navigateToServiceStatusByBottomNav.value = service
    }
    fun onServiceStatusNavigated() {
        _navigateToServiceStatusByBottomNav.value = null
    }

    fun navigateToSalesAmountByBottomNav(service: Service) {
        _navigateToSalesAmountByBottomNav.value = service
    }
    fun onSalesAmountNavigated() {
        _navigateToSalesAmountByBottomNav.value = null
    }



//    private fun getSalesmanProfile(token: String) {
//
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//            // It will return Result object after Deferred flow
//            val result = stylishRepository.getUserProfile(token)
//
//            _user.value = when (result) {
//
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    result.data
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                    if (result.error.contains("Invalid Access Token", true)) {
//                        SalesmanManager.clear()
//                    }
//                    null
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                else -> {
//                    _error.value = getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//            }
//        }
//    }
}