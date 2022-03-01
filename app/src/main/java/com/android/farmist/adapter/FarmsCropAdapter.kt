package com.android.farmist.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.model.getFarm.farmsccrop
import com.bumptech.glide.Glide

class FarmsCropAdapter(val context: Context, val data:List<farmsccrop>):RecyclerView.Adapter<FarmsCropAdapter.FarmsViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.farmscroplayout,parent,false)
        return FarmsCropAdapter.FarmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FarmsViewHolder, position: Int) {
       var posi = data[position]

        holder.cropName.setText(posi.name)
        try {
            Glide.with(context).load(posi.image).into(holder.cropImage)
        }catch (e:Exception){
            Log.d("","")}
    }

    override fun getItemCount(): Int {
        return data.size
    }



    class FarmsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var cropName:TextView = itemView.findViewById(R.id.name)
        var cropImage:ImageView = itemView.findViewById(R.id.image)
    }

}