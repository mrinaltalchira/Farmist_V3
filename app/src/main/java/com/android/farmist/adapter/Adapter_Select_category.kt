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
import java.lang.Character.toLowerCase
import java.util.*
import kotlin.collections.ArrayList

class Adapter_Select_category(
    var data: ArrayList<Fruitlist>,
    private var onselectitem: SelectItem,
    val context: Context,
    var dataCrop: String
) : RecyclerView.Adapter<Adapter_Select_category.ViewHolder>(),
    Adapter_Select_categoryVeg.SelectItemVeg,Filterable{
    var checkData: Int = -1
    var checkData2: Int = -1
    var cropList = mutableListOf<Fruitlist>()
    var cropListFilter = mutableListOf<Fruitlist>()
//    var cropFiltera = mutableListOf<Fruitlist>()

    fun setList(DataList: List<Fruitlist>) {
        this.data = DataList as ArrayList<Fruitlist>
        this.cropList = DataList as ArrayList<Fruitlist>
        this.cropListFilter = DataList as ArrayList<Fruitlist>

        Toast.makeText(context, "setlist" + DataList, Toast.LENGTH_SHORT).show()
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
        val obj: Fruitlist = cropList[position]


        holder.itemView.setOnClickListener(View.OnClickListener {

//            Navigation.createNavigateOnClickListener(R.id.action_selectCropCategory_to_activity_Crop_Details_Fragment).onClick(holder.itemView)
//            holder.itemView.setBackgroundResource(R.drawable.green_button_background)
            checkData = position
            onselectitem.onItemSelect(obj)
            notifyDataSetChanged()
//            mListener?.onItemClick(holder.itemView, data[position])
        })

        if (checkData == position) {
            checkData2 = -1
            Toast.makeText(context, "fruits ......." + checkData, Toast.LENGTH_SHORT).show()

            holder.itemView.setBackgroundResource(R.drawable.green_button_background)
//            holder.tvCroptitle.setTextColor(Color.parseColor("#FFFFFF"))
            checkData = -1

            if (checkData2 == position) {
                holder.itemView.setBackgroundResource(R.drawable.loan_form_background)
//                holder.tvCroptitle.setTextColor(Color.parseColor("#000000"))
                checkData2 = -1
            } else {
                checkData2 = position


            }
        } else {

            holder.itemView.setBackgroundResource(R.drawable.loan_form_background)
        }


    }

    override fun getItemCount(): Int {
        return cropList.size
    }
//    fun getFilter(): Filter {
//        return cityFilter
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvCroptitle: TextView = itemView.findViewById(R.id.TvtittleSelectCrop)

        var ivCrop: ImageView = itemView.findViewById(R.id.ivSelectCropCategory)

    }

    interface SelectItem {
        fun onItemSelect(viewModel: Fruitlist)
    }


    override fun onItemSelect(viewModel: Veg) {
        dataCrop = viewModel.name

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
                  val itemModal=ArrayList<Fruitlist>()
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
              cropList=filterResult!!.values as ArrayList<Fruitlist>
              notifyDataSetChanged()

          }
      }

    }
}




