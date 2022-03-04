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
     private var createGroupList: ArrayList<String> = ArrayList()
    private var croppricelist: ArrayList<String> = ArrayList()
    lateinit var bdRoom:appDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

bdRoom = appDatabase.getAppDBInstance(requireActivity())


        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_alerts, container, false)
         getGovScheme()
        getNewsAlert()

        val list2 = bdRoom.getAppDao().getGov()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
               var a = Adapter_Alerts_Goverment_Schemes(requireActivity(),it)
                binding.rvgovernmentSchemes.adapter = a
                a.notifyDataSetChanged()
            })

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

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
               binding.rvalertsnews.adapter = adapterAlertsNews
                binding.rvalertsnews.layoutManager = LinearLayoutManager(activity?.applicationContext)
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
                 bdRoom.getAppDao().deleteGov()
                bdRoom.getAppDao().insertGov(response.body()!!.schemes)
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
             }

            override fun onFailure(call: Call<GetGovtScheme>, t: Throwable) {
                Log.d("getNewsError", t.toString())
            }
        })
    }

}