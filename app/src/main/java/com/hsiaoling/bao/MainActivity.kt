package com.hsiaoling.bao

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hsiaoling.bao.data.Day
import com.hsiaoling.bao.databinding.ActivityMainBinding
import com.hsiaoling.bao.databinding.BadgeBottomBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.login.DayManager
import com.hsiaoling.bao.util.CurrentFragmentType
import kotlinx.coroutines.launch


class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainViewModel> { getVmFactory() }
    private lateinit var binding: ActivityMainBinding

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_add_service -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalCalendarFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_service_status -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalServiceStatusFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sales_amount-> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalSalesAmountFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    // get the height of status bar from system
    private val statusBarHeight: Int
        get() {
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            return when {
                resourceId > 0 -> resources.getDimensionPixelSize(resourceId)
                else -> 0
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        // set DayManager
        viewModel.thisTime.observe(this, Observer {
            DayManager.day = it
            Log.i("HsiaoLing","viewModel.thisTime.observe=[${DayManager.day}]")
        })

        // observe current fragment change, only for show info
        viewModel.currentFragmentType.observe(this, Observer {

            Log.i("Hsiao","viewModel.currentFragmentType=[${viewModel.currentFragmentType.value}]")

        })

        viewModel.navigateToAddServiceByBottomNav.observe(this, Observer {
            it?.let {
                binding.bottomNavView.selectedItemId = R.id.navigation_add_service
                viewModel.onAddServiceNavigated()
            }
        })

        viewModel.navigateToServiceStatusByBottomNav.observe(this, Observer {
            it?.let {
                binding.bottomNavView.selectedItemId = R.id.navigation_service_status
                viewModel.onServiceStatusNavigated()
            }
        })

        viewModel.navigateToSalesAmountByBottomNav.observe(this, Observer {
            it?.let {
                binding.bottomNavView.selectedItemId = R.id.navigation_sales_amount
                viewModel.onSalesAmountNavigated()
            }
        })

        setupToolbar()
        setupBottomNav()
        setupNavController()

    }

    /**
     * Set up [BottomNavigationView],  [BottomNavigationMenuView] and [BottomNavigationItemView]
     *
     */


    private fun setupBottomNav() {
        binding.bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }


    fun setupBadgeView() {
        val menuView = binding.bottomNavView.getChildAt(0) as BottomNavigationMenuView
        val itemView = menuView.getChildAt(1) as BottomNavigationItemView
        val bindingBadge =BadgeBottomBinding.inflate(LayoutInflater.from(this),itemView,true)
        bindingBadge.lifecycleOwner = this
        bindingBadge.viewModel = viewModel
    }


    /**
     * Set up [NavController.addOnDestinationChangedListener] to record the current fragment, it better than another design
     * which is change the [CurrentFragmentType] enum value by [MainViewModel] at [onCreateView]
     */
    private fun setupNavController() {
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.loginDialog -> CurrentFragmentType.LOGIN
                R.id.calendarFragment -> CurrentFragmentType.ADDSERVICE
                R.id.serviceStatusFragment -> CurrentFragmentType.SERVICESTATUS
                R.id.salesAmountFragment -> CurrentFragmentType.SALESAMOUNT
                else -> viewModel.currentFragmentType.value
            }
        }
    }

    /**
     * Set up the layout of [Toolbar], according to whether it has cutout
     */
    private fun setupToolbar() {

        binding.toolbar.setPadding(0, statusBarHeight, 0, 0)

        launch {

            val dpi = resources.displayMetrics.densityDpi.toFloat()
            val dpiMultiple = dpi / DisplayMetrics.DENSITY_DEFAULT

            val cutoutHeight = getCutoutHeight()


            Log.i("Hsiao","$dpi dpi (${dpiMultiple}x)")
            Log.i("Hsiao","statusBarHeight: ${statusBarHeight}px/${statusBarHeight / dpiMultiple}dp")

            when {
                cutoutHeight > 0 -> {
                    Log.i("Hsiao","cutoutHeight: ${cutoutHeight}px/${cutoutHeight / dpiMultiple}dp")

                    val oriStatusBarHeight = resources.getDimensionPixelSize(R.dimen.height_status_bar_origin)

                    binding.toolbar.setPadding(0, oriStatusBarHeight, 0, 0)
                    val layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
                    layoutParams.gravity = Gravity.CENTER
                    layoutParams.topMargin = statusBarHeight - oriStatusBarHeight
//                    binding.imageToolbarLogo.layoutParams = layoutParams
                    binding.textToolbarTitle.layoutParams = layoutParams
                }
            }
            Log.i("Hsiao","====== ${Build.MODEL} ======")
        }
    }


    fun reObserveLiveStatuses() {

        // get first time liveStatus when login App
        viewModel.liveStatuses.observe(this, Observer {
            Log.i("HsiaoLing","viewModel.liveStatuses.observe=[$it]")
        })

        // set the first time status as most updated status
        viewModel.countStatus1?.observe(this, Observer {
            Log.i("HsiaoLing","viewModel.countStatus1.observe=[$it]")

        })

        setupBadgeView()
    }
}



