package com.hsiaoling.bao.addservice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.BaoApplication

import com.hsiaoling.bao.data.*
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import android.icu.text.SimpleDateFormat

import android.icu.util.Calendar
import com.hsiaoling.bao.data.Date
import com.hsiaoling.bao.ext.toDayFormat


class CalendarViewModel(private val repository: BaoRepository) : ViewModel() {

    private val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    fun selectedDate (date: String){
        _date.value = date
    }



    private val _getDayStatus = MutableLiveData<LoadApiStatus>()
    val getDayStatus:LiveData<LoadApiStatus>
    get()=_getDayStatus

    private val _schedules = MutableLiveData<List<Service>>()
    val schedules: LiveData<List<Service>>
        get() = _schedules

    private val _masters = MutableLiveData<List<User>>()
    val masters: LiveData<List<User>>
        get() = _masters


    //define var to get  today and transfer to String
    var currentday = Calendar.getInstance().getTime()
    var today =this.currentday.time.toDayFormat()


    var transToday = SimpleDateFormat("yyyy-M-d").parse(today).time


    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus


    private val _navigateToAddNewJob = MutableLiveData<Date>()
    val navigateToAddNewJob:LiveData<Date>
    get()=_navigateToAddNewJob

    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        getToday()
        getMastersResult()



    }

    fun getMastersResult(){
        coroutineScope.launch{
            _status.value = LoadApiStatus.LOADING
            val result = repository.getMastersResult()
            _masters.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    Log.i("HsiaoLing","master=${result.data}")
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
                    _error.value = BaoApplication.instance.getString(com.hsiaoling.bao.R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }



   fun getToday()  {
       currentday.time.toDayFormat()
       Log.i("HsiaoLing","currentday=${ currentday.time.toDayFormat()}")
       Log.i("HsiaoLing","currentday=${ currentday.time}")
//       transformToday
        transToday
       Log.i("HsiaoLing","currentdaytest=${transToday}")
    }













//
//    fun getDateResult(date: String){
//        coroutineScope.launch{
//            _status.value = LoadApiStatus.LOADING
//            val result = repository.getDateResult("")
//
//            _schedules.value = when (result) {
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
//
//
//        }
//    }


    fun navToAddNewJob(date: Date){
        _navigateToAddNewJob.value = date
    }
    fun onAddNewJobNavigated() {
        _navigateToAddNewJob.value = null
    }

}
