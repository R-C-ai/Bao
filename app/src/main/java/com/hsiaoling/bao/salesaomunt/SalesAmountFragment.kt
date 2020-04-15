package com.hsiaoling.bao.salesaomunt


import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.MPPointF
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.addservice.SpinnerAdapter

import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.databinding.FragmentSalesAmountBinding
import com.hsiaoling.bao.ext.*
import com.hsiaoling.bao.login.UserManager
import kotlinx.android.synthetic.main.fragment_sales_amount.*
import java.util.*
import kotlin.collections.ArrayList

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
        // get login user for SalesAmount
        binding.salesUser = UserManager.user

        val revChart =  binding.revChart
        val cumRevChart = binding.cumRevChart
//        val revGoalChart =binding.revGoalChart




        //set BarChart YAxis and Data
        revChart.axisLeft.apply {
            setDrawTopYLabelEntry(true)
//            enableAxisLineDashedLine(10f,10f,0f)
            setDrawLabels(false)
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
                var  dataSetBao = BarDataSet(baoDayRev, "每日包膜營收")
                     dataSetBao.setDrawIcons(false)
                     dataSetBao.color = BaoApplication.instance.getColor(R.color.colorAccent)
                val dataSets = ArrayList<IBarDataSet>()
                dataSets.add(dataSetBao)


                val revBarData = BarData(dataSetBao)
                revChart.setData(revBarData)
                revChart.animateY(3000)
                revChart.setFitBars(true)
                revChart.invalidate()
                revBarData.setValueTextSize(8f)
                revBarData.barWidth = 0.9f

                // set date data for barchart
                val formatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        Log.i("HsiaoLing","list.size=${list.size}")
                        Log.i("HsiaoLing","getAxisLabel, value=${value}")
                        Log.i("HsiaoLing","list[${value.toInt()}]=${list[value.toInt()]}")
                        return if (value.toInt() >= 0 && list[value.toInt()].isNotEmpty()) {
                            list[value.toInt()][0].finishCheckTime.toListDayFormat()
                        } else {
                            ""
                        }
                    }
                }


                val xAxis:XAxis = revChart.xAxis
                xAxis.granularity = 1f
                xAxis.valueFormatter = formatter
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawAxisLine(false)
                xAxis.setDrawGridLines(false)
                xAxis.textSize = 8f

                val lb=revChart.legend
                lb.formSize = 5f
                lb.form = Legend.LegendForm.CIRCLE
                lb.xEntrySpace = 10f
                lb.yEntrySpace = 20f
                lb.isWordWrapEnabled = true
                lb.verticalAlignment =Legend.LegendVerticalAlignment.TOP
                lb.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                lb.orientation = Legend.LegendOrientation.HORIZONTAL

                revChart.description.isEnabled= false
                revChart.setData(revBarData)
            }

        }



        // set LineChart YAxis and Data
        cumRevChart.axisLeft.apply {
            setDrawTopYLabelEntry(true)
            setDrawGridLines(false)
            setDrawLabels(false)

            //set revenue goal line
//            val yLimitLine = LimitLine(60000f,"Goal 60,000")
//            axisMaximum = 70000f
//            addLimitLine(yLimitLine)
//            yLimitLine.lineColor = BaoApplication.instance.getColor(R.color.blue_voyage)
//            yLimitLine.textColor = BaoApplication.instance.getColor(R.color.backBlue)
//            yLimitLine.lineWidth = 0.5f
        }

        cumRevChart.axisRight.apply {
            setDrawTopYLabelEntry(true)
            setDrawZeroLine(false)
            setDrawGridLines(false)
            setDrawLabels(false)
        }

        fun setLineChartData(
            lineEntries: List<Entry>,
            list: List<List<Service>>
        ) {

            val cumRev= lineEntries

            val lineDataSetBao : LineDataSet

            if (cumRevChart.getData() != null && cumRevChart.getData().getDataSetCount() > 0) {
                lineDataSetBao = cumRevChart.getData().getDataSetByIndex(0) as LineDataSet
                lineDataSetBao.values

                cumRevChart.getData().notifyDataChanged()
                cumRevChart.notifyDataSetChanged()
            }  else {
                var  lineDataSetBao = LineDataSet(cumRev, "累計包膜營收")
                lineDataSetBao.setDrawIcons(false)
                lineDataSetBao.color = BaoApplication.instance.getColor(R.color.s2purple)
                lineDataSetBao.setCircleColor(BaoApplication.instance.getColor(R.color.s1orange))
                lineDataSetBao.lineWidth = 0.5f
                lineDataSetBao.mode = LineDataSet.Mode.CUBIC_BEZIER
                lineDataSetBao.setDrawFilled(true)
                lineDataSetBao.fillDrawable = BaoApplication.instance.getDrawable(R.drawable.line_chart_fill)
//                lineDataSetBao.fillColor =BaoApplication.instance.getColor(R.color.s1orange)
                lineDataSetBao.fillAlpha = 100

                val lineDataSets = ArrayList<ILineDataSet>()
                lineDataSets.add(lineDataSetBao)


                val cumRevLineData = LineData(lineDataSetBao)
                cumRevChart.setData(cumRevLineData)
                cumRevChart.animateY(1000)
                cumRevChart.invalidate()
                cumRevLineData.setValueTextSize(8f)


                // set date data for linechart
                val formatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {

                        Log.i("HsiaoLing","line getAxisLabel, value=${value}")

                        return if (value.toInt() >= 0 && list[value.toInt()].isNotEmpty()) {
                            Log.i("HsiaoLing","line list[${value.toInt()}]=${list[value.toInt()]}")

                            list[value.toInt()][0].finishCheckTime.toListDayFormat()


                        } else {
                            ""
                        }
                    }
                }


                val xAxis:XAxis = cumRevChart.xAxis
                xAxis.granularity = 1f
                xAxis.valueFormatter = formatter
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawAxisLine(true)
                xAxis.setDrawGridLines(false)
                xAxis.textSize = 8f

                val ll=cumRevChart.legend
                ll.formSize = 5f
                ll.form = Legend.LegendForm.CIRCLE
                ll.xEntrySpace = 10f
                ll.yEntrySpace = 20f
                ll.isWordWrapEnabled = true
                ll.verticalAlignment =Legend.LegendVerticalAlignment.TOP
                ll.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                ll.orientation = Legend.LegendOrientation.HORIZONTAL

                cumRevChart.description.isEnabled= false
                cumRevChart.setData(cumRevLineData)
            }

        }





        Log.i("HsiaoLing","UserManager.user=${UserManager.user}")

        var currentday = Calendar.getInstance().getTime()
        var currentMonth = currentday.time.toMonthFormat().toInt()
        var currentYear = currentday.time.toYearFormat().toInt()


        //Year Spinner Adapter
//        binding.textYear.adapter= SpinnerAdapter(
//            BaoApplication.instance.resources.getStringArray(R.array.year_list)
//        )
//
//        viewModel.selectedYearPosition.observe(this, Observer {
//            Log.i("HsiaoLing","viewModel.selectedYearPosition.observe, it=$it")
//        })
//        viewModel.yearChosen.observe(this, Observer {
//            Log.i("Hsiao","viewModel.deviceChosen.observe, it=$it")
//        })



        viewModel.uptoDateRev.observe(this, Observer {
            Log.e("HsiaoLing"," viewModel.uptoDateRev.observe=$it")

            // ge service list of user's revenue till yesterday
            val revlist = viewModel.rev.value!!
            Log.e("HsiaoLing"," viewModel.rev.value!!=$revlist")
            Log.e("HsiaoLing"," viewModel.rev.value!!.size=${revlist.size}")
            Log.e("HsiaoLing"," currentMonth=$currentMonth")

            // get day service list of user  for barChatr data as XAxis date value
            val list = viewModel.getServicesByDayGroup(viewModel.rev.value!!,currentMonth,currentYear)
            Log.w("HsiaoLing"," viewModel.getServicesByDayGroup=$list")

            // get day revenue list   for barChart data as YAxis value
            val entries:List<BarEntry> =viewModel.getBarEntries(list)
            Log.i("HsiaoLing","  viewModel.getBarEntries(list)=$entries")

            val cumRevEntries =viewModel.getCumRevBarEntries(list)
            Log.i("HsiaoLing","viewModel.getCumRevBarEntries(list)=$cumRevEntries")

            setBarChartData(entries, list)
            setLineChartData(cumRevEntries,list)

        })



        return binding.root
    }




}

