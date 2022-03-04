package com.android.farmist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.model.alertsResponse.GetGovtScheme
import com.android.farmist.model.alertsResponse.Scheme
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_alerts_govermentnews.view.*
import kotlinx.android.synthetic.main.adapter_news_all.view.*
import kotlinx.android.synthetic.main.adapter_news_announcements.view.*

class Adapter_Announcement_All()  : RecyclerView.Adapter<Adapter_Announcement_All.ViewHolder>() {

   lateinit var announcementAllList:List<Scheme> 
   lateinit var context: Context

    fun setList(productDataList: List<Scheme>,context: Context) {
        announcementAllList=productDataList
        this.context=context
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_alerts_govermentnews, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.TvSchemeTitle.setText(announcementAllList[position].title)
        holder.itemView.tvTimeDuration.setText(announcementAllList[position].createdAt)
        Glide.with(context).load(announcementAllList[position].image).into(holder.itemView.ivScheme)
        
        
        
        
        holder.itemView.setOnClickListener(View.OnClickListener {

            var bundle = bundleOf(
               // "newsId" to announcementAllList[position]._id,
                "newsImage" to announcementAllList[position].image,
                "newsTime" to announcementAllList[position].createdAt,
                "newsDec" to announcementAllList[position].desc,
                "newsTitle" to announcementAllList[position].title
            )
          Navigation.createNavigateOnClickListener(R.id.action_news_Announcement_all_Fragment_to_announcement_Detail_Fragment,bundle).onClick(holder.itemView)



        })

    }

    override fun getItemCount(): Int {
        return announcementAllList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}