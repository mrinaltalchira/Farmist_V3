package com.android.farmist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.farmist.R
import com.android.farmist.model.alertsResponse.New
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_news_all.view.*

class Adapter_News_All()  : RecyclerView.Adapter<Adapter_News_All.ViewHolder>() {

    lateinit var newsList:List<New>
    lateinit var context:Context

    fun setList(productDataList: List<New>?, context:Context) {
        this.context=context

        if (productDataList != null) {
            newsList=productDataList
        }

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_news_all, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list = newsList[position]
        holder.itemView.tvTitleNewsAllAdapter.setText(newsList[position].title)
        holder.itemView.tvTimeNewsAll.setText(newsList[position].createdAt)
        Glide.with(context).load(newsList[position].image).into(holder.itemView.ivNewsAllAdpter)

        holder.itemView.setOnClickListener {
            var bundle = bundleOf(
//                "newsId" to newsList[position]._id,
                "newsImage" to newsList[position].image,
                "newsTime" to newsList[position].createdAt,
                "newsDec" to newsList[position].desc,
                "newsTitle" to newsList[position].title
            )

            Navigation.createNavigateOnClickListener(
                R.id.action_news_Announcement_all_Fragment_to_newsDetail_Fragment,
                bundle
            ).onClick(holder.itemView)
        }


    }

    override fun getItemCount(): Int {
        return newsList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}