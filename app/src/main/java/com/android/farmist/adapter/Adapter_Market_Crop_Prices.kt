package com.android.farmist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.CropPriceResponse.Data
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_market_crop_prices.view.*

class Adapter_Market_Crop_Prices( )  : RecyclerView.Adapter<Adapter_Market_Crop_Prices.ViewHolder>() {
    lateinit var context: Context
    lateinit var data: List<Data>

    fun setList(DataList: List<Data>, context: Context) {
        this.context=context
        this.data = DataList
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_market_crop_prices, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.itemView.tvtitleMycropPrices.setText(data[position].title)
        holder.itemView.tvPriceMycrop.setText("Rs. "+data[position].price)
        Glide.with(context).load(data[position].image).into(holder.itemView.ivMycropImage)

        holder.itemView.setOnClickListener(View.OnClickListener {
            var bundle = bundleOf(
                "cropId" to data[position].id
            )
            Navigation.createNavigateOnClickListener(R.id.action_nav_prices_to_marketPriceFragment,bundle).onClick(holder.itemView)

        })


    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}