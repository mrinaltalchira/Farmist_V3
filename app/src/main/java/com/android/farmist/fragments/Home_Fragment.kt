package com.android.farmist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.android.farmist.R
import com.android.farmist.RoomDatabase.appDatabase
import com.android.farmist.adapter.*
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentHomeBinding
import com.android.farmist.model.alertsResponse.GetGovtScheme
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Home_Fragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val adapterNewsAnnouncements by lazy { Adapter_News_Announcements() }
    private val adapterCropPrices by lazy { Adapter_Crop_Prices() }
    private var createGroupList : ArrayList<String> = ArrayList()
    private var croppricelist : ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_home,container,false)







        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bindUIViews()
        getGovScheme()
        binding.expIncometracker.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_expensess_Income_tracker,null)
        })

        binding.crop.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_crops_Fragment,null)
        })


        binding.myfarms.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.myFarm_Fragment,null)
        })


        binding.loansubsidies.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_loan_Subsidies_Fragment,null)
        })


        binding.farmstates.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_fragment_Farm_States,null)
        })

        binding.tvallnewsannouncement.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_news_Announcement_all_Fragment,null)
        })

    }

    private fun getGovScheme() {
        val call: Call<GetNewsAlert>
        call = Api_Controller().getInstacneAdmin().getNewsAlert()
        call.enqueue(object : Callback<GetNewsAlert> {
            override fun onResponse(call: Call<GetNewsAlert>, response: Response<GetNewsAlert>) {
                val responseList=response.body()?.news


                binding.rvnewsannouncment.adapter = adapterNewsAnnouncements
                if (responseList != null) {
                    adapterNewsAnnouncements.setList(responseList,requireContext())
                }
            }

            override fun onFailure(call: Call<GetNewsAlert>, t: Throwable) {
                Log.d("getNewsError", t.toString())
            }
        })
    }



}