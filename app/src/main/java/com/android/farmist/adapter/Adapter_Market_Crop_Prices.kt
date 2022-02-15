package com.android.farmist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.alertsResponse.New

class Adapter_Market_Crop_Prices(val context: Context, var data: List<Crop>)  : RecyclerView.Adapter<Adapter_Market_Crop_Prices.ViewHolder>() {

    val incomeTrackerList: ArrayList<String> = ArrayList()

    fun setList(DataList: List<Crop>) {
        this.data = DataList
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_market_crop_prices, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list = incomeTrackerList[position]

        holder.itemView.setOnClickListener(View.OnClickListener {
            Navigation.createNavigateOnClickListener(R.id.action_nav_prices_to_marketPriceFragment).onClick(holder.itemView)


        })


    }

    override fun getItemCount(): Int {
        return incomeTrackerList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}