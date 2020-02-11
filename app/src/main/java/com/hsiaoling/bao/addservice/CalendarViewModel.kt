package com.hsiaoling.bao.addservice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.data.Date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CalendarViewModel : ViewModel() {

    private val _date = MutableLiveData<Date>()
    val date: LiveData<Date>
        get() = _date

    private val _navigateToAddNewJob = MutableLiveData<Date>()
    val navigateToAddNewJob:LiveData<Date>
    get()=_navigateToAddNewJob

    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun navToAddNewJob(date: Date){
        _navigateToAddNewJob.value = date
    }
    fun onAddNewJobNavigated() {
        _navigateToAddNewJob.value = null
    }

}
