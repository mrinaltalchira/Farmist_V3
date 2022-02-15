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
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New
import com.android.farmist.model.getSowedCrop.UserCrop
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class Adapter_Alerts_News(val context: Context, var data: List<New>) :
    RecyclerView.Adapter<Adapter_Alerts_News.ViewHolder>() {

//    val incomeTrackerList: ArrayList<String> = ArrayList()

    fun setList(DataList: List<New>) {
        this.data = DataList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_alerts_news, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNewstitle.setText(data[position].title)
        holder.tvTime.setText(data[position].createdAt)
        Glide.with(context).load(data[position].image).into(holder.ivNews)



        holder.itemView.setOnClickListener(View.OnClickListener {
            var bundle = bundleOf(
                "newsId" to data[position]._id,
                "newsImage" to data[position].image,
                "newsTime" to data[position].createdAt,
                "newsDec" to data[position].desc,
                "newsTitle" to data[position].title
            )


            Navigation.createNavigateOnClickListener(
                R.id.action_nav_alerts_to_newsDetail_Fragment,
                bundle
            ).onClick(holder.itemView)


        })


    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNewstitle: TextView = itemView.findViewById(R.id.newsAlertTitle)
        var tvTime: TextView = itemView.findViewById(R.id.newsTimeDuration)
        var ivNews: ImageView = itemView.findViewById(R.id.ivNewsAlert)


    }

}