package com.android.farmist.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.SelectCropCategory
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.selectCategoryResponse.Fruitlist
import com.android.farmist.model.selectCategoryResponse.Veg
import com.bumptech.glide.Glide

class Adapter_Select_categoryVeg(
    var data: List<Veg>,
    private var onselectitemVeg: SelectItemVeg,
    val context: Context
) :
    RecyclerView.Adapter<Adapter_Select_categoryVeg.ViewHolder>() {

    var checkData: Int = -1
    var checkData2: Int = -1


//    private var onItemClickListener: Adapter_Select_categoryVeg.OnItemClickListener? = null
//
//    fun setOnItemClickListener(onItemClickListener: Adapter_Select_categoryVeg.OnItemClickListener) {
//        this.onItemClickListener = onItemClickListener
//    }


    fun setList(DataList: List<Veg>) {
        this.data = DataList

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adpter_search_singlevalue, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvCroptitle.setText(data[position].name)
        Glide.with(context).load(data[position].image).into(holder.ivCrop)


        val obj: Veg = data[position]

        holder.itemView.setOnClickListener(View.OnClickListener {
            checkData=-1
//            Navigation.createNavigateOnClickListener(R.id.action_selectCropCategory_to_activity_Crop_Details_Fragment).onClick(holder.itemView)
//            mListener?.onItemClick(holder.itemView, data[position])
//            holder.itemView.setBackgroundRenn source(R.drawable.green_button_background)
//            holder.itemView.setBackgroundResource(R.drawable.green_button_background)
            checkData = position
            Toast.makeText(context, "veg........"+checkData, Toast.LENGTH_SHORT).show()
            onselectitemVeg.onItemSelect(obj)
            notifyDataSetChanged()
        })
        if (checkData == position) {
            checkData2=-1
            holder.tvCroptitle.setTextColor(Color.parseColor("#FFFFFF"))
            holder.itemView.setBackgroundResource(R.drawable.green_button_background)
            checkData=-1
            if (checkData2 == position) {
                holder.itemView.setBackgroundResource(R.drawable.loan_form_background)
                holder.tvCroptitle.setTextColor(Color.parseColor("#000000"))
                checkData2 = -1
            }
            else {

                checkData2 = position

            }
        }
        else {
            holder.itemView.setBackgroundResource(R.drawable.loan_form_background)
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, viewModel: Veg)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvCroptitle: TextView = itemView.findViewById(R.id.TvtittleSelectCrop)

        var ivCrop: ImageView = itemView.findViewById(R.id.ivSelectCropCategory)

    }

    interface SelectItemVeg {
        fun onItemSelect(viewModel: Veg)
    }

}