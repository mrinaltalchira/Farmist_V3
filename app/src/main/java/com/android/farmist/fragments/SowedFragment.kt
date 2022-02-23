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
import com.android.farmist.adapter.Adapter_Sowed_Crop
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentSowedBinding
import com.android.farmist.model.getSowedCrop.GetSowedCrop
import com.android.farmist.util.progressbars
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SowedFragment : Fragment() {

    private lateinit var binding : FragmentSowedBinding

    //    private val adapterSowedCrop by lazy { Adapter_Sowed_Crop() }
    private var createGroupList : ArrayList<String> = ArrayList()
    lateinit var userId:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_sowed,container,false)
        return binding.root



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getcropData()

    }

    private fun getcropData() {
        val progressbars=progressbars(requireActivity())
        progressbars.showDialog()
        val preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()
        val call:Call<GetSowedCrop>
        call=Api_Controller().getInstacne().getSwoedCrop(userId)
        call.enqueue(object :Callback<GetSowedCrop>{
            override fun onResponse(call: Call<GetSowedCrop>, response: Response<GetSowedCrop>) {
                val cropList= response.body()?.userCrops

                val adpterSowedCrop= cropList?.let { Adapter_Sowed_Crop(requireActivity(), it) }
                progressbars.hidediloag()
                binding.rvsowedlist.adapter = adpterSowedCrop
                binding.rvsowedlist.layoutManager = LinearLayoutManager(requireActivity())
//                recyleviewOption.adapter = optionAdapter
//                recyleviewOption.setHasFixedSize(true)

            }

            override fun onFailure(call: Call<GetSowedCrop>, t: Throwable) {
                progressbars.hidediloag()
                Toast.makeText(requireActivity(), "${t.stackTrace}", Toast.LENGTH_SHORT).show()

            }
        })
    }





}