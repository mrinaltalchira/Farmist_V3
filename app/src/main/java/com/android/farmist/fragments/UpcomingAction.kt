package com.android.farmist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.farmist.R
import com.android.farmist.databinding.FragmentUpcomingActionBinding

class UpcomingAction : Fragment() {

    lateinit var binding:FragmentUpcomingActionBinding
    lateinit var yaer:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_upcoming_action,container,false)

        binding.calenderLayout.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(requireActivity(), "$year , $month , $dayOfMonth", Toast.LENGTH_SHORT).show()

        }



         return binding.root
    }

}