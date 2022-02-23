package com.android.farmist.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.SelectCropCategory
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.selectCategoryResponse.Fruitlist
import com.android.farmist.model.selectCategoryResponse.Veg
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class Adapter_Select_categoryVeg(
    var data: List<Veg>,
    private var onselectitemVeg: SelectItemVeg,
    val context: Context

) :
    RecyclerView.Adapter<Adapter_Select_categoryVeg.ViewHolder>(),Filterable {

    var checkData: Int = -1
    var checkData2: Int = -1
    var cropList = mutableListOf<Veg>()
    var cropListFilter = mutableListOf<Veg>()


//    private var onItemClickListener: Adapter_Select_categoryVeg.OnItemClickListener? = null
//
//    fun setOnItemClickListener(onItemClickListener: Adapter_Select_categoryVeg.OnItemClickListener) {
//        this.onItemClickListener = onItemClickListener
//    }


    fun setList(DataList: List<Veg>) {
        this.data = DataList
        this.cropList = DataList as ArrayList<Veg>
        this.cropListFilter = DataList as ArrayList<Veg>

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adpter_search_singlevalue, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvCroptitle.setText(cropList[position].name)
        Glide.with(context).load(cropList[position].image).into(holder.ivCrop)


        val obj: Veg = cropList[position]

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
//            holder.tvCroptitle.setTextColor(Color.parseColor("#FFFFFF"))
            holder.itemView.setBackgroundResource(R.drawable.green_button_background)
            checkData=-1
            if (checkData2 == position) {
                holder.itemView.setBackgroundResource(R.drawable.loan_form_background)
//                holder.tvCroptitle.setTextColor(Color.parseColor("#000000"))
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
        return cropList.size
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

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filterResults=FilterResults();
                if (charSequence==null||charSequence.length<0)
                {
                    filterResults.count=cropListFilter.size
                    filterResults.values=cropListFilter

                }
                else{
                    var searchChar=charSequence.toString()
                    val itemModal=ArrayList<Veg>()
                    for (item in cropListFilter)
                    {
                        if (item.name.lowercase(Locale.ROOT).contains(searchChar))
                        {
                            itemModal.add(item)
                        }

                    }
                    filterResults.count=itemModal.size
                    filterResults.values=itemModal
                }
                return filterResults

            }

            override fun publishResults(p0: CharSequence?, filterResult: FilterResults?) {
                cropList=filterResult!!.values as ArrayList<Veg>
                notifyDataSetChanged()

            }
        }

    }

}