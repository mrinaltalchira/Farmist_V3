package com.android.farmist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_Alerts_News
import com.android.farmist.adapter.Adapter_News_All
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentNewsAllBinding
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class News_All : Fragment() {

    private lateinit var binding : FragmentNewsAllBinding
    private val adapterNewsAll by lazy { Adapter_News_All() }
    private var createGroupList : ArrayList<String> = ArrayList()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_news_all,container,false)
        return binding.root



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNewsAlert()

    }

    private fun bindUIViews(dataRespo: List<New>?) {

        adapterNewsAll.setList(dataRespo,requireActivity())
        binding.rvnewsall.adapter = adapterNewsAll


    }

    private fun getNewsAlert() {
        val call: Call<GetNewsAlert>
        call= Api_Controller().getInstacneAdmin().getNewsAlert()
        call.enqueue(object : Callback<GetNewsAlert> {
            override fun onResponse(call: Call<GetNewsAlert>, response: Response<GetNewsAlert>) {


                val   adapterAlertsNews=
                    response.body()?.let { activity?.applicationContext?.let { it1 ->
                        Adapter_Alerts_News(
                            it1, it.news)
                    } }

                val dataRespo= response.body()?.news
                bindUIViews(dataRespo)
//                binding.rvnewsall.adapter = adapterAlertsNews
//                binding.rvnewsall.layoutManager = LinearLayoutManager(activity?.applicationContext)
////                Toast.makeText(requireContext(), "news${response.body().toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<GetNewsAlert>, t: Throwable) {
                Log.d("getNewsError",t.toString())
            }
        })
    }


}