package com.hsiaoling.bao.servicestatus


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.FragmentServiceStatusBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.master.dailyItem.MasterDailyItemViewModel

/**
 * A simple [Fragment] subclass.
 */
class ServiceStatusFragment : Fragment() {


    private val viewModel by viewModels<ServiceStatusViewModel> { getVmFactory() }
//    private var service: Service? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentServiceStatusBinding .inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        val serviseStatusItemAdapter =ServiceStatusItemAdapter(
//            ServiceStatusItemAdapter.OnClickListener{
//            }
//        )
//        binding.recyclerStatusItem.adapter = serviseStatusItemAdapter

        viewModel.getLiveStatus()
        Log.i("Hsiao","getLiveStatus=${viewModel.liveStatuses.value}")
        viewModel.liveStatuses.observe(this, Observer {
            Log.i("Hsiao","liveStatuses.observe=${viewModel.liveStatuses.value}")

            Log.i("HsiaoLing","liveStatuses.observe = $it")

            val serviseStatusItemAdapter =ServiceStatusItemAdapter(
                ServiceStatusItemAdapter.OnClickListener{
                }
            )
            binding.recyclerStatusItem.adapter = serviseStatusItemAdapter
            serviseStatusItemAdapter.submitList(it)
        })

//        service?.let {
//            viewModel.setNewstatus(it)
//            SalesmanManager.salesman?.let {
//                viewModel.setSalesmanForNewStatus(it)
//            }
//            Log.i("HsiaoLingSalesmanManager","SalesmanManager.salesman = $it")
//        }

        binding.salesman = SalesmanManager.salesman

        return binding.root
    }


}
