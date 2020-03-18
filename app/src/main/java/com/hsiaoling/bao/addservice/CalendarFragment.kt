package com.hsiaoling.bao.addservice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.hsiaoling.bao.databinding.CalendarFragmentBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.ext.toDayFormat
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.login.UserManager
import com.hsiaoling.bao.master.MasterAdapter


class CalendarFragment : Fragment() {


    private val viewModel by viewModels<CalendarViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = CalendarFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Log.i("Hsiao","SalesmanManager.salesman=${SalesmanManager.salesman}")

        // get the calendar
        binding.calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //  val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            //  Toast.makeText(context, msg, Toast.LENGTH_LONG).show()

            // get date on click  the calendar day
            val selectedDate: String = "" + year + "-" + (month + 1) + "-" + dayOfMonth
            // put the click day into selectedDate
            viewModel.selectedDate(selectedDate)

            Log.i("SelectedDate", "selectedDay=${selectedDate}")
        }

            // get today  when navigate to calendarFragment without click
        val today =viewModel.currentday.time.toDayFormat()
            //put today into selectedDate
        viewModel.selectedDate((today))


        //  observe  masters in CalenderViewModel getMasterResult , start Master viewpager  with masterResult
        viewModel.masters.observe(this, Observer {
            Log.i("HsiaoLing","viewModel.masters.observe, it=$it")
            it?.let { masters ->

                binding.viewpagerMaster.let {
                    binding.tabsMaster.setupWithViewPager(it)

                    UserManager.user?.let { user ->
                        if (user.type == "master") {
                            it.adapter = MasterAdapter(childFragmentManager, listOf(user))
                        } else {
                            it.adapter = MasterAdapter(childFragmentManager, masters)
                        }
                    }
                    it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabsMaster))
                }
            }
        })

        return binding.root
    }
}





//        val masters = mutableListOf<Master>()
//        val masterA = Master("9527", "Jimmy")
//        val masterB = Master("9528", "Jerry")
//        val masterC = Master("9529", "Angela")
//        masters.add(masterA)
//        masters.add(masterB)
//        masters.add(masterC)