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
import com.hsiaoling.bao.databinding.DialogStatusUpdateBinding

import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.login.UserManager
import com.hsiaoling.bao.messageDialog.MessageDialog
import com.hsiaoling.bao.util.Logger


class StatusUpdateDialog : AppCompatDialogFragment() {

    private val viewModel by viewModels<StatusUpdateViewModel> { getVmFactory() }
    private lateinit var binding:DialogStatusUpdateBinding
    private var service: Service? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.StatusUpdateDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding = DialogStatusUpdateBinding.inflate(inflater, container, false)
        binding.layoutStatusUpdate.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_slide_up))

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Log.i("Hsiao","SalesmanManager.salesman=${SalesmanManager.salesman}")

        service = requireArguments().getParcelable<Service>("serviceToUpdate")

        Log.i("Hsiao","requireArguments().getParcelable=${service}")


        // set loginUser to services
        service?.let {
            viewModel.setStatus(it)
            UserManager.user?.let {
                viewModel.setLoginUserForService(it)
            }
        }

        viewModel.navgateToChange.observe(this, Observer {
            it?.let {
                Log.i("HsiaoLing","viewModel.navgateToChange.observe=${it}")
                dismiss()
                findNavController().navigate(NavigationDirections.actionGlobalAddBaoDialog(it))


            }
        })






        viewModel.navigateToAddSuccess.observe(this, Observer {
            it?.let {
                dismiss()
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

        return binding.root

    }


}
