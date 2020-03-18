package com.hsiaoling.bao.salesaomunt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry

import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.hsiaoling.bao.databinding.BarchartBinding


class BarChartFragment :Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = BarchartBinding.inflate(inflater, container, false)
        val revChart =  binding.revChart

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f, 0f))
        entries.add(BarEntry(2f, 1f))
        entries.add(BarEntry(3f, 2f ))
        var dataSet = BarDataSet(entries,"Label")







        return binding.root
    }





}




