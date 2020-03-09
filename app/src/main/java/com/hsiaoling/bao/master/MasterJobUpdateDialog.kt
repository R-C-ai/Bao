package com.hsiaoling.bao.master


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
import com.hsiaoling.bao.NavigationDirections

import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.DialogMasterJobUpdateBinding

import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.login.UserManager
import com.hsiaoling.bao.messageDialog.MessageDialog


class MasterJobUpdateDialog : AppCompatDialogFragment() {

    private val viewModel by viewModels<MasterJobUpdateViewModel> { getVmFactory() }
    private lateinit var binding:DialogMasterJobUpdateBinding
    private var service: Service? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.StatusUpdateDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding = DialogMasterJobUpdateBinding.inflate(inflater, container, false)
        binding.layoutMasterJobUpdate.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_slide_up))

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Log.i("Hsiao","SalesmanManager.salesman=${UserManager.user}")

        service = requireArguments().getParcelable<Service>("serviceToUpdate")

        Log.i("Hsiao","requireArguments().getParcelable=${service}")

        service?.let {
            viewModel.updateMasterJob(it)
            UserManager.user?.let {
                viewModel.setMsaterForJob(it)
            }
        }


        viewModel.navigateToAddSuccess.observe(this, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalMessageDialog3(
                    MessageDialog.MessageType.DONE_SUCCESS))
                viewModel.onAddedSuccessNavigated()
            }
        })

        viewModel.navigateToAddedFail.observe(this, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalMessageDialog3(
                    MessageDialog.MessageType.MESSAGE.apply { value.message = getString(R.string.bao_finished) }
                ))
                viewModel.onAddedFailNavigated()
            }
        })



        viewModel.leave.observe(this, Observer {
            it?.let {
                if (it) findNavController().popBackStack()
            }
        })


//        binding.textViewSalesman.text = service?.salesmanName
//        binding.textViewMaster.text = service?.masterName
//        binding.textViewCustomer.text = service?.customerNo


        return binding.root

    }


}
