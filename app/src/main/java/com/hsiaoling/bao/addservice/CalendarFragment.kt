package com.hsiaoling.bao.addservice

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
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
import com.hsiaoling.bao.master.MasterAdapter
import kotlinx.android.synthetic.main.calendar_fragment.*


class CalendarFragment : Fragment() {

//    companion object {
//        fun newInstance() = CalendarFragment()
//    }

    private val   viewModel by lazy {
        ViewModelProviders.of(this).get(CalendarViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding= CalendarFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
//            val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
//            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
//            val selectedDate= Date(year,month,dayOfMonth)

//            viewModel.navToAddNewJob(newDay)

         viewpager_master.let{
             tabs_master.setupWithViewPager(it)
             it.adapter = MasterAdapter(childFragmentManager)
             it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs_master))
         }
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


