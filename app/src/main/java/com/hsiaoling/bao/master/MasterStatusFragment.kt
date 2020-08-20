package com.hsiaoling.bao.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hsiaoling.bao.databinding.FragmentMasterStatusBinding
import com.hsiaoling.bao.ext.getVmFactory

class MasterStatusFragment :Fragment(){

    private val viewModel by viewModels<MasterStatusViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding=FragmentMasterStatusBinding .inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel= viewModel

        return binding.root
    }
}