package com.hsiaoling.bao.servicestatus


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.NavigationDirections

import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.DialogServiceInfoDetailBinding
import com.hsiaoling.bao.databinding.DialogServiceInfoDetailBindingImpl
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.messageDialog.MessageDialog
import com.hsiaoling.bao.util.Logger


class StatusInfoDialog : AppCompatDialogFragment() {

    private val viewModel by viewModels<StatusInfoViewModel> { getVmFactory() }
    private lateinit var binding:DialogServiceInfoDetailBindingImpl
    private var service: Service? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.StatusUpdateDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding =DialogServiceInfoDetailBinding.inflate(inflater, container, false)
        binding.layoutStatusInfo.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_slide_up))

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Log.i("Hsiao","SalesmanManager.salesman=${SalesmanManager.salesman}")

        service = requireArguments().getParcelable<Service>("serviceInfo")

        Log.i("Hsiao","requireArguments().getParcelable=${service}")




        service?.let {
            viewModel.updateStatus(it)
            SalesmanManager.salesman?.let {
                viewModel.setSalesmanForService(it)
            }
        }




        viewModel.leave.observe(this, Observer {
            it?.let {
                if (it) findNavController().popBackStack()
            }
        })

        return binding.root

    }


}
