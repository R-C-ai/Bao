package com.hsiaoling.bao.addservice

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.databinding.ItemSpinnerBinding

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class SalesmanSpinnerAdapter(private val salesmans: List<Salesman>) : BaseAdapter() {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.title = salesmans[position].name

        return binding.root
    }

    override fun getItem(position: Int): Any {
        return salesmans[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return salesmans.size
    }
}