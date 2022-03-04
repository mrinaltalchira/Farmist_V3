package com.android.farmist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.farmist.R
import com.android.farmist.RoomDatabase.appDatabase
import com.android.farmist.adapter.Adapter_Alerts_News
import com.android.farmist.adapter.Adapter_News_All
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentNewsAllBinding
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New
import com.android.farmist.viewModel.Account_View_Model
import com.android.farmist.viewModel.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class News_All : Fragment() {

    private lateinit var binding : FragmentNewsAllBinding
    private val adapterNewsAll by lazy { Adapter_News_All() }
    private var createGroupList : ArrayList<String> = ArrayList()
//    private lateinit var recyclerViewAdapter: Adapter_News_All
    lateinit var viewModel: NewsViewModel
    lateinit var appDatabaseObj: appDatabase




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_news_all,container,false)
        return binding.root



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDatabaseObj= appDatabase.getAppDBInstance(requireContext())

        getNewsAlert()
        val list =appDatabaseObj.getAppDao().getnews().observe(requireActivity(), Observer<List<New>>{

            activity?.let { it1 -> adapterNewsAll.setList(it, it1.applicationContext) }
            binding.rvnewsall.adapter = adapterNewsAll
                adapterNewsAll.notifyDataSetChanged()
        })
//        initViewModel()
//        initMainViewModel()
    }

    private fun getNewsAlert() {
        val call: Call<GetNewsAlert>
        call= Api_Controller().getInstacneAdmin().getNewsAlert()
        call.enqueue(object : Callback<GetNewsAlert> {
            override fun onResponse(call: Call<GetNewsAlert>, response: Response<GetNewsAlert>) {


                val   adapterAlertsNews=
                    response.body()?.let { activity?.applicationContext?.let { it1 ->
                        Adapter_Alerts_News(
                            )
                        adapterNewsAll.setList(it.news,it1.applicationContext)
                    } }

                val dataRespo= response.body()?.news
                appDatabaseObj.getAppDao().deleteAllRecords()
                appDatabaseObj.getAppDao().insertAll(dataRespo!!)
            }

            override fun onFailure(call: Call<GetNewsAlert>, t: Throwable) {
                Log.d("getNewsError",t.toString())
            }
        })
    }






}