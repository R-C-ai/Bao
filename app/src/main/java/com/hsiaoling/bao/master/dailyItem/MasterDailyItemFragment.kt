package com.hsiaoling.bao.master.dailyItem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.hsiaoling.bao.NavigationDirections
import com.hsiaoling.bao.R
import com.hsiaoling.bao.addservice.CalendarFragment
import com.hsiaoling.bao.addservice.CalendarViewModel
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.FragmentMasterDailyItemBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.login.SalesmanManager
import com.hsiaoling.bao.master.MasterTypeFliter
import com.hsiaoling.bao.messageDialog.MessageDialog




class MasterDailyItemFragment(private val master:Master):Fragment() {

    private val viewModel by viewModels<MasterDailyItemViewModel> { getVmFactory() }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentMasterDailyItemBinding = FragmentMasterDailyItemBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        val list = mutableListOf<Service>()


        val masterDailyItemAdapter =MasterDailyItemAdapter(
            MasterDailyItemAdapter.OnClickListener{
                viewModel.navgateToAddBao(it)
            }
        )
        binding.recyclerMasterDailyItem.adapter = masterDailyItemAdapter


        viewModel.navgateToAddBao.observe(this, Observer {
            Log.i("HsiaoLing","viewModel.navgateToAddBao.observe=$it")

            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalAddBaoDialog(it))
                viewModel.onAddJobNavigated()
            }
        })

        viewModel.navgateToInfoStatus.observe(this, Observer {
            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalStatusInfoDialog(it))
                viewModel.onInfoStatusNavigated()

            }
        })

        viewModel.navigateToAddReject.observe(this, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalMessageDialog3(MessageDialog.MessageType.ADD_REJECT))
                viewModel.onAddedRejectNavigated()
            }
        })



        // let  "date" from CalendarViewModel  pass to MasterDailyItemFragment ,need to pass by parentViewModel
        val parentViewModel = ViewModelProviders.of(parentFragment!!).get(CalendarViewModel::class.java)

        // when observe CalendarFragment  " date" change  , MasterDailyItemViewModel  get the Result of the "date"
        parentViewModel.date.observe(parentFragment as CalendarFragment, Observer {date ->
            Log.i("HsiaoLing","getDate=${date}")
            Log.i("HsiaoLing","masters=${master}")

            val today = parentViewModel.today
            Log.i("HsiaoLing","today=$today")

            fun isBeforeToday(data: String): Boolean {
                val todaySeparated =
                    today.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                todaySeparated[0]
                todaySeparated[1]
                todaySeparated[2]
                Log.i("HsiaoLing","todaySeparated=${todaySeparated[0]} ,${todaySeparated[1]} ,${todaySeparated[2]}")

                val dateSeparated =
                   date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                Log.i("HsiaoLing","dateSeparated=${dateSeparated[0]} ,${dateSeparated[1]} ,${dateSeparated[2]}")

                return when {
                    dateSeparated[0].toInt() < todaySeparated[0].toInt() -> { // year
                        Log.i("HsiaoLing","isBeforeToday0=")
                        true
                    }
                    dateSeparated[0].toInt() > todaySeparated[0].toInt() -> { // year
                        Log.i("HsiaoLing","isBeforeToday01=")
                        false
                    }
                    dateSeparated[1].toInt() < todaySeparated[1].toInt() -> { // month
                        Log.i("HsiaoLing","isBeforeToday1=")
                        true
                    }
                    dateSeparated[1].toInt() > todaySeparated[1].toInt() -> { // month
                        Log.i("HsiaoLing","isBeforeToday11=")
                        false
                    }
                    dateSeparated[2].toInt() < todaySeparated[2].toInt() -> { // day
                        Log.i("HsiaoLing","isBeforeToday2=")
                        true
                    }
                    else -> false
                }
            }


            viewModel.getLiveDateServices(date,master.id)
            viewModel.schedules.observe(this, Observer {
                binding.textEmptyMasterDailyItem.visibility = View.GONE
                it?.let{
                    if (it.isEmpty()) {
                        if (isBeforeToday(today)) {
                            binding.textEmptyMasterDailyItem.visibility = View.VISIBLE
                        } else {
                            viewModel.newDailyServices(date,master.id,master.name)
                        }
                    }
                }
                masterDailyItemAdapter.submitList(it)
            })


        })

        return binding.root
    }
}