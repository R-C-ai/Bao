package com.hsiaoling.bao.servicestatus


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hsiaoling.bao.NavigationDirections

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentServiceStatusBinding .inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //get exist status Service

        viewModel.liveStatuses.observe(this, Observer {
                      Log.i("HsiaoLing","liveStatuses.observe = $it")
            Log.i("Hsiao","getLiveStatus=${viewModel.liveStatuses.value}")
            // show by recyclerview
            val serviceStatusItemAdapter =ServiceStatusItemAdapter(
                ServiceStatusItemAdapter.OnClickListener{
                    viewModel.navgateToUpdateStatus(it)

                }
            )
            binding.recyclerStatusItem.adapter = serviceStatusItemAdapter
            serviceStatusItemAdapter.submitList(it)
        })

        // get login salesman by Salesmaneger
        binding.salesman = SalesmanManager.salesman

        viewModel.navgateToUpdateStatus.observe(this, Observer {
            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalStatusUpdateDialog(it))
                viewModel.onUpdateStatusNavigated()

            }
        })

        viewModel.getLiveStatus()
        return binding.root
    }


}
