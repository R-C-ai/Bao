package com.hsiaoling.bao.addservice


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.hsiaoling.bao.R
import com.roundtableapps.timelinedayviewlibrary.EventView
import kotlinx.android.synthetic.main.fragment_add_new_job.*

/**
 * A simple [Fragment] subclass.
 */
class AddNewJobFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add_new_job, container, false)
    }


}
