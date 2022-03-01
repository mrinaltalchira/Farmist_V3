package com.android.farmist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_Announcement_All
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentAnnounceAllBinding
import com.android.farmist.model.alertsResponse.GetGovtScheme
import com.android.farmist.model.alertsResponse.Scheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Announce_All : Fragment() {
    private lateinit var binding : FragmentAnnounceAllBinding
    private val adapterAnnouncementAll by lazy { Adapter_Announcement_All() }
    private var createGroupList : ArrayList<String> = ArrayList()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_announce__all,container,false)
        return binding.root



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getGovScheme()

    }

    private fun bindUIViews(dataRespo: List<Scheme>) {

        activity?.let { adapterAnnouncementAll.setList(dataRespo, it.applicationContext) }
        binding.rvannounceall.adapter = adapterAnnouncementAll


    }

//    private fun setData(): ArrayList<String> {
//        for (i in 0 until 5) {
//
//            createGroupList.add("Group")
//        }
//
//        return createGroupList
//    }

    private fun getGovScheme() {
        val call: Call<GetGovtScheme>
        call = Api_Controller().getInstacneAdmin().getGovtscheme()
        call.enqueue(object : Callback<GetGovtScheme> {
            override fun onResponse(call: Call<GetGovtScheme>, response: Response<GetGovtScheme>) {
                val dataRespo= response.body()?.schemes
                if (dataRespo != null) {
                    bindUIViews(dataRespo)
                }


            }

            override fun onFailure(call: Call<GetGovtScheme>, t: Throwable) {
                Log.d("getNewsError", t.toString())
            }
        })
    }

}