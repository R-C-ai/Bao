package com.hsiaoling.bao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hsiaoling.bao.addservice.AddBaoViewModel
import com.hsiaoling.bao.addservice.CalendarViewModel
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.login.LoginViewModel
import com.hsiaoling.bao.master.MasterJobUpdateViewModel
import com.hsiaoling.bao.master.MasterStatusViewModel
import com.hsiaoling.bao.master.dailyItem.MasterDailyItemViewModel
import com.hsiaoling.bao.salesaomunt.SalesAmountViewModel
import com.hsiaoling.bao.servicestatus.ServiceStatusViewModel
import com.hsiaoling.bao.servicestatus.StatusInfoViewModel
import com.hsiaoling.bao.servicestatus.StatusUpdateViewModel

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val baoRepository: BaoRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(SalesAmountViewModel::class.java) ->
                    SalesAmountViewModel(baoRepository)

                isAssignableFrom(MasterJobUpdateViewModel::class.java) ->
                    MasterJobUpdateViewModel(baoRepository)

                isAssignableFrom(StatusInfoViewModel::class.java) ->
                    StatusInfoViewModel(baoRepository)

                isAssignableFrom(StatusUpdateViewModel::class.java) ->
                    StatusUpdateViewModel(baoRepository)

                isAssignableFrom(ServiceStatusViewModel::class.java) ->
                   ServiceStatusViewModel(baoRepository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(baoRepository)


                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(baoRepository)

                isAssignableFrom(MasterDailyItemViewModel::class.java) ->
                    MasterDailyItemViewModel(baoRepository)

                isAssignableFrom(AddBaoViewModel::class.java) ->
                    AddBaoViewModel(baoRepository)

                isAssignableFrom(CalendarViewModel::class.java) ->
                    CalendarViewModel(baoRepository)

                isAssignableFrom(MasterStatusViewModel::class.java) ->
                    MasterStatusViewModel(baoRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
