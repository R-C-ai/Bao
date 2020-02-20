package com.hsiaoling.bao

import android.app.Service
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.login.SalesManManager
import com.hsiaoling.bao.data.Salesman
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

    private val _salesman = MutableLiveData<Salesman>()
    val salesman: LiveData<Salesman>
        get() = _salesman

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


    // Handle navigation to home by bottom nav directly which includes icon change
//    private val _navigateToHomeByBottomNav = MutableLiveData<Boolean>()
//
//    val navigateToHomeByBottomNav: LiveData<Boolean>
//        get() = _navigateToHomeByBottomNav

    // check user login status
    val isLoggedIn
        get() = SalesManManager.isLoggedIn



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
    fun setupSalesman(salesman: Salesman) {

        _salesman.value = salesman

        Log.i("Hsiao","| setupUser |")
        Log.i("Hsiao","user=$salesman")
        Log.i("Hsiao","MainViewModel=${this}")

    }

    fun checkSalesman() {
        if (salesman.value == null) {
            SalesManManager.salesmanToken?.let {
//                getUserProfile(it)
            }
        }
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
//                        SalesManManager.clear()
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