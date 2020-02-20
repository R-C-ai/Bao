package com.hsiaoling.bao.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [LoginDialog].
 */
class LoginViewModel(private val repository: BaoRepository) : ViewModel() {

    // Get Input salesman  LiveData
    private val _salesman = MutableLiveData<Salesman>()
    val salesman: LiveData<Salesman>
        get() = _salesman as LiveData<Salesman>

    fun setSalesman(salesman: Salesman){
        _salesman.value = salesman
    }

    //SalesmanChosen spinner
    val selectedSalesmanPosition = MutableLiveData<Int>()
    val salesmanChosen: LiveData<SalesManChosen> = Transformations.map(selectedSalesmanPosition) {
        SalesManChosen.values()[it]
    }

    private val _navigateToCalendar = MutableLiveData<Salesman>()
    val navigateToCalendar : LiveData<Salesman>
        get() = _navigateToCalendar



    // Handle navigation to login success
    private val _navigateToLoginSuccess = MutableLiveData<Salesman>()

    val navigateToLoginSuccess: LiveData<Salesman>
        get() = _navigateToLoginSuccess

    // Handle leave login
    private val _loginTWM = MutableLiveData<Boolean>()

    val loginTWM: LiveData<Boolean>
        get() = _loginTWM

    // Handle leave login
    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

//    lateinit var twmCallbackManager: CallbackManager

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {

    }


    /**
     * track [StylishRepository.userSignIn]: -> [DefaultStylishRepository] : [StylishRepository] -> [StylishRemoteDataSource] : [StylishDataSource]
     * @param fbToken: Facebook token
     */
//     private fun loginBao(twmToken: String) {
//
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//            // It will return Result object after Deferred flow
//            when (val result = stylishRepository.userSignIn(fbToken)) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    SalesManManager.userToken = result.data.userSignIn?.accessToken
//                    _user.value = result.data.userSignIn?.user
//                    _navigateToLoginSuccess.value = user.value
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _error.value = getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
//        }
//     }

    /**
     * Login Stylish by Facebook: Step 1. Register FB Login Callback
     */
//    fun login() {
//        _status.value = LoadApiStatus.LOADING
//
//        fbCallbackManager = CallbackManager.Factory.create()
//        LoginManager.getInstance().registerCallback(fbCallbackManager, object : FacebookCallback<LoginResult> {
//            override fun onSuccess(loginResult: LoginResult) {
//
//                loginStylish(loginResult.accessToken.token)
//            }
//
//            override fun onCancel() { _status.value = LoadApiStatus.ERROR }
//
//            override fun onError(exception: FacebookException) {
//                Logger.w("[${this::class.simpleName}] exception=${exception.message}")
//
//                exception.message?.let {
//                    _error.value = if (it.contains("ERR_INTERNET_DISCONNECTED")) {
//                         getString(R.string.internet_not_connected)
//                    } else {
//                        it
//                    }
//                }
//                _status.value = LoadApiStatus.ERROR
//            }
//        })
//
//        loginFacebook()
//    }

    /**
     * Login Stylish by Facebook: Step 2. Login Facebook
     */
//    private fun loginFacebook() {
//        _loginFacebook.value = true
//    }
//
    fun leave() {
        _leave.value = true
    }

    fun onLeaveCompleted() {
        _leave.value = null
    }

    fun nothing() {}

//    fun onLoginFacebookCompleted() {
//        _loginFacebook.value = null
//    }
}