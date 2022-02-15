package com.android.farmist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.databinding.FragmentHomeBinding
import com.android.farmist.databinding.LoansSubsidiesInfoBinding


class Loan_Subsidies_Fragment : Fragment() {

    private lateinit var binding : LoansSubsidiesInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.loans_subsidies_info,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


}