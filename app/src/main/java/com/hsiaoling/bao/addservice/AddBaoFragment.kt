package com.hsiaoling.bao.addservice


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels

import com.hsiaoling.bao.R
import com.hsiaoling.bao.databinding.FragmentAddBaoBinding
import com.hsiaoling.bao.ext.getVmFactory

/**
 * A simple [Fragment] subclass.
 */
class AddBaoFragment : Fragment() {

//    private val viewModel by viewModel<AddBaoViewModel>{getVmFactory()}

lateinit var binding:FragmentAddBaoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bao, container, false)
    }


}
