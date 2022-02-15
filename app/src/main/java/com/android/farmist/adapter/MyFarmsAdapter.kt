package com.android.farmist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.model.adapterGetFarm.AdaptedData
import com.android.farmist.model.adapterGetFarm.AdeptDataResponce
import com.bumptech.glide.Glide
import java.lang.Exception

class MyFarmsAdapter(val context: Context, val data: List<AdaptedData>) :
    RecyclerView.Adapter<MyFarmsAdapter.MyFarmsViewHolder>(){

private lateinit var mListener : onItemClickListner

   interface onItemClickListner{
       fun onItemClick(position: Int)
   }


fun setOnItemClickListener(listner: onItemClickListner){
    mListener = listner
}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFarmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapter_my_farms, parent, false)
        return MyFarmsViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: MyFarmsViewHolder, position: Int) {
        var datapositon = data[position]
        holder.adaptName.text = datapositon.name
        holder.adaptTehsil.text = datapositon.tehsil
        holder.adaptArea.text = datapositon.area
        holder.adaptAreatype.text = datapositon.areaType
       Glide.with(context).load(datapositon.image).into(holder.adaptImage)


    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyFarmsViewHolder(itemView: View,listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {

        var adaptName : TextView = itemView.findViewById(R.id.tv_name_adapt_MyFarm)
        var adaptImage : ImageView = itemView.findViewById(R.id.img_adapt_MyFarm)
        var adaptTehsil : TextView = itemView.findViewById(R.id.tv_area_adapt_MyFarm)
        var adaptArea : TextView = itemView.findViewById(R.id. tv_areaType_adapt_MyFarm)
        var adaptAreatype : TextView = itemView.findViewById(R.id.tv_tehsil_adapt_MyFarm)

      init {
          itemView.setOnClickListener {
              listner.onItemClick(adapterPosition)
          }
      }

       }
}