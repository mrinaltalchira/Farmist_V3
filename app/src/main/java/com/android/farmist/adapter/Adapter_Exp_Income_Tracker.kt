package com.android.farmist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.api.Api_Controller
import com.android.farmist.model.ExpensesIncomeTrackerResponse.Data
import com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpensesTrackerResponse.GetExpensesTracker
import com.android.farmist.model.alertsResponse.New
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.adaapter_exp_income_tracker.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Adapter_Exp_Income_Tracker(val context: Context, var data: List<Data>)  : RecyclerView.Adapter<Adapter_Exp_Income_Tracker.ViewHolder>() {

//    val incomeTrackerList: ArrayList<String> = ArrayList()

    fun setList(DataList: List<Data>) {
        this.data = DataList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adaapter_exp_income_tracker, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val list = incomeTrackerList[position]

        holder.tvtitle.setText(data[position].name)
        holder.tvArea.setText(data[position].area+" "+data[position].areaType)
        holder.tvSowed.setText(data[position].date)
        Glide.with(context).load(data[position].image).into(holder.ivprofile)

        holder.itemView.setOnClickListener(View.OnClickListener {
            var bundle = bundleOf(
                "cropId" to data[position].cropId
            )
            Navigation.createNavigateOnClickListener(R.id.action_expensess_Income_tracker_to_expenses_Tracker_Fragment, bundle).onClick(holder.itemView)
        })
        holder.itemView.addexp.setOnClickListener(View.OnClickListener {
            var bundle = bundleOf(
                "cropId" to data[position].cropId
            )
            Navigation.createNavigateOnClickListener(R.id.action_expensess_Income_tracker_to_add_Expenses_Fragment,bundle).onClick(holder.itemView)
        })

        holder.itemView.tvaddsubsidy.setOnClickListener(View.OnClickListener {

            var bundle = bundleOf(
                "cropId" to data[position].cropId)
            Navigation.createNavigateOnClickListener(R.id.action_expensess_Income_tracker_to_add_Subsidy_Fragment,bundle).onClick(holder.itemView)
        })

        holder.itemView.tvaddincome.setOnClickListener(View.OnClickListener {
            var bundle = bundleOf(
                "cropId" to data[position].cropId)
            Navigation.createNavigateOnClickListener(R.id.action_expensess_Income_tracker_to_add_Income_Fragment, bundle).onClick(holder.itemView)

        })

                   var call: Call<GetExpensesTracker> = Api_Controller.apiInterface.getExpenses(data[position].cropId)
            call.enqueue(object : Callback<GetExpensesTracker> {
                override fun onResponse(
                    call: Call<GetExpensesTracker>,
                    response: Response<GetExpensesTracker>
                ) {

                    holder.expen.setText("Rs. "+response.body()?.data?.userExpense)

                }

                override fun onFailure(call: Call<GetExpensesTracker>, t: Throwable) {


                }
            })

        }


    override fun getItemCount(): Int {
        return data.size

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvtitle: TextView = itemView.findViewById(R.id.TvCropTitleIncomeExpesesTracker)
        var tvArea: TextView = itemView.findViewById(R.id.TvCropAreaIncomeExpesesTracker)
         var tvSowed: TextView = itemView.findViewById(R.id.TvCropSowedIncomeExpesesTracker)
        var ivprofile: CircleImageView = itemView.findViewById(R.id.iv_profile)
    var expen:TextView = itemView.findViewById(R.id.TvCropExpensesIncomeExpesesTracker)

    }

}