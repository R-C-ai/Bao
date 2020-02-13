package com.hsiaoling.bao.addservice

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.hsiaoling.bao.NavigationDirections
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Date
import com.hsiaoling.bao.databinding.CalendarFragmentBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.master.MasterAdapter
import kotlinx.android.synthetic.main.calendar_fragment.*


class CalendarFragment : Fragment() {

//    companion object {
//        fun newInstance() = CalendarFragment()
//    }

    private val viewModel by viewModels<CalendarViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = CalendarFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //            val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
//            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            val selectedDate = "$year-$month-$dayOfMonth"
            viewModel.selectedDate(selectedDate)

            Log.i("SelectedDate", "selectedDay=${selectedDate}")


//            viewModel.navToAddNewJob(newDay)
//binding.viewpagerMaster

        }

        binding.viewpagerMaster.let {
            binding.tabsMaster.setupWithViewPager(it)
            it.adapter = MasterAdapter(childFragmentManager)
            it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabsMaster))
        }

//        viewModel.navigateToAddNewJob.observe(this, Observer {
//            it?.let{
//                    findNavController().navigate(NavigationDirections.actionGlobalAddNewJobFragment(it))
//                viewModel.onAddNewJobNavigated()
//            }
//        })


        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//

}


