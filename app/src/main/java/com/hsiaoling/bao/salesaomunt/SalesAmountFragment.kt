package com.hsiaoling.bao.salesaomunt


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R

import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.FragmentSalesAmountBinding
import com.hsiaoling.bao.ext.getVmFactory
import com.hsiaoling.bao.ext.toDayFormat
import com.hsiaoling.bao.ext.toListDayFormat
import com.hsiaoling.bao.login.UserManager
import kotlinx.android.synthetic.main.fragment_sales_amount.*

/**
 * A simple [Fragment] subclass.
 */
class SalesAmountFragment() : Fragment( ) {

    private val viewModel by viewModels<SalesAmountViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSalesAmountBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val revChart =  binding.revChart


        revChart.axisLeft.apply {
            setDrawTopYLabelEntry(true)
            }

        revChart.axisRight.apply {

            setDrawTopYLabelEntry(true)
            setDrawZeroLine(false)
            setDrawGridLines(false)
            setDrawLabels(false)
        }



        fun setBarChartData(
            entries: List<BarEntry>,
            list: List<List<Service>>
        ) {

            val baoDayRev= entries

            val dataSetBao : BarDataSet

            if (revChart.getData() != null && revChart.getData().getDataSetCount() > 0) {
                dataSetBao = revChart.getData().getDataSetByIndex(0) as BarDataSet
                dataSetBao.values

                revChart.getData().notifyDataChanged()
                revChart.notifyDataSetChanged()
            }  else {
                var  dataSetBao = BarDataSet(baoDayRev, "Revenue")
                     dataSetBao.setDrawIcons(false)
                     dataSetBao.color = BaoApplication.instance.getColor(R.color.s1orange)
                val dataSets = ArrayList<IBarDataSet>()
                dataSets.add(dataSetBao)


                val revBarData = BarData(dataSetBao)
                revChart.setData(revBarData)
                revChart.animateY(3000)
                revChart.setFitBars(true)
                revChart.invalidate()
                revBarData.setValueTextSize(10f)
                revBarData.barWidth = 0.9f

                val formatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        Log.i("HsiaoLing","list.size=${list.size}")
                        Log.i("HsiaoLing","getAxisLabel, value=${value}")
                        Log.i("HsiaoLing","list[${value.toInt()}]=${list[value.toInt()]}")
                        return if (list[value.toInt()].isNotEmpty()) {
                            list[value.toInt()][0].doneTime.toListDayFormat()
                        } else {
                            ""
                        }
                    }
                }


                val xAxis:XAxis = revChart.xAxis
                xAxis.granularity = 1f
                xAxis.valueFormatter = formatter
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawAxisLine(true)
                xAxis.setDrawGridLines(false)

                revChart.description.isEnabled= false
                revChart.setData(revBarData)
            }

        }
        Log.i("HsiaoLing","UserManager.user=${UserManager.user}")


        viewModel.rev.observe(this, Observer {
            Log.d("HsiaoLing","viewModel.rev.observe=$it")

        })

        viewModel.uptoDateRev.observe(this, Observer {
            Log.e("HsiaoLing"," viewModel.uptoDateRev.observe=$it")

            val revlist = viewModel.rev.value!!
            Log.e("HsiaoLing"," viewModel.rev.value!!=$revlist")
            Log.e("HsiaoLing"," viewModel.rev.value!!.size=${revlist.size}")

            viewModel.getServicesByDayGroup(revlist,3,2020)

            val list = viewModel.getServicesByDayGroup(viewModel.rev.value!!,3,2020)
            Log.w("HsiaoLing"," viewModel.getServicesByDayGroup=$list")

            val entries:List<BarEntry> =viewModel.getBarEntries(list)
            Log.i("HsiaoLing","  viewModel.getBarEntries(list)=$entries")

            setBarChartData(entries, list)

        })



        return binding.root
    }




}

