package com.android.farmist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.android.farmist.R
import com.android.farmist.RoomDatabase.Repository
import com.android.farmist.RoomDatabase.appDatabase
import com.android.farmist.adapter.*
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentAlertsBinding
import com.android.farmist.model.alertsResponse.GetGovtScheme
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New
import com.android.farmist.viewModel.NewsViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_alerts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class Alerts_Fragment : Fragment() {


    private lateinit var binding: FragmentAlertsBinding
    lateinit var adapterAlertsNews: Adapter_Alerts_News
//    private val adapterAlertsGovermentSchemes by lazy { Adapter_Alerts_Goverment_Schemes() }
    private var createGroupList: ArrayList<String> = ArrayList()
    private var croppricelist: ArrayList<String> = ArrayList()

//    lateinit var newsViewModel: NewsViewModel
//    lateinit var newsList:List<New>
//    lateinit var repository: Repository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_alerts, container, false)
//        getNewsAlert()
        getGovScheme()
        getNewsAlert()

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUIViews()

//        repository=Repository(activity?.application)
//        newsList=ArrayList<New>()
//        newsViewModel=ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)







    }
//
//    private fun getGovScheme() {
//        val call: Call<GetNewsAlert>
//        call = Api_Controller().getInstacneAdmin().getNewsAlert()
//        call.enqueue(object : Callback<GetNewsAlert> {
//            override fun onResponse(call: Call<GetNewsAlert>, response: Response<GetNewsAlert>) {
//                val responseList=response.body()?.news
//
//
//                binding.rvnewsannouncment.adapter = adapterNewsAnnouncements
//                if (responseList != null) {
//                    adapterNewsAnnouncements.setList(responseList,requireContext())
//                }
//            }
//
//            override fun onFailure(call: Call<GetNewsAlert>, t: Throwable) {
//                Log.d("getNewsError", t.toString())
//            }
//        })
//    }

    //rx java
//    private fun getNewsAlert() {
//        adapterAlertsNews= activity?.let { Adapter_Alerts_News(it.applicationContext,ArrayList<New>()) }!!
//        binding.rvalertsnews.setHasFixedSize(true)
//        binding.rvalertsnews.layoutManager = LinearLayoutManager(activity?.applicationContext)
//        binding.rvalertsnews.adapter = adapterAlertsNews
//
//        val compositeDisposable= CompositeDisposable()
//        compositeDisposable.add(getObservable().subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe ({ response->getObserver(response as GetNewsAlert)},
//                {t->onFailure(t)}
//            ))
//
//
//    }
//
//    private fun getObservable():Observable<GetNewsAlert>
//    {
//        return Api_Controller.apiInterface.getNewsAlert()
//    }
//
//    private fun getObserver(newsData:GetNewsAlert)
//    {
//        if (newsData!=null)
//        {
//
//            val newsList=newsData.news
//            val db=Room.databaseBuilder(activity?.applicationContext!!,appDatabase::class.java,"newsAlert")
//                .build()
//         val dbNews= db.newsDao().insertAll(newsList)
//
//            Toast.makeText(requireActivity(), "dbnews: $dbNews", Toast.LENGTH_SHORT).show()
//            adapterAlertsNews.setList(newsList)
//        }
//
//    }
//    private fun onFailure(t:Throwable)
//    {
//        Log.d("Main", "OnFailure: "+ t.message)
//    }

        private fun getNewsAlert() {
        val call: Call<GetNewsAlert>
        call= Api_Controller().getInstacneAdmin().getNewsAlert()
        call.enqueue(object :Callback<GetNewsAlert>{
            override fun onResponse(call: Call<GetNewsAlert>, response: Response<GetNewsAlert>) {


                val   adapterAlertsNews=
                    response.body()?.let { activity?.applicationContext?.let { it1 ->
                        Adapter_Alerts_News(
                            it1, it.news)
                    } }
//                repository=Repository(activity?.application)
//                newsList=ArrayList<New>()
//                newsViewModel=ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
//                val responseData=response.body()?.news
//                repository.insert(responseData)

                binding.rvalertsnews.adapter = adapterAlertsNews
                binding.rvalertsnews.layoutManager = LinearLayoutManager(activity?.applicationContext)
//                Toast.makeText(requireContext(), "news${response.body().toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<GetNewsAlert>, t: Throwable) {
              Log.d("getNewsError",t.toString())
            }
        })
    }
    private fun getGovScheme() {
        val call: Call<GetGovtScheme>
        call = Api_Controller().getInstacneAdmin().getGovtscheme()
        call.enqueue(object : Callback<GetGovtScheme> {
            override fun onResponse(call: Call<GetGovtScheme>, response: Response<GetGovtScheme>) {


                val adapterAlertsGovermentSchemes =
                    response.body()?.let {
                        activity?.let { it1 ->
                            Adapter_Alerts_Goverment_Schemes(
                                it1.applicationContext, it.schemes
                            )
                        }
                    }
                binding.rvgovernmentSchemes.adapter = adapterAlertsGovermentSchemes
                binding.rvgovernmentSchemes.layoutManager =
                    LinearLayoutManager(activity?.applicationContext)
//                Toast.makeText(activity?.applicationContext, "news${response.body().toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<GetGovtScheme>, t: Throwable) {
                Log.d("getNewsError", t.toString())
            }
        })
    }


    private fun bindUIViews() {
//        setData()
//
//        adapterAlertsGovermentSchemes.setList(croppricelist)
//        binding.rvalertsnews.adapter = adapterAlertsNews
//        binding.rvgovernmentSchemes.adapter = adapterAlertsGovermentSchemes


    }

    private fun setData(): ArrayList<String> {
        for (i in 0 until 2) {

            createGroupList.add("Group")
            croppricelist.add("Crop")
        }

        return createGroupList
        return croppricelist
    }

}