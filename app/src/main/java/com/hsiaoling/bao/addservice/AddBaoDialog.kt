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
import com.hsiaoling.bao.login.UserManager
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

//        ---------------------------------------------------------------------------------------------------
//         binding  dialod_master_grab_bao,
//        binding.layoutGrabBao.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_slide_up))
// --------------------------------------------------------------------------------------------------------



        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Log.i("Hsiao","UserManager.user=${UserManager.user}")

        // get service from selected schedule
        service = requireArguments().getParcelable<Service>("givenservice")

        // set loginUser to services
        service?.let {
            viewModel.setService(it)
            UserManager.user?.let {
                viewModel.setLoginUserForService(it)
            }
        }


        viewModel.service.observe(this, Observer {
            it?.let {
                when(it.status){
                    1 ->{
                        viewModel.getDevicePosition()
                        viewModel.getScreenPosition()
                        viewModel.getBackPosition()
                    }
                }
            }
        })


//        Spinner------------------------------------------------------------------------------------------------------------------------------
        //Device Spinner Adapter
         binding.textDevice.adapter=SpinnerAdapter(
             BaoApplication.instance.resources.getStringArray(R.array.device_list)
         )

        // for status1 to change service
        viewModel.deviceIndex.observe(this, Observer {
            viewModel.selectedDevicePosition.value = viewModel.deviceIndex.value
        })

        viewModel.selectedDevicePosition.observe(this, Observer {
            Log.i("Hsiao","viewModel.selectedDevicePosition.observe, it=$it")
        })
        viewModel.deviceChosen.observe(this, Observer {
            Log.i("Hsiao","viewModel.deviceChosen.observe, it=$it")
        })



        //Service0 Screen Spinner Adapter
        binding.textScreen.adapter=SpinnerAdapter(
            BaoApplication.instance.resources.getStringArray(R.array.service0_list)
        )

        // for status1 change service
        viewModel.screenIndex.observe(this, Observer {
            viewModel.selectedScreenPosition.value = viewModel.screenIndex.value
        })

        viewModel.selectedScreenPosition.observe(this, Observer {
            Log.i("Hsiao","viewModel.selectedService0Position.observe, it=$it")
        })
        viewModel.screenChosen.observe(this, Observer {
            Log.i("Hsiao","viewModel.screenChosen.observe, it=$it")
        })


        //Service1 Back Spinner Adapter
        binding.textBack.adapter=SpinnerAdapter(
            BaoApplication.instance.resources.getStringArray(R.array.service1_list)
        )

        //for status1 change service
        viewModel.backIndex.observe(this, Observer {
            viewModel.selectedBackPosition.value = viewModel.backIndex.value
        })
        viewModel.selectedBackPosition.observe(this, Observer {
            Log.i("Hsiao","viewModel.selectedBackPosition.observe, it=$it")
        })
        viewModel.backChosen.observe(this, Observer {
            Log.i("Hsiao","viewModel.backChosen.observe, it=$it")
        })

//        ---------------------------------------------------------------------------------------------------------------------------------

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
                dismiss()
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


        return binding.root

    }


}
