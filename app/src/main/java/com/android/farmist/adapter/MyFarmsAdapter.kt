package com.android.farmist.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.api.Api_Controller
import com.android.farmist.fragments.MyFarm_Fragment
import com.android.farmist.model.adapterGetFarm.AdaptedData
import com.android.farmist.model.setFarm.DeleteFarmRespo
import com.android.farmist.util.progressbars
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.customedialoug.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.contracts.contract

class MyFarmsAdapter(val context: Context, val data: List<AdaptedData>) :
    RecyclerView.Adapter<MyFarmsAdapter.MyFarmsViewHolder>() {

//private lateinit var mListener : onItemClickListner

//   interface onItemClickListner{
//       fun onItemClick(position: Int)
//   }


//fun setOnItemClickListener(listner: onItemClickListner){
//    mListener = listner
//}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFarmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapter_my_farms, parent, false)
//        return MyFarmsViewHolder(view,mListener)
        return MyFarmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyFarmsViewHolder, position: Int) {
        var datapositon = data[position]
        holder.adaptName.text = datapositon.name
        holder.adaptTehsil.text = datapositon.tehsil
        holder.adaptArea.text = datapositon.area
        holder.adaptAreatype.text = datapositon.areaType
        Glide.with(context).load(datapositon.image).into(holder.adaptImage)

        holder.adaptName.setOnClickListener {
            var bundel: Bundle = bundleOf("FarmID" to datapositon._id)
            var navController = Navigation.findNavController(it)
            navController.navigate(
                R.id.action_myFarm_Fragment_to_fragment_Farm_Info,
                bundel
            )
        }

        holder.adaptImage.setOnClickListener {

            var bundel: Bundle = bundleOf(
                "FarmID" to datapositon._id,
                "FarmImg" to datapositon.image,
                "FarmName" to datapositon.name,
                "FarmArea" to datapositon.area,
                "FarmAreaType" to datapositon.areaType,
                "FarmTehsil" to datapositon.tehsil,
                "FarmSurvey" to datapositon.surveyNum
            )
            var navController = Navigation.findNavController(it)
            navController.navigate(
                R.id.action_myFarm_Fragment_to_fragment_Farm_Info,
                bundel
            )
        }

        holder.adaptDelete.setOnClickListener {

            val view = View.inflate(context, R.layout.customedialoug, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.color.transe)

            view.yes.setOnClickListener {

                var progress = progressbars(context)
                dialog.dismiss()

                progress.showDialog()
                Api_Controller().getInstacne().deleteFarm(datapositon._id)
                    .enqueue(object : Callback<DeleteFarmRespo> {
                        override fun onResponse(
                            call: Call<DeleteFarmRespo>,
                            response: Response<DeleteFarmRespo>
                        ) {
                            var respon = response.body()
                            if (respon != null) {
                                progress.hidediloag()
                                return
                            }
                        }

                        override fun onFailure(
                            call: Call<DeleteFarmRespo>,
                            t: Throwable
                        ) {
                            Toast.makeText(context , "not success $t", Toast.LENGTH_SHORT).show()
                            progress.hidediloag()
                        }
                    })


            }
            view.no.setOnClickListener {

                dialog.dismiss()

                Toast.makeText(context.applicationContext, "cancel request", Toast.LENGTH_SHORT).show()
            }

        }

        holder.adaptEdit.setOnClickListener {


            val share =  context.getSharedPreferences("farmId", Context.MODE_PRIVATE)
            val editor = share.edit()
            editor.putString("ID", datapositon._id)
            editor.apply()
            val bbundle = bundleOf("EditFarmID" to datapositon._id)

            var navController = Navigation.findNavController(it)
            navController.navigate(
                R.id.action_myFarm_Fragment_to_fragment_editFarmDetails,
                bbundle
            )


        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    //    class MyFarmsViewHolder(itemView: View,listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
    class MyFarmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var adaptName: TextView = itemView.findViewById(R.id.tv_name_adapt_MyFarm)
        var adaptImage: ImageView = itemView.findViewById(R.id.img_adapt_MyFarm)
        var adaptTehsil: TextView = itemView.findViewById(R.id.tv_tehsil_adapt_MyFarm)
        var adaptArea: TextView = itemView.findViewById(R.id.tv_area_adapt_MyFarm)
        var adaptAreatype: TextView = itemView.findViewById(R.id.tv_areaType_adapt_MyFarm)
        var adaptDelete: TextView = itemView.findViewById(R.id.btn_delete_adapt_MyFarm)
        var adaptEdit: TextView = itemView.findViewById(R.id.btn_edit_adapt_MyFarm)
//        init {
//          itemView.setOnClickListener {
//              listner.onItemClick(adapterPosition)
//          }
//      }

    }


}