package com.android.farmist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R

class Adapter_News_All()  : RecyclerView.Adapter<Adapter_News_All.ViewHolder>() {

    val incomeTrackerList: ArrayList<String> = ArrayList()

    fun setList(productDataList: ArrayList<String>) {
        incomeTrackerList.clear()
        incomeTrackerList.addAll(productDataList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_news_all, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list = incomeTrackerList[position]


    }

    override fun getItemCount(): Int {
        return incomeTrackerList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}