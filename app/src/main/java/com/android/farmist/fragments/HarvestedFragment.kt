package com.android.farmist.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_Harvested_Crop
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentHarvestedBinding
import com.android.farmist.model.harvested.Data
import com.android.farmist.model.harvested.GetHarvestedCrop
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HarvestedFragment : Fragment() {
    private lateinit var binding : FragmentHarvestedBinding
    private val adapterHarvestedCrop by lazy { Adapter_Harvested_Crop(requireActivity()) }
    private var createGroupList : ArrayList<String> = ArrayList()

    var data : ArrayList<Data> = arrayListOf()
lateinit var userId:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_harvested,container,false)

       var preferences = requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()


        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUIViews()

    }


    private fun bindUIViews(){

var call:Call<GetHarvestedCrop> = Api_Controller().getInstacne().getHarvested(userId)
        call.enqueue(object : Callback<GetHarvestedCrop>{
            override fun onResponse(
                call: Call<GetHarvestedCrop>,
                response: Response<GetHarvestedCrop>
            ) {
                var respond = response.body()
                if (respond != null){

                    data = respond.data
                    Toast.makeText(requireActivity(), "$data", Toast.LENGTH_SHORT).show()
                    adapterHarvestedCrop.setList(data)
                    binding.rvharvest.adapter = adapterHarvestedCrop
                }
            }

            override fun onFailure(call: Call<GetHarvestedCrop>, t: Throwable) {
                Toast.makeText(requireActivity(), "$t", Toast.LENGTH_SHORT).show()
            }
        })



    }



}