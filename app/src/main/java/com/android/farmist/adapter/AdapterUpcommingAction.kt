package com.android.farmist.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.model.adapterGetFarm.AdaptedData
import com.android.farmist.model.upcommingAction.CropData
import com.bumptech.glide.Glide

class AdapterUpcommingAction(val context:Context, val data: ArrayList<CropData>)
    :RecyclerView.Adapter<AdapterUpcommingAction.UpcomingHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapt_file_for_upcomimg_action, parent, false)
        return UpcomingHolder(view)


    }

    override fun onBindViewHolder(holder: UpcomingHolder, position: Int) {
        var datapositon = data[position]
//        try {
            holder.adaptName.setText(datapositon.name.toString())
            Glide.with(context).load(datapositon.image).into(holder.adaptImg)
//        }catch (e:Exception){Log.d("",e.toString())}

    }

    override fun getItemCount(): Int {
      return data.size
    }


    class UpcomingHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        var adaptName: TextView = itemView.findViewById(R.id.upcomingAcitonNa)
        var adaptImg: ImageView = itemView.findViewById(R.id.upcomingimage)

    }

}