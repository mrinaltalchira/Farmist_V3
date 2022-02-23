package com.android.farmist.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_farm_States
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FarmStatusBinding
import com.android.farmist.model.farmstats.StatsData
import kotlinx.android.synthetic.main.farm_status.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_Farm_States : Fragment() {
lateinit var userId:String
    private lateinit var binding: FarmStatusBinding
    private val adapterFarmStates by lazy { Adapter_farm_States() }
    private var createGroupList: ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.farm_status, container, false)

        val preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()

        return binding.root


    }


    fun setData() {

        var call: Call<StatsData> = Api_Controller().getInstacne().getFarmstats(userId)
        call.enqueue(object:Callback<StatsData>{
            override fun onResponse(call: Call<StatsData>, response: Response<StatsData>) {
                var respo = response.body()
                if (respo != null){

                    incometotal.setText("Rs. "+respo.incomeTotal)
                    quantitytotal.setText(respo.quantityTotal)
                    numofland.setText(respo.numOfLand)
                    numofcrops.setText(respo.numOfCrops)
                    varities.setText(respo.varities)
                    numofharvest.setText(respo.numOfHarvested)
                    expenseTotal.setText(respo.expenseTotal)
                    areaSowed.setText(respo.areaSowed +" "+ respo.areaType[0])

                }

            }
            override fun onFailure(call: Call<StatsData>, t: Throwable) {
                Toast.makeText(requireActivity(), "$t", Toast.LENGTH_SHORT).show()
            }
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()

        setData()
    }


}

