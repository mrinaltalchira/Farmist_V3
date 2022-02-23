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
import com.android.farmist.model.harvested.Data
import com.bumptech.glide.Glide

class Adapter_Harvested_Crop(var context: Context) :
    RecyclerView.Adapter<Adapter_Harvested_Crop.ViewHolder>() {

    var harvestedList: ArrayList<Data> = ArrayList()
    fun setList(productDataList: ArrayList<Data>) {
        this.harvestedList = productDataList
        Toast.makeText(context, "adapter :- $harvestedList", Toast.LENGTH_SHORT).show()

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_harvest, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list = harvestedList[position]

        holder.name.setText(list.name)
        holder.area.setText(list.area)
        holder.areaType.setText(list.areaType)
        Glide.with(context).load(list.image).into(holder.image)

        holder.more.setOnClickListener {

            val bundle = bundleOf("cropId" to list.cropId.toString())

            Navigation.createNavigateOnClickListener(R.id.action_crops_Fragment_to_harvested_Crop_Info,bundle)
                .onClick(holder.itemView)

        }
    }
    override fun getItemCount(): Int {
        return harvestedList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.tvharvestname)
        var area: TextView = itemView.findViewById(R.id.tvharvest_area)
        var image: ImageView = itemView.findViewById(R.id.iv_harvest_Image)
        var areaType: TextView = itemView.findViewById(R.id.tvharvestareaType)
        var more: TextView = itemView.findViewById(R.id.harvested_moreDetail)

    }

}