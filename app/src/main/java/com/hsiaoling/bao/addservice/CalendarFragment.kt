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
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.databinding.CalendarFragmentBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.master.MasterAdapter
import kotlinx.android.synthetic.main.calendar_fragment.*


class CalendarFragment : Fragment() {


    private val viewModel by viewModels<CalendarViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = CalendarFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //  val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            //  Toast.makeText(context, msg, Toast.LENGTH_LONG).show()

            // get date
            val selectedDate: String = "" + year + "-" + (month + 1) + "-" + dayOfMonth
            viewModel.selectedDate(selectedDate)

            Log.i("SelectedDate", "selectedDay=${selectedDate}")
        }

//        val masters = mutableListOf<Master>()
//        val masterA = Master("9527", "Jimmy")
//        val masterB = Master("9528", "Jerry")
//        val masterC = Master("9529", "Angela")
//        masters.add(masterA)
//        masters.add(masterB)
//        masters.add(masterC)

        //  observe  masters in CalenderViewModel getMasterResult , start Master viewpager  with masterResult
        viewModel.masters.observe(this, Observer {
            Log.i("HsiaoLing","viewModel.masters.observe, it=$it")
            it?.let { masters ->

                binding.viewpagerMaster.let {
                    binding.tabsMaster.setupWithViewPager(it)
                    it.adapter = MasterAdapter(childFragmentManager, masters)
                    it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabsMaster))
                }
            }
        })

        return binding.root
    }
}


