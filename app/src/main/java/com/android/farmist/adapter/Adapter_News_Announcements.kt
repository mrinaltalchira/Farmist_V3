package com.android.farmist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.model.alertsResponse.New
import com.bumptech.glide.Glide

class Adapter_News_Announcements()  : RecyclerView.Adapter<Adapter_News_Announcements.ViewHolder>() {
    lateinit var context:Context
    var incomeTrackerList: List<New> = ArrayList()

    fun setList(productDataList: List<New>,context: Context) {
        this.context=context

        incomeTrackerList=productDataList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_news_announcements, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list = incomeTrackerList[position]
        holder.tvNewstitle.setText(incomeTrackerList[position].title)
        holder.tvTime.setText(incomeTrackerList[position].createdAt)
        Glide.with(context).load(incomeTrackerList[position].image).into(holder.ivNews)


        holder.itemView.setOnClickListener(View.OnClickListener {

            var bundle = bundleOf(
                "newsId" to incomeTrackerList[position]._id,
                "newsImage" to incomeTrackerList[position].image,
                "newsTime" to incomeTrackerList[position].createdAt,
                "newsDec" to incomeTrackerList[position].desc,
                "newsTitle" to incomeTrackerList[position].title
            )
          Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_newsDetail_Fragment,bundle).onClick(holder.itemView)


        })

    }

    override fun getItemCount(): Int {
        return incomeTrackerList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var tvNewstitle: TextView = itemView.findViewById(R.id.tvTitleNews)
        var tvTime: TextView = itemView.findViewById(R.id.tvTimeNewsAndAnnouncements)
        var ivNews: ImageView = itemView.findViewById(R.id.ivnewsAndAnnouncement)
    }

}