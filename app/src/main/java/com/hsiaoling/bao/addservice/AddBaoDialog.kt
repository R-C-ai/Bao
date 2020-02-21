package com.hsiaoling.bao.addservice


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
import com.hsiaoling.bao.databinding.DialogAddBaoBinding

import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.messageDialog.MessageDialog
import com.hsiaoling.bao.util.Logger


class AddBaoDialog : AppCompatDialogFragment() {

    private val viewModel by viewModels<AddBaoViewModel> { getVmFactory() }
    private lateinit var binding:DialogAddBaoBinding
    private var service: Service? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AddBaoDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding = DialogAddBaoBinding.inflate(inflater, container, false)
        binding.layoutAddBao.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_slide_up))

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Log.i("Hsiao","SalesmanManager.salesman=${SalesmanManager.salesman}")

        service = requireArguments().getParcelable<Service>("givenservice")
        service?.let {
            viewModel.setService(it)
            SalesmanManager.salesman?.let {
                viewModel.setSalesmanForService(it)
            }
        }


        //Device Spinner Adapter
         binding.textDevice.adapter=SpinnerAdapter(
             BaoApplication.instance.resources.getStringArray(R.array.device_list)
         )

        viewModel.selectedDevicePosition.observe(this, Observer {
            Log.i("Hsiao","viewModel.selectedDevicePosition.observe, it=$it")
        })
        viewModel.deviceChosen.observe(this, Observer {
            Log.i("Hsiao","viewModel.deviceChosen.observe, it=$it")
        })


        //Service0 Spinner Adapter
        binding.textService0.adapter=SpinnerAdapter(
            BaoApplication.instance.resources.getStringArray(R.array.service0_list)
        )

        viewModel.selectedService0Position.observe(this, Observer {
            Log.i("Hsiao","viewModel.selectedService0Position.observe, it=$it")
        })
        viewModel.service0Chosen.observe(this, Observer {
            Log.i("Hsiao","viewModel.service0Chosen.observe, it=$it")
        })


        //Service1 Spinner Adapter
        binding.textService1.adapter=SpinnerAdapter(
            BaoApplication.instance.resources.getStringArray(R.array.service1_list)
        )

        viewModel.selectedService1Position.observe(this, Observer {
            Log.i("Hsiao","viewModel.selectedService1Position.observe, it=$it")
        })
        viewModel.service1Chosen.observe(this, Observer {
            Log.i("Hsiao","viewModel.service1Chosen.observe, it=$it")
        })

//        binding.textDevice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                // An item was selected. You can retrieve the selected item using
//                // parent.getItemAtPosition(pos)
//                Log.i("Hsiao","poistion=${position}, parent.getItemAtPosition(pos)=${parent.getItemAtPosition(position)}")
//                binding.textDevice.setSelection(position)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Another interface callback
//            }
//        }

        viewModel.navigateToAddSuccess.observe(this, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalMessageDialog3(MessageDialog.MessageType.ADDED_SUCCESS))
                viewModel.onAddedSuccessNavigated()
            }
        })

        viewModel.navigateToAddFail.observe(this, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalMessageDialog3(
                    MessageDialog.MessageType.MESSAGE.apply { value.message = getString(R.string.bao_finished) }
                ))
                viewModel.onAddedFailNavigated()
            }
        })



        viewModel.leave.observe(this, Observer {
            it?.let {
                dismiss()
                viewModel.onLeaveCompleted()
            }
        })



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
            Logger.i("added")
        })

        return binding.root

    }


}
