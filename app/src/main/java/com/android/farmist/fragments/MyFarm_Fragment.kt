package com.android.farmist.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentMyFarmBinding
import com.android.farmist.model.getFarm.GetFarmForSpinner
import com.android.farmist.model.getFarms
import com.android.farmist.model.signUp.signUpModel
import kotlinx.android.synthetic.main.activity_add_crop_details.*
import kotlinx.android.synthetic.main.adaapter_exp_income_tracker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyFarm_Fragment : Fragment() {

    private lateinit var binding:FragmentMyFarmBinding
    lateinit var message:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_my_farm_,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.tvAddNewFarm.setOnClickListener {
                findNavController().navigate(R.id.action_myFarm_Fragment_to_addFarmDetails,null)
        }

        super.onViewCreated(view, savedInstanceState)

        getFarmss()
    }

    private fun getFarmss() {
        val preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        message = preferences.getString("message", "").toString()
        Toast.makeText(requireContext(), "id:${message.toString()}", Toast.LENGTH_SHORT).show()

        val call: Call<getFarms>
        call = Api_Controller().getInstacne().getFarm(message)
        call.enqueue(object :Callback<getFarms>{
            override fun onResponse(call: Call<getFarms>, response: Response<getFarms>) {
                val responseStr=response.body()
                Toast.makeText(requireContext(), "data${responseStr.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<getFarms>, t: Throwable) {

                Log.d("ErrorFarm",t.toString())
            }
        })
    }

}