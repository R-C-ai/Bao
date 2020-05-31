package com.hsiaoling.bao.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hsiaoling.bao.*
import com.hsiaoling.bao.addservice.SalesmanSpinnerAdapter
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.databinding.DialogLoginBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.ext.setTouchDelegate
import com.google.android.gms.tasks.Task
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import kotlinx.android.synthetic.main.dialog_login.*


/**
 * Created by Wayne Chen in Jul. 2019.
 */
class LoginDialog : AppCompatDialogFragment() {

    /**
     * Lazily initialize our [LoginViewModel].
     */
    private val viewModel by viewModels<LoginViewModel> { getVmFactory() }
    private lateinit var binding: DialogLoginBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    val RC_SIGN_IN = 1
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.LoginDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        binding = DialogLoginBinding.inflate(inflater, container, false)
        binding.layoutLogin.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.anim_slide_up
            )
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        binding.buttonGoogleLogin.setOnClickListener {
            googleSignInClient = GoogleSignIn.getClient(context!!, gso)

            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)

        }



//  -------------------------------------------------------------------------------
//        viewModel.selectedSalesman.observe(this, Observer {
//            mainViewModel.setupSalesman(it)
//        })


//        viewModel.selectedSalesman.observe(this, Observer {
//            viewModel.addNewSalesman(it)
//            Log.i("Hsiao","viewModel.addNewSalesman=$it")
//
//        })

        //Salesman Spinner Adapter
//        viewModel.salemans.observe(this, Observer {
//            it?. let{
//                binding.textSalesman.adapter= SalesmanSpinnerAdapter(it)
//
//                viewModel.selectedSalesmanPosition.observe(this, Observer {
//                    Log.i("Hsiao","viewModel.selectedSalesmanPosition.observe, it=$it")
//                })

        // another way to get firebase salesman data link with spinner
//                ---------------------------------------------------------------------------------------------------------------------
//                binding.textSalesman.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                    override fun onNothingSelected(parent: AdapterView<*>?) {
//                    }
//                    override fun onItemSelected(parent: AdapterView<*>?,view: View?, position: Int, id: Long) {
//                        Log.d("Hsiao","onItemSelected, position=$position, id=$id, binding.textSalesman.adapter.getItem(position)=${binding.textSalesman.adapter.getItem(position) as Salesman}")
//                    }
//                }
//            }
//        })


        viewModel.leave.observe(this, Observer {
            it?.let {
                dismiss()
                viewModel.onLeaveCompleted()
            }
        })


        // login success navigate to calendar with loginSalesman,
        viewModel.loginUser.observe(this, Observer {
            Log.i("HsiaoLing", " viewModel.loginUser.observe, it=$it")
            it?.let {

                // set SalesmanManager data is selectedsalesman ,so can be used anywhere in this App
                UserManager.user = it

                val mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

                //  get first time liveStatus when login App
                mainViewModel.getMonthLiveStatus()

                // reObserve liveStatus to get the most updated livestatus in mainActivity for first time login App
                (activity as MainActivity).reObserveLiveStatuses()

                findNavController().navigate(NavigationDirections.actionGlobalCalendarFragment())
            }
        })

        // login success navigate to calendar with loginSalesman,
        viewModel.error.observe(this, Observer {
            Log.i("HsiaoLing", " viewModel.loginUser.observe, it=$it")
            it?.let {
                // set error message
                val msg = BaoApplication.instance.getString(R.string.without_this_account)
                Toast.makeText(context,msg,Toast.LENGTH_LONG).show()

                // logout exit account for next new account to login
                googleSignInClient.signOut()

            }
        })


        return binding.root
    }

    override fun dismiss() {
        binding.layoutLogin.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.anim_slide_down
            )
        )
        Handler().postDelayed({ super.dismiss() }, 200)
    }


    // get LoginResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed
                Log.w("Hsiao", "Google sign in failed", e)

            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("Hsiao ", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    // Sign in success
                    Log.d("Hsiao", "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.d("Hsiao", "auth.currentUser=" + auth.currentUser)

                    // check loginSalesman in store
                    val loginUserId: String = user!!.uid
                    val loginUserName: String = user!!.displayName!!
                    viewModel.getLoginUser(loginUserId, loginUserName)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Hsiao", "signInWithCredential:failure", task.exception)

                }

            }
    }

}
