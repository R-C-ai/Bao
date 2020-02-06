package com.hsiaoling.bao.master.dailyItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.hsiaoling.bao.NavigationDirections
import com.hsiaoling.bao.databinding.FragmentMasterDailyItemBinding
import com.hsiaoling.bao.master.MasterAdapter
import com.hsiaoling.bao.master.MasterTypeFliter

class MasterDailyItemFragment(private val masterType:MasterTypeFliter):Fragment() {

    private val   viewModel by lazy {
        ViewModelProviders.of(this).get(MasterDailyItemViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentMasterDailyItemBinding = FragmentMasterDailyItemBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recyclerMasterDailyItem.adapter = MasterDailyItemAdapter(MasterDailyItemAdapter.OnClickListener{
            viewModel.navgateToAddBao(it)
        })


        viewModel.navgateToAddBao.observe(this, Observer {
            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalAddJobFragment(it))
                viewModel.onAddJobNavigated()
            }
        })


        return binding.root
    }

}