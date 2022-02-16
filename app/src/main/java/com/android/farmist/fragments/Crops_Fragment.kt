package com.android.farmist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.databinding.FragmentCropsBinding
import com.android.farmist.viewPager.ViewPagerAdapter


class Crops_Fragment : Fragment() {

    private lateinit var binding : FragmentCropsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_crops,container,false)

binding.backbtn.setOnClickListener {

    findNavController().navigate(R.id.action_crops_Fragment_to_nav_home)

}

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPagerData()

        binding.tvaddnewcrop.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_crops_Fragment_to_selectCropCategory,null)
        })
    }

    private fun setViewPagerData() {

        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(
            SowedFragment(),
            "Sowed"
        )
        viewPagerAdapter.addFragment(
            HarvestedFragment(),
            "Harvested"
        )

        binding.tabViewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.tabViewPager)

    }


}