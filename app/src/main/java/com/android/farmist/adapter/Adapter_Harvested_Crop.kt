package com.android.farmist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R

class Adapter_Harvested_Crop()  : RecyclerView.Adapter<Adapter_Harvested_Crop.ViewHolder>() {

    val incomeTrackerList: ArrayList<String> = ArrayList()

    fun setList(productDataList: ArrayList<String>) {
        incomeTrackerList.clear()
        incomeTrackerList.addAll(productDataList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_harvest, parent, false)
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