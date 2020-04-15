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
import com.hsiaoling.bao.login.UserManager
import java.text.SimpleDateFormat
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.github.mikephil.charting.data.BarEntry
import com.hsiaoling.bao.ext.*
import com.hsiaoling.bao.login.DayManager


class SalesAmountViewModel(private val repository: BaoRepository) : ViewModel() {


    // get  Rev
    private var _rev = MutableLiveData<List<Service>>()
    val rev: LiveData<List<Service>>
        get() = _rev

    var  currentUser = UserManager.user!!


    // Get Input Update Service Status  LiveData
    private val _service = MutableLiveData<Service>()
    val service: LiveData<Service>
        get() = _service as LiveData<Service>


    // get current time
    var currentday = Calendar.getInstance().getTime()

    // set current time to Day formate to display
    var currentDay = currentday.time.toDayFormat()

    var firstDay = DayManager.day!!.firstDay
    var endDay = DayManager.day!!.endDay
    var todayTimeStamp = DayManager.day!!.todayTimeStamp

    private var _serviceList = MutableLiveData<List<List<Service>>>()
    val serviceList: LiveData<List<List<Service>>>
        get() = _serviceList

    private var _cumR = MutableLiveData<Long>()
    val cumR: LiveData<Long>
        get() = _cumR



    //Year Chosen spinner
//    val selectedYearPosition = MutableLiveData<Int>()
    // change livedata by Transformation.map to selectedvalue
//    val yearChosen: LiveData<YearChosen> = Transformations.map(selectedYearPosition) {
//        val selectedYear = it.toString()
//        Log.i("HsiaoLing", "chosenYear=$selectedYear")
//        Log.i("HsiaoLing", "chosenYear=${YearChosen.values()[it]}")
//        // get the YearChosen value  by the corresponding position
//        YearChosen.values()[it]
//    }



    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus





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

//        setCurrentMonth()
        getLiveRev(currentUser, firstDay, endDay)


    }




    // put loginUser service for currentMonth
    fun getLiveRev(user: User, firstDay: Long, endDay: Long) {
        _rev =
            repository.getLiveRev(user, firstDay, endDay) as MutableLiveData<List<Service>>

    }

//    fun setCurrentMonth(){
//        when{
//            currentMonth < 12 ->{
//                firstDay = SimpleDateFormat("yyyy-M-d").parse("$currentYear-$currentMonth-1").time
//                endDay =  SimpleDateFormat("yyyy-M-d").parse("$currentYear-${currentMonth+1}-1").time
//            }
//            else -> {firstDay = SimpleDateFormat("yyyy-M-d").parse("$currentYear-$currentMonth-1").time
//                endDay =  SimpleDateFormat("yyyy-M-d").parse("${currentYear+1}-1-1").time
//            }
//        }
//        Log.e("HsiaoLing", "currentMonth=$firstDay,$endDay")
//    }


    val uptoDateRev: LiveData<Long> = Transformations.map(rev) {

        Log.i("HsiaoLing", "repository.getLiveRev uptoDateRev=$currentUser,$firstDay,$endDay")
        Log.i("HsiaoLing", "uptoDateRev rev=$it")
        Log.i("HsiaoLing", "uptoDateRev rev.size=${it.size}")

        var uptoDateRev: Long = 0

        for (service in it) {

//            if (service.finishCheckTime <= todayTimeStamp) {
                rev.value?.let {

                    Log.i("HsiaoLing", "repository.getLiveRev  uptoDateRev for=$it")
                    Log.i("HsiaoLing", " repository.getLiveRev uptoDateRev time =$todayTimeStamp")

                    service.price.let { price ->
                        uptoDateRev += (service.price)

                        Log.i("HsiaoLing", "repository.getLiveRev uptoDateRev =$uptoDateRev")
                    }
                }

        }

        Log.i("HsiaoLing", "uptoDateRev rev.value?.size =${rev.value?.size}")
        Log.i("HsiaoLing", "uptoDateRev =$uptoDateRev")

        uptoDateRev
    }
















//    val uptoDateRev: LiveData<Long> = Transformations.map(rev) {
//        Log.i("HsiaoLing", "repository.getLiveRev uptoDateRev=$currentUser,$firstDay,$endDay")
//        Log.i("HsiaoLing", "uptoDateRev rev=$it")
//        Log.i("HsiaoLing", "uptoDateRev rev.size=${it.size}")
//
//        var uptoDateRev :Long = 0
//        Log.i("HsiaoLing", "repository.getLiveRev uptoDateRev =$uptoDateRev")
//        rev.value?.let {
//            for (service in it) {
//                Log.i("HsiaoLing", "repository.getLiveRev  uptoDateRev for=$it")
//                Log.i("HsiaoLing", " repository.getLiveRev uptoDateRev time =$todayTimeStamp")
//
////                if (service.finishCheckTime <= todayTimeStamp)
//                    service.price.let { price ->
//                        uptoDateRev += (service.price)
//                        Log.i("HsiaoLing",
//                            " uptoDateRev.finishCheckTime <= todayTimeStamp=${service.finishCheckTime},$todayTimeStamp"
//                        )
//                }
//            }
//        }
//        Log.i("HsiaoLing", "uptoDateRev rev.value?.size =${rev.value?.size}")
//        Log.i("HsiaoLing", "uptoDateRev =$uptoDateRev")
//        uptoDateRev
//    }
//

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

        // put service list of the day into servicesList by day ( the finishtime day of the service is the servicesList day)
        for (service in list) {
            val dayFormat = service.updateTime.toDayFormat()

            for (day in 1..daysOfMonth) {

                Log.i("HsiaoLing", " dayFormat1=$year-$month-$day")
                Log.i("HsiaoLing", "dayFormat=$dayFormat")

                if ("$year-$month-$day" == dayFormat) {
                    // the start index of function is 0 , so need to do day-1
                   //
                    (servicesList[day-1] as MutableList).add(service)
                    Log.i("HsiaoLing", " dayFormat1=$year-$month-$day")
                    Log.i("HsiaoLing", "dayFormat=$dayFormat")
                    Log.i("HsiaoLing", " dayFormat servicesList[${day-1 }]=${servicesList[day-1 ]}")
                }
            }
        }
        Log.i("HsiaoLing", "  servicesList =$servicesList")
        Log.i("HsiaoLing", "  servicesList.size =${servicesList.size}")
        return servicesList
    }

    // get everyday revenue and put into BarEntry for barchart data
    fun getBarEntries(list: List<List<Service>>):ArrayList<BarEntry>{
        val entries = ArrayList<BarEntry>()
        for ((index   , dayList) in list.withIndex()){
            if (dayList.isNotEmpty()){
                var dayRev: Long = 0
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

    // get everyday cumRevenue and put into BarEntry for linechart data
    fun getCumRevBarEntries(list: List<List<Service>>):ArrayList<BarEntry>{
        val cumRevEntries = ArrayList<BarEntry>()
        var cumdayRev: Long = 0
        for ((index   , dayList) in list.withIndex()){
            if (dayList.isNotEmpty()){

                for (service in dayList){
                    Log.i("HsiaoLing", "  dayList =$dayList")
                    service.price.let{
                        cumdayRev += (service.price)
                    }
                }
                Log.i("HsiaoLing", "  dayRev =$cumdayRev")

                var cumBarEntry = BarEntry(index.toFloat(),cumdayRev.toFloat())

                Log.i("HsiaoLing", "  barEntry =$cumBarEntry")

                cumRevEntries.add(cumBarEntry)

            }}
        Log.i("HsiaoLing", "cumRevEntries.add(cumBarEntry)=$cumRevEntries")
        return cumRevEntries
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


    fun onRefreshed() {
        _refreshStatus.value = null
    }


    fun onLeaveCompleted() {
        _leave.value = null
    }

    fun nothing() {}


}
//
//enum class YearChosen(val positionOnSpinner: Int) {
//
//    YEAR_2020(0),
//    YEAR_2021(1),
//    YEAR_2022(2),
//    YEAR_2023(3),
//    YEAR_2024(4),
//    YEAR_2025(5),
//    YEAR_2026(6),
//    YEAR_2027(7),
//    YEAR_2028(8),
//    YEAR_2029(9),
//    YEAR_2030(10)
//}