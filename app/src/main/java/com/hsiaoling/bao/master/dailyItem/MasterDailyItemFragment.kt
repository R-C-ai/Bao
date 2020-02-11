package com.hsiaoling.bao.master.dailyItem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.hsiaoling.bao.NavigationDirections
import com.hsiaoling.bao.data.BaoService
import com.hsiaoling.bao.data.Schedule
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.FragmentMasterDailyItemBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.master.MasterAdapter
import com.hsiaoling.bao.master.MasterTypeFliter

class MasterDailyItemFragment(private val masterType:MasterTypeFliter):Fragment() {

    private val viewModel by viewModels<MasterDailyItemViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentMasterDailyItemBinding = FragmentMasterDailyItemBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val list = mutableListOf<Service>()

        binding.recyclerMasterDailyItem.adapter = MasterDailyItemAdapter(
            MasterDailyItemAdapter.OnClickListener{
                viewModel.navgateToAddBao(it)
            }
        )


//        val schedule = Schedule ("0","可預約圖","可預約" ,0)
//        val schedule1 = Schedule ("1","已預約圖","已預約",1)
//        val list:MutableList<Schedule> = mutableListOf<Schedule>()
//        list.add(schedule)
//        list.add(schedule1)

//        val service = BaoService(0, "iPhone X", "", "", 1)
//        val service2 = BaoService(1, "iPhone XR", "", "", 1)
//        val list = mutableListOf<BaoService>()
//        list.add(service)
//        list.add(service2)
//        (binding.recyclerMasterDailyItem.adapter as MasterDailyItemAdapter).submitList(list)

//        binding.recyclerMasterDailyItem.adapter = MasterDailyItemAdapter()

        viewModel.navgateToAddBao.observe(this, Observer {
            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalAddBaoFragment())
                viewModel.onAddJobNavigated()
            }
        })


//        viewModel.navgateToAddBao.observe(this, Observer {
//            it?.let{
//                findNavController().navigate(NavigationDirections.actionGlobalAddJobFragment(it))
//                viewModel.onAddJobNavigated()
//            }
//        })


        return binding.root
    }

}