package com.hsiaoling.bao.addservice


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.DialogAddBaoBinding

import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.master.dailyItem.MasterDailyItemViewModel
import com.hsiaoling.bao.util.Logger


class AddBaoDialog : AppCompatDialogFragment() {

    private val viewModel by viewModels<AddBaoViewModel> { getVmFactory() }
    private var service: Service? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AddBaoDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding = DialogAddBaoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        service = requireArguments().getParcelable<Service>("givenservice")

        service?.let {  viewModel.setService(it) }



//        viewModel.leave.observe(this, Observer {
//            it?.let { needRefresh ->
//                if (needRefresh) {
//                    ViewModelProviders.of(activity!!).get(MasterDailyItemViewModel::class.java).apply {
//                        refresh()
//                    }
//                }
//                findNavController().navigateUp()
//                viewModel.onLeft()
//            }
//        })
        viewModel.service.observe(this, Observer {
            Logger.i("change")
        })

        return binding.root

    }


}
