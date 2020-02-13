package com.hsiaoling.bao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hsiaoling.bao.addservice.AddBaoViewModel
import com.hsiaoling.bao.addservice.CalendarViewModel
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.master.dailyItem.MasterDailyItemViewModel

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
                isAssignableFrom(MasterDailyItemViewModel::class.java) ->
                    MasterDailyItemViewModel(baoRepository)

                isAssignableFrom(AddBaoViewModel::class.java) ->
                    AddBaoViewModel(baoRepository)

                isAssignableFrom(CalendarViewModel::class.java) ->
                    CalendarViewModel(baoRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
