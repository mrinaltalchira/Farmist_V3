package com.android.farmist.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.farmist.R
import com.android.farmist.activity.MainActivity
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.PersonalInformationBinding
import com.android.farmist.model.getUserInfo.getUserModel
import com.android.farmist.model.signUp.signUpModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.farmist.util.progressbars


class Personal_Information_Fragment : Fragment() {

    private lateinit var binding: PersonalInformationBinding

    //    private lateinit var getUserModelObj:getUserModel
    lateinit var message: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.personal_information, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserData()
        binding.saveBtn.setOnClickListener {

            updateUserData()
        }


    }

    private fun updateUserData() {

        var progressBars=progressbars(requireContext())
        progressBars.showDialog()


        val StrName = binding.userName.text.toString()
        val Strphone = binding.userPhone.text.toString()
        val Strpincode = binding.userPincode.text.toString()
        val StrVillage = binding.userVillage.text.toString()
        val StrTehsil = binding.userTehsil.text.toString()
        val StrDistrict = binding.userDistrict.text.toString()
        val StrNoAcres = binding.userNoAcres.text.toString()

        val call: Call<signUpModel>
        call = Api_Controller().getInstacne().updateUserData(
            message,
            StrName,
            Strphone,
            Strpincode,
            StrVillage,
            StrTehsil,
            StrDistrict,
            StrNoAcres
        )

        call.enqueue(object : Callback<signUpModel> {
            override fun onResponse(call: Call<signUpModel>, response: Response<signUpModel>) {
                val updateData = response.body()
                if (response.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "update Successful:${updateData.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent= Intent(requireContext(), MainActivity::class.java)
                  progressBars.hidediloag()
                    startActivity(intent)
                    activity?.finish()
                }


            }

            override fun onFailure(call: Call<signUpModel>, t: Throwable) {
                Log.d("updateError", t.toString())

            }
        })
    }

    private fun getUserData() {


        val preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        message = preferences.getString("message", "").toString()
        Toast.makeText(requireContext(), "id:${message.toString()}", Toast.LENGTH_SHORT).show()


        val call: Call<getUserModel>
        call = Api_Controller().getInstacne().getUser(message.toString())
        call.enqueue(object : Callback<getUserModel> {
            override fun onResponse(call: Call<getUserModel>, response: Response<getUserModel>) {
                val test = response.body()
                if (response.isSuccessful) {

                    setUserdata(test)
                } else {
                    Toast.makeText(
                        activity?.applicationContext,
                        "Unresponse:${test.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<getUserModel>, t: Throwable) {
                Log.d("getUserDataError", t.toString())
            }
        })
    }

    private fun setUserdata(userdata: getUserModel?) {
        if (userdata != null) {
            binding.userName.setText(userdata.user.name.toString())
            binding.userPhone.setText(userdata.user.phone.toString())
            binding.userPincode.setText(userdata.user.pincode.toString())
            binding.userVillage.setText(userdata.user.village.toString())
            binding.userTehsil.setText(userdata.user.tehsil.toString())
            binding.userDistrict.setText(userdata.user.district.toString())
            binding.userNoAcres.setText(userdata.user.numOfAcers.toString())

        }


    }


}