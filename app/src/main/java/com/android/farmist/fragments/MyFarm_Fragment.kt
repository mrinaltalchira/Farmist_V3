package com.android.farmist.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.farmist.R
import com.android.farmist.adapter.MyFarmsAdapter
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentMyFarmBinding
import com.android.farmist.model.adapterGetFarm.AdeptDataResponce
import com.android.farmist.util.progressbars
import kotlinx.android.synthetic.main.fragment_my_farm_.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyFarm_Fragment : Fragment() {

    private lateinit var binding: FragmentMyFarmBinding
    lateinit var userId: String
    lateinit var adap: MyFarmsAdapter
    lateinit var progress: progressbars

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_farm_, container, false)
        progress = progressbars(requireActivity())


        getFarmss()

        binding.refresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {

                binding.refresh.isRefreshing = false
            }, 3000)
            try {
                getFarmssagain()
            }catch (e:Exception){Log.d("","")}

        }


binding.backText.setOnClickListener {
    findNavController().navigate(R.id.action_myFarm_Fragment_to_nav_home2)

}


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.tvAddNewFarm.setOnClickListener {
            findNavController().navigate(R.id.action_myFarm_Fragment_to_addFarmDetails, null)
        }

        super.onViewCreated(view, savedInstanceState)

    }

    private fun getFarmss() {
        progress.showDialog()
        val preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()

        var call: Call<AdeptDataResponce> = Api_Controller().getInstacne().getFarmsForAdaper(userId)
        call.enqueue(object : Callback<AdeptDataResponce> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<AdeptDataResponce>,
                response: Response<AdeptDataResponce>
            ) {
                var respo = response.body()
                if (respo != null) {
                    adap = MyFarmsAdapter(context!!, respo.farms)
                    Log.d("dataFarm", userId)

                    recyclerview_myfarm.adapter = adap
                    recyclerview_myfarm.layoutManager = LinearLayoutManager(requireActivity())
                    adap.notifyDataSetChanged()
                    progress.hidediloag()
                }

            }

            override fun onFailure(call: Call<AdeptDataResponce>, t: Throwable) {
                Toast.makeText(activity?.applicationContext!!, "$t", Toast.LENGTH_SHORT).show()
                progress.hidediloag()
            }
        })

    }


    private fun getFarmssagain() {

        val preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()

        var call: Call<AdeptDataResponce> = Api_Controller().getInstacne().getFarmsForAdaper(userId)
        call.enqueue(object : Callback<AdeptDataResponce> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<AdeptDataResponce>,
                response: Response<AdeptDataResponce>
            ) {
                var respo = response.body()
                if (respo != null) {
                    adap = MyFarmsAdapter(context!!, respo.farms)
                    Log.d("dataFarm", userId)
                    recyclerview_myfarm.adapter = adap
                    recyclerview_myfarm.layoutManager = LinearLayoutManager(requireActivity())
                    adap.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<AdeptDataResponce>, t: Throwable) {
                Toast.makeText(activity?.applicationContext!!, "$t", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
