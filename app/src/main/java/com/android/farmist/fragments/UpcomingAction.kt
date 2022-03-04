package com.android.farmist.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.farmist.R
import com.android.farmist.adapter.AdapterUpcommingAction
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentUpcomingActionBinding
import com.android.farmist.model.upcommingAction.Upcomingrespo
import kotlinx.android.synthetic.main.fragment_upcoming_action.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import java.util.*
import kotlin.collections.ArrayList
import com.applandeo.materialcalendarview.EventDay

import com.applandeo.materialcalendarview.listeners.OnDayLongClickListener





class UpcomingAction : Fragment() {

    lateinit var binding: FragmentUpcomingActionBinding
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_upcoming_action,
            container,
            false
        )


        var preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()
        getItem()


        



        return binding.root
    }

    fun getItem() {

        var call: Call<Upcomingrespo> = Api_Controller().getInstacne().UpcomingFun(userId)
        call.enqueue(object : Callback<Upcomingrespo> {
            override fun onResponse(call: Call<Upcomingrespo>, response: Response<Upcomingrespo>) {

                var respo = response.body()
                if (respo != null) {
                    Log.d("userId", userId)
                    var adap = AdapterUpcommingAction(requireActivity(), respo.cropData)
                    binding.upcomingActionRecyclerView.adapter = adap
                    adap.notifyDataSetChanged()
                    binding.upcomingActionRecyclerView.layoutManager =
                        LinearLayoutManager(requireActivity())


                }
            }

            override fun onFailure(call: Call<Upcomingrespo>, t: Throwable) {
                Log.d("", "")
            }
        })
    }

}