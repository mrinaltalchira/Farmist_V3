package com.android.farmist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_Harvested_Crop
import com.android.farmist.databinding.NewsDetailsBinding
import com.android.farmist.model.harvested.GetHarvestedCrop
import com.bumptech.glide.Glide


class NewsDetail_Fragment : Fragment() {
    private lateinit var binding : NewsDetailsBinding
//    private val adapterHarvestedCrop by lazy { Adapter_Harvested_Crop(requireActivity()) }
    private var createGroupList : ArrayList<String> = ArrayList()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.news_details,container,false)
        return binding.root



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUIViews()






    }

    private fun bindUIViews(){
        val newsId: String? = arguments?.getString("newsId")
        val newsTitle: String? = arguments?.getString("newsTitle")
        val newsImg: String? = arguments?.getString("newsImage")
        val newsDec: String? = arguments?.getString("newsDec")
        val newsTime: String? = arguments?.getString("newsTime")

        binding.TvnewsTitle.setText(newsTitle)
        binding.TvnewsDesc.setText(newsDec)
        binding.TvnewsTime.setText(newsTime)
        Glide.with(this).load(newsImg).into(binding.ivNewsImage)

    }




}