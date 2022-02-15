package com.android.farmist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_Exp_Income_Tracker
import com.android.farmist.adapter.Adapter_Harvested_Crop
import com.android.farmist.databinding.FragmentExpensessIncomeTrackerBinding
import com.android.farmist.databinding.FragmentHarvestedBinding


class HarvestedFragment : Fragment() {
    private lateinit var binding : FragmentHarvestedBinding
    private val adapterHarvestedCrop by lazy { Adapter_Harvested_Crop() }
    private var createGroupList : ArrayList<String> = ArrayList()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_harvested,container,false)
        return binding.root



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUIViews()

    }

    private fun bindUIViews(){
        setData()
        adapterHarvestedCrop.setList(createGroupList)
        binding.rvharvest.adapter = adapterHarvestedCrop


    }

    private fun setData(): ArrayList<String> {
        for (i in 0 until 5) {

            createGroupList.add("Group")
        }

        return createGroupList
    }


}