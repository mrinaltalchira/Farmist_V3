package com.android.farmist.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

class ViewPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val stringArrayList = ArrayList<String>()
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        stringArrayList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return stringArrayList[position]
    }
}