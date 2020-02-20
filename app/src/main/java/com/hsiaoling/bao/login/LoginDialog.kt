package com.hsiaoling.bao.login

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.hsiaoling.bao.*
import com.hsiaoling.bao.addservice.SpinnerAdapter
import com.hsiaoling.bao.databinding.DialogLoginBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.ext.setTouchDelegate

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class LoginDialog : AppCompatDialogFragment() {

    /**
     * Lazily initialize our [LoginViewModel].
     */
    private val viewModel by viewModels<LoginViewModel> { getVmFactory() }
    private lateinit var binding: DialogLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.LoginDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DialogLoginBinding.inflate(inflater, container, false)
        binding.layoutLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_slide_up))

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.buttonLoginClose.setTouchDelegate()

        val mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        //Device Spinner Adapter
        binding.textSalesman.adapter= SpinnerAdapter(
            BaoApplication.instance.resources.getStringArray(R.array.salesman_list)
        )

        viewModel.selectedSalesmanPosition.observe(this, Observer {
            Log.i("Hsiao","viewModel.selectedSalesmanPosition.observe, it=$it")
        })
        viewModel.salesmanChosen.observe(this, Observer {
            Log.i("Hsiao","viewModel.salesmanChosen.observe, it=$it")
        })



        viewModel.navigateToCalendar.observe(this, Observer {
            it?.let {
                findNavController().navigate(
                    NavigationDirections.actionGlobalCalendarFragment())
            }
        })


        viewModel.salesman.observe(this, Observer {
            it?.let {
                mainViewModel.setupSalesman(it)
            }
        })

//        viewModel.navigateToLoginSuccess.observe(this, Observer {
//            it?.let {
//                mainViewModel.navigateToLoginSuccess(it)
//                dismiss()
//            }
//        })

        viewModel.leave.observe(this, Observer {
            it?.let {
                dismiss()
                viewModel.onLeaveCompleted()
            }
        })

//        viewModel.loginTWM.observe(this, Observer {
//            it?.let {
//                LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
//                viewModel.onLoginFacebookCompleted()
//            }
//        })

        return binding.root
    }

    override fun dismiss() {



        binding.layoutLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_slide_down))
        Handler().postDelayed({ super.dismiss() }, 200)
    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        viewModel.fbCallbackManager.onActivityResult(requestCode, resultCode, data)
//    }
}
