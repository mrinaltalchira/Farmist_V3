package com.android.farmist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.model.CropPriceResponse.Crop
import com.bumptech.glide.Glide

class Adapter_Crop_Prices_singleRow(val context: Context, var data: List<Crop>)  : RecyclerView.Adapter<Adapter_Crop_Prices_singleRow.ViewHolder>() {



    fun setList(DataList: List<Crop>) {
        this.data = DataList
      //  Toast.makeText(context, "setlist"+DataList, Toast.LENGTH_SHORT).show()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_other_market_crop_prices, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvCroptitle.setText(data[position].title)
        holder.tvPrice.setText("Rs. "+data[position].currentPrice)
        Glide.with(context).load(data[position].image).into(holder.ivCrop)


        holder.itemView.setOnClickListener(View.OnClickListener {
            var bundle = bundleOf(
                "cropId" to data[position].id
            )
            Navigation.createNavigateOnClickListener(R.id.action_cropPrices_to_marketPriceFragment,bundle).onClick(holder.itemView)


        })


    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvCroptitle: TextView = itemView.findViewById(R.id.TvtitleCropPrice)
        var tvPrice: TextView = itemView.findViewById(R.id.tvcropPrice)
        var ivCrop: ImageView = itemView.findViewById(R.id.ivcrop)

    }

}