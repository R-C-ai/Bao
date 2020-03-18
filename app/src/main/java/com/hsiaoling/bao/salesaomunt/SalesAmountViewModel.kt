package com.hsiaoling.bao.salesaomunt


import android.icu.util.Calendar
import android.util.Log
import androidx.databinding.InverseMethod
import androidx.lifecycle.*
import com.hsiaoling.bao.data.*
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import com.hsiaoling.bao.ext.toDayFormat
import com.hsiaoling.bao.login.UserManager
import java.text.SimpleDateFormat
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.github.mikephil.charting.data.BarEntry


class SalesAmountViewModel(private val repository: BaoRepository) : ViewModel() {


    // get  Rev
    private var _rev = MutableLiveData<List<Service>>()
    val rev: LiveData<List<Service>>
        get() = _rev

    private var _serviceList = MutableLiveData<List<List<Service>>>()
    val serviceList: LiveData<List<List<Service>>>
        get() = _serviceList

    var currentday = Calendar.getInstance().getTime()
    var today = this.currentday.time.toDayFormat()
    var todayTimeStamp = SimpleDateFormat("yyyy-M-d").parse(today).time





    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus


//    private val _navigateToAddSuccess = MutableLiveData<Service>()
//    val navigateToAddSuccess: LiveData<Service>
//        get() = _navigateToAddSuccess
//
//    private val _navigateToAddedFail = MutableLiveData<Service>()
//    val navigateToAddedFail: LiveData<Service>
//        get() = _navigateToAddedFail
//
//    private val _navigateToDeleteSuccess = MutableLiveData<Service>()
//    val navigateToDeleteSuccess: LiveData<Service>
//        get() = _navigateToDeleteSuccess
//
//    private val _navigateToDeletedFail = MutableLiveData<Service>()
//    val navigateToDeletedFail: LiveData<Service>
//        get() = _navigateToDeletedFail


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


        val firstMarch = SimpleDateFormat("yyyy-M-d").parse("2020-3-1").time
        val firstApr = SimpleDateFormat("yyyy-M-d").parse("2020-4-1").time


        getLiveRev(UserManager.user!!, firstMarch, firstApr)



    }


    // put loginUser service
    fun getLiveRev(user: User, firstDay: Long, endDay: Long) {
        _rev =
            repository.getLiveRev(user, firstDay, endDay) as MutableLiveData<List<Service>>
        Log.i("HsiaoLing", "currentdaytest=$firstDay,$endDay")

    }

    val firstJan = SimpleDateFormat("yyyy-M-d").parse("2020-1-1").time
    val firstFeb = SimpleDateFormat("yyyy-M-d").parse("2020-2-1").time
    val firstMay = SimpleDateFormat("yyyy-M-d").parse("2020-5-1").time
    val firstJune = SimpleDateFormat("yyyy-M-d").parse("2020-6-1").time

    fun getTodayTimeStamp() {
        todayTimeStamp
    }


    val uptoDateRev: LiveData<Long> = Transformations.map(rev) {
        var uptoDateRev = 0L
        rev.value?.let {
            for (service in it) {
                Log.i("HsiaoLing", "  todayTimeStamp =$todayTimeStamp")

                if (service.doneTime <= todayTimeStamp) {
                    service.price.let { price ->
                        uptoDateRev += (service.price)
                        Log.i("HsiaoLing", " service.doneTime <  todayTimeStamp =$uptoDateRev")
                    }
                }
            }
        }
        Log.i("HsiaoLing", "currentdaytest=${rev.value?.size}")
        Log.i("HsiaoLing", "  uptoDateRev =$uptoDateRev")
        uptoDateRev

    }


    fun getServicesByDayGroup(list: List<Service>, month: Int, year: Int): List<List<Service>> {
        Log.i("HsiaoLing", "  list =$list")
        val servicesList = mutableListOf<List<Service>>()
        val daysOfMonth = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> 28
            else -> 0
        }

        // create servicesList index ,they are days in a month
        for (day in 1..daysOfMonth) {
            servicesList.add(mutableListOf<Service>())
        }

        // put service list of the day into servicesList by day ( the donetime day of the service is the servicesList day)
        for (service in list) {
            val dayFormat = service.doneTime.toDayFormat()

            for (day in 1..daysOfMonth) {

                Log.i("HsiaoLing", " dayFormat1=$year-$month-$day")
                Log.i("HsiaoLing", "dayFormat=$dayFormat")

                if ("$year-$month-$day" == dayFormat) {
                    // the start index of function is 0 , so need to do day-1
                    (servicesList[day - 1] as MutableList).add(service)
                    Log.i("HsiaoLing", " dayFormat1=$year-$month-$day")
                    Log.i("HsiaoLing", "dayFormat=$dayFormat")
                    Log.i("HsiaoLing", " servicesList[${day - 1}]=${servicesList[day - 1]}")
                }
            }
        }
        Log.i("HsiaoLing", "  servicesList =$servicesList")
        Log.i("HsiaoLing", "  servicesList.size =${servicesList.size}")
        return servicesList

    }

    fun getBarEntries(list: List<List<Service>>):ArrayList<BarEntry>{
        val entries = ArrayList<BarEntry>()
        for ((index   , dayList) in list.withIndex()){
            if (dayList.isNotEmpty()){
                var dayRev: Long = 0
                var label = dayList[0].doneTime.toDayFormat()
                for (service in dayList){
                    Log.i("HsiaoLing", "  dayList =$dayList")
                    service.price.let{
                        dayRev += (service.price)
                    }

                }
                Log.i("HsiaoLing", "  dayRev =$dayRev")

                var barEntry = BarEntry(index.toFloat(),dayRev.toFloat())

                Log.i("HsiaoLing", "  barEntry =$barEntry")

                entries.add(barEntry)
        }}
        return entries
    }







//    val dayRev:LiveData<Long> =


//    fun getRev(date: String, userType: UserType) {
//
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//
//            val result = repository.getDateRevResult(date, userType)
//
//            Log.i("HsiaogetRev", " userType=${userType}")
//
//            _rev.value = when (result) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    result.data
//
//                    Log.i("HsiaogetRev", " _rev.value=${}")
//
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
//        }
//    }


//    fun onAddedSuccessNavigated() {
//        _navigateToAddSuccess.value = null
//    }
//
//    fun onAddedFailNavigated() {
//        _navigateToAddedFail.value = null
//    }

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

