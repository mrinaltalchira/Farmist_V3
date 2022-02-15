package com.android.farmist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.farmist.R
import com.android.farmist.databinding.FragmentCropsBinding
import com.android.farmist.databinding.FragmentNewsAnnouncementAllBinding
import com.android.farmist.viewPager.ViewPagerAdapter


class News_Announcement_all_Fragment : Fragment() {

    private lateinit var binding :  FragmentNewsAnnouncementAllBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_news__announcement_all_,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPagerData()
    }

    private fun setViewPagerData() {

        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(
            News_All(),
            "News"
        )
        viewPagerAdapter.addFragment(
            Announce_All(),
            "Announcement"
        )

        binding.tabViewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.tabViewPager)



    }


}