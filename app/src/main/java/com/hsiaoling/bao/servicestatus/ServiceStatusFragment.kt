package com.hsiaoling.bao.servicestatus


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hsiaoling.bao.NavigationDirections
import com.hsiaoling.bao.databinding.FragmentServiceStatusBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.login.UserManager

/**
 * A simple [Fragment] subclass.
 */
class ServiceStatusFragment : Fragment() {


    private val viewModel by viewModels<ServiceStatusViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentServiceStatusBinding .inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel



        //get exist status Service
        val serviceStatusItemAdapter =ServiceStatusItemAdapter(
            ServiceStatusItemAdapter.OnClickListener{
                viewModel.navgateToUpdateStatus(it)

            }
        )

        binding.recyclerStatusItem.adapter = serviceStatusItemAdapter


        viewModel.liveStatuses.observe(this, Observer {
            Log.i("Hsiao","getLiveStatus=${viewModel.liveStatuses.value}")
            // show by recyclerview
            serviceStatusItemAdapter.submitList(it)
        })




        // when click search buttom, show search data
        viewModel.filterStatus.observe(this, Observer {
            it?.let {
                when (it) {
                    0 -> { // all
                        serviceStatusItemAdapter.submitList(viewModel.liveStatuses.value)
                    }
                    1 -> { //reserve
                        serviceStatusItemAdapter.submitList(viewModel.liveStatuses.value?.filter { it.status == 1 } ?: listOf())


                    }
                    2 -> { //get job
                        serviceStatusItemAdapter.submitList(viewModel.liveStatuses.value?.filter { it.status == 2 } ?: listOf())

                    }
                    3 -> { //done
                        serviceStatusItemAdapter.submitList(viewModel.liveStatuses.value?.filter { it.status == 3 } ?: listOf())

                    }
                    4 -> { //finish check
                        serviceStatusItemAdapter.submitList(viewModel.liveStatuses.value?.filter { it.status == 4 } ?: listOf())

                    }
                    5 -> { //delete
                        serviceStatusItemAdapter.submitList(viewModel.liveStatuses.value?.filter { it.status == 5 } ?: listOf())
                    }

                }
            }
        })

        // get loginUser by UserManeger
        binding.statusUser = UserManager.user

        viewModel.navgateToAddBao.observe(this, Observer {
            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalAddBaoDialog(it))
                viewModel.onUpdateStatusNavigated()

            }
        })

        viewModel.navgateToUpdateMasterJob.observe(this, Observer {
            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalMasterJobUpdateDialog(it))
                viewModel.onUpdateStatusNavigated()

            }
        })


        viewModel.navgateToUpdateStatus.observe(this, Observer {
            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalStatusUpdateDialog(it))
                viewModel.onUpdateStatusNavigated()

            }
        })

        viewModel.navgateToInfoStatus.observe(this, Observer {
            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalStatusInfoDialog(it))
                viewModel.onInfoStatusNavigated()

            }
        })



        viewModel.getLiveStatus()
        return binding.root
    }


}
