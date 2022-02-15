package com.android.farmist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_Harvested_Crop
import com.android.farmist.adapter.Adapter_farm_States
import com.android.farmist.databinding.FarmStatusBinding
import com.android.farmist.databinding.FragmentHomeBinding


class Fragment_Farm_States : Fragment() {

    private lateinit var binding : FarmStatusBinding
    private val adapterFarmStates by lazy { Adapter_farm_States() }
    private var createGroupList : ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.farm_status,container,false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUIViews()

    }

    private fun bindUIViews(){
        setData()
        adapterFarmStates.setList(createGroupList)
        binding.rvfarmstates.adapter = adapterFarmStates


    }

    private fun setData(): ArrayList<String> {
        for (i in 0 until 5) {

            createGroupList.add("Group")
        }

        return createGroupList
    }

}