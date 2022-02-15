package com.android.farmist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_Harvested_Crop
import com.android.farmist.adapter.Adapter_News_All
import com.android.farmist.databinding.FragmentHarvestedBinding
import com.android.farmist.databinding.FragmentNewsAllBinding

class News_All : Fragment() {

    private lateinit var binding : FragmentNewsAllBinding
    private val adapterHarvestedCrop by lazy { Adapter_News_All() }
    private var createGroupList : ArrayList<String> = ArrayList()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_news_all,container,false)
        return binding.root



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUIViews()

    }

    private fun bindUIViews(){
        setData()
        adapterHarvestedCrop.setList(createGroupList)
        binding.rvnewsall.adapter = adapterHarvestedCrop


    }

    private fun setData(): ArrayList<String> {
        for (i in 0 until 5) {

            createGroupList.add("Group")
        }

        return createGroupList
    }


}