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
import com.hsiaoling.bao.addservice.CalendarFragment
import com.hsiaoling.bao.addservice.CalendarViewModel
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.FragmentMasterDailyItemBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.master.MasterTypeFliter

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

//        val service = BaoService(0, "iPhone X", "", "", 1)
//        val service2 = BaoService(1, "iPhone XR", "", "", 1)
//        val list = mutableListOf<BaoService>()
//        list.add(service)
//        list.add(service2)
//        (binding.recyclerMasterDailyItem.adapter as MasterDailyItemAdapter).submitList(list)

        viewModel.navgateToAddBao.observe(this, Observer {
            it?.let{
                findNavController().navigate(NavigationDirections.actionGlobalAddBaoDialog(it))
                viewModel.onAddJobNavigated()
            }
        })

        // let  "date" from CalendarViewModel  pass to MasterDailyItemFragment ,need to pass by parentViewModel
        val parentViewModel = ViewModelProviders.of(parentFragment!!).get(CalendarViewModel::class.java)

        //  navigato CalendarFragment  get today data first
//        parentViewModel.date.observe(parentFragment as CalendarFragment, Observer {today ->
//            viewModel.getLiveDateServices(today,master.id)
//            Log.i("Hsiao","gettodayServices=${viewModel.schedules.value}")
//            viewModel.schedules.observe(this, Observer {
//                Log.i("Hsiao","schedules.observe=${viewModel.schedules.value}")
//                it?.let {
//                    if (it.size == 0) {
//                        viewModel.newDailyServices(today, master.id,master.name)
//                    }
//                }
//                Log.i("HsiaoLing","getLiveTodayServices = $it")
//                masterDailyItemAdapter.submitList(it)
//            })
//        })


        // when observe CalendarFragment  " date" change  , MasterDailyItemViewModel  get the Result of the "date"
        parentViewModel.date.observe(parentFragment as CalendarFragment, Observer {date ->
            Log.i("HsiaoLing","getDate=${date}")
            Log.i("HsiaoLing","masters=${master}")

            val today = parentViewModel.today
            if ( date < today) {
                viewModel.getLiveDateServices(date,master.id)
                viewModel.schedules.observe(this, Observer {
                    masterDailyItemAdapter.submitList(it)
                })
            }

            else{
                viewModel.getLiveDateServices(date,master.id)
                viewModel.schedules.observe(this, Observer {
                    it?.let{
                        if(it.size == 0){
                            viewModel.newDailyServices(date,master.id,master.name)
                        }
                    }
                    masterDailyItemAdapter.submitList(it)
                })

            }

//            viewModel.getLiveDateServices(date, master.id)
//                Log.i("GetLiveData","getLiveDateServices=${viewModel.schedules.value}")
//            viewModel.schedules.observe(this, Observer {
//                Log.i("GetLiveData","schedules.observe=${viewModel.schedules.value}")
//
//                it?.let {
//                    if (it.size == 0) {
//                        viewModel.newDailyServices(date, master.id,master.name)
//                    }
//                }
//                Log.i("HsiaoLing","getLiveDateServices = $it")
//                masterDailyItemAdapter.submitList(it)
//            })
        })


//        viewModel.schedules.observe(this, Observer {
//            it?.let{
//                if (it.size == 0)
//                viewModel.newDailySchedules()
//                return@let
//            }
//        }
//        )



//        ViewModelProviders.of(activity!!).get(MasterDailyItemViewModel::class.java).apply {
//            refresh.observe(this@MasterDailyItemFragment, Observer {
//                it?.let {
//                    viewModel.refresh()
//                    onRefreshed()
//                }
//            })
//        }


//        viewModel.navgateToAddBao.observe(this, Observer {
//            it?.let{
//                findNavController().navigate(NavigationDirections.actionGlobalAddJobFragment(it))
//                viewModel.onAddJobNavigated()
//            }
//        })


        return binding.root
    }

}