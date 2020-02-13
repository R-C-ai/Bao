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
import com.hsiaoling.bao.addservice.CalendarFragment
import com.hsiaoling.bao.addservice.CalendarViewModel
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.FragmentMasterDailyItemBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.master.MasterTypeFliter

class MasterDailyItemFragment(private val masterType:MasterTypeFliter):Fragment() {

    private val viewModel by viewModels<MasterDailyItemViewModel> { getVmFactory() }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
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

//        val service = BaoService(0, "iPhone X", "", "", 1)
//        val service2 = BaoService(1, "iPhone XR", "", "", 1)
//        val list = mutableListOf<BaoService>()
//        list.add(service)
//        list.add(service2)
//        (binding.recyclerMasterDailyItem.adapter as MasterDailyItemAdapter).submitList(list)



        viewModel.navgateToAddBao.observe(this, Observer {
            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalAddBaoDialog(it))
                viewModel.onAddJobNavigated()
            }
        })
//
        val parentViewModel = ViewModelProviders.of(parentFragment!!).get(CalendarViewModel::class.java)

        parentViewModel.date.observe(parentFragment as CalendarFragment, Observer {
            Log.i("HsiaoLing","getDate=${it}")
            Log.i("HsiaoLing","masterType.value=${masterType.value}")
            viewModel.getDateResult(it)
        })





//        ViewModelProviders.of(activity!!).get(MasterDailyItemViewModel::class.java).apply {
//            refresh.observe(this@MasterDailyItemFragment, Observer {
//                it?.let {
//                    viewModel.refresh()
//                    onRefreshed()
//                }
//            })
//        }


//        viewModel.navgateToAddBao.observe(this, Observer {
//            it?.let{
//                findNavController().navigate(NavigationDirections.actionGlobalAddJobFragment(it))
//                viewModel.onAddJobNavigated()
//            }
//        })


        return binding.root
    }

}