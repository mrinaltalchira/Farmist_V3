package com.android.farmist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

import androidx.core.os.bundleOf
import com.android.farmist.api.Api_Controller

import com.android.farmist.fragments.Crop_info_Fragment
import com.android.farmist.model.alertsResponse.New
import com.android.farmist.model.getSowedCrop.GetSowedCrop
import com.android.farmist.model.getSowedCrop.ProgressTracker
import com.android.farmist.model.getSowedCrop.UserCrop
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Adapter_Sowed_Crop(val context: Context, val data: List<UserCrop>) :
    RecyclerView.Adapter<Adapter_Sowed_Crop.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_sowed, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCropName.setText(data?.get(position)?.cropName)
        holder.tvArea.setText(data?.get(position)?.sowedArea + " " + (data?.get(position)?.areaType))
        Glide.with(context).load(data?.get(position)?.image).into(holder.ivsowed)
        holder.cropId= data?.get(position)?._id.toString()

            holder.tvMoreDetail.setOnClickListener {
                var navController = Navigation.findNavController(it)


                val bundle = bundleOf("cropId" to holder.cropId)
                navController!!.navigate(R.id.crop_info_Fragment, bundle)


            }


            val call: Call<ProgressTracker> = Api_Controller.apiInterface.getProgress(holder.cropId)
            call.enqueue(object : Callback<ProgressTracker> {
                override fun onResponse(
                    call: Call<ProgressTracker>,
                    response: Response<ProgressTracker>
                ) {
                    var respo = response.body()
                    if (respo != null) {

                   holder.tvOne.setText("Add fertilizer in "+ respo.fertilizeDays + " days")
                   holder.tvTwo.setText(respo.harvestDays +" days left for harvest")
                    }
                }
                override fun onFailure(call: Call<ProgressTracker>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "error found :- $t",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }


    override fun getItemCount(): Int {
        return data!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCropName: TextView = itemView.findViewById(R.id.tvsowedname)
        var tvMoreDetail: TextView = itemView.findViewById(R.id.tvSowedMoreDetails)
        var tvArea: TextView = itemView.findViewById(R.id.tvsowedacers)
        var tvOne: TextView = itemView.findViewById(R.id.tvaddfertilizer)
        var tvTwo: TextView = itemView.findViewById(R.id.tv_makeHarvest)
        var ivsowed: CircleImageView = itemView.findViewById(R.id.ivsowed)
        lateinit var cropId: String
    }

}