package com.hsiaoling.bao.addservice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.data.Day
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CalendarViewModel : ViewModel() {

    private val _day = MutableLiveData<Day>()
    val day: LiveData<Day>
        get() = _day

    private val _navigateToAddNewJob = MutableLiveData<Day>()
    val navigateToAddNewJob:LiveData<Day>
    get()=_navigateToAddNewJob

    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun navToAddNewJob(day: Day){
        _navigateToAddNewJob.value = day
    }
    fun onAddNewJobNavigated() {
        _navigateToAddNewJob.value = null
    }

}
