package com.hsiaoling.bao

import android.app.Service
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.data.Day
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.User
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.ext.toCurrentFormat
import com.hsiaoling.bao.ext.toDayFormat
import com.hsiaoling.bao.ext.toMonthFormat
import com.hsiaoling.bao.ext.toYearFormat
import com.hsiaoling.bao.login.DayManager
import com.hsiaoling.bao.login.UserManager
import com.hsiaoling.bao.login.UserManager.user
import com.hsiaoling.bao.network.LoadApiStatus
import com.hsiaoling.bao.util.CurrentFragmentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [MainActivity].
 */
class MainViewModel(private val repository: BaoRepository) : ViewModel() {

    // setup UserManager
    private val _loginUser = MutableLiveData<User?>()
    val loginUser: LiveData<User?>
        get() = _loginUser


    // setup DayManager
    private val _thisTime = MutableLiveData<Day?>()
    val thisTime: LiveData<Day?>
        get() = _thisTime

    var currentday = Calendar.getInstance().getTime()
    var today = this.currentday.time?.toCurrentFormat()



    var todayTimeStamp = SimpleDateFormat("yyyy-M-d hh:mm").parse(today).time

    var currentMonth = currentday.time?.toMonthFormat().toInt()
    var currentYear = currentday.time?.toYearFormat().toInt()
    var currentDay = currentday.time?.toDayFormat()

    var firstDay: Long = 0L
    var endDay: Long = 0L


    // Count number for bottom badge as liveData
    var liveStatuses = MutableLiveData<List<com.hsiaoling.bao.data.Service>>()

    //  this liveStatus is null when first time login (because there is nothing to addSnapshotListener)
    var countStatus1: LiveData<Int>? = null


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
        get() = _navigateToSalesAmountByBottomNav

    // Handle navigation to servicestatus by bottom nav directly which includes icon change
    private val _navigateToMasterStatusByBottomNav = MutableLiveData<Service>()
    val navigateToMasterStatusByBottomNav: LiveData<Service>
        get() = _navigateToMasterStatusByBottomNav


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
        setCurrentMonth()
    }

    fun setCurrentMonth() {
        when {
            currentMonth < 12 -> {
                firstDay = SimpleDateFormat("yyyy-M-d").parse("$currentYear-$currentMonth-1").time
                endDay =
                    SimpleDateFormat("yyyy-M-d").parse("$currentYear-${currentMonth + 1}-1").time
            }
            else -> {
                firstDay = SimpleDateFormat("yyyy-M-d").parse("$currentYear-$currentMonth-1").time
                endDay = SimpleDateFormat("yyyy-M-d").parse("${currentYear + 1}-1-1").time
            }
        }
        Log.e("HsiaoLing", "currentMonth=$firstDay,$endDay")
        Log.e("HsiaoLing", "_thisTime.value=${_thisTime.value}")
        _thisTime.value = Day(todayTimeStamp, firstDay, endDay)


    }


    // get live status from firebase by loginUser

//    fun getLiveStatus(){
//        when(UserManager.user!!.type){
//            "master" -> {
//                repository.getMasterLiveStatus(UserManager.user!!.id) {
//                    liveStatuses.value = it
//                }
//            }else ->{
//            repository.getSalesmanLiveStatus(UserManager.user!!.id) {
//                liveStatuses.value = it
//            }
//        }
//        }
//
//        Log.i("HsiaoLing","getLiveStatus=${liveStatuses.value}")
//    }

    fun getMonthLiveStatus() {
        Log.i("HsiaoLing", "getLiveM user!=${UserManager.user!!}")
        liveStatuses =
            repository.getLiveM(
                UserManager.user!!,
                firstDay,
                endDay
            ) as MutableLiveData<List<com.hsiaoling.bao.data.Service>>

        // set the most updated liveStatus to count for badgeView
        setCountStatus1()
    }

        // get the count for liveStatus
    fun setCountStatus1() {
        countStatus1 = Transformations.map(liveStatuses) {
            it?.let {
                it.filter {
                    it.status == 1
                }
            }?.size ?: 0
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

    fun onMasterStatusNavigated() {
        _navigateToMasterStatusByBottomNav.value = null
    }


}