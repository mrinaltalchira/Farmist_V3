package com.android.farmist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.farmist.activity.MainActivity
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.ActivitySignupSecondBinding
import com.android.farmist.model.signUp.signUpModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signup_Second : AppCompatActivity() {
    private lateinit var binding: ActivitySignupSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_second)
        binding.btnsignupfinal.setOnClickListener(View.OnClickListener {
            UserSignUp()
        })
    }

    fun UserSignUp() {
        var strUser: String? = intent.getStringExtra("Username")
        var strphone: String? = intent.getStringExtra("phoneNumber")
        var StrPincode: String = binding.etName.text.toString()
        var StrVillage: String = binding.etMobilenumber.text.toString()
        var StrTehsil: String = binding.etTehsil.text.toString()
        var StrDistrict: String = binding.etDistrict.text.toString()
        var StrAcres: String = binding.etAcers.text.toString()
        var StrSeedBrand: String = binding.etBrand.text.toString()
        var StrChemical: String = binding.etCompny.text.toString()

        if (strUser!!.isBlank() || strphone!!.isBlank() || StrPincode.isBlank() || StrVillage.isBlank() || StrTehsil.isBlank() || StrDistrict.isBlank() || StrAcres.isBlank() || StrSeedBrand.isBlank() || StrChemical.isBlank()) {
            Toast.makeText(this, "Field Cannot be empty", Toast.LENGTH_SHORT).show()
            return
        } else {
            var call: Call<signUpModel>
            call = Api_Controller().getInstacne().UserRegister(
                strUser!!,
                strphone!!,
                StrPincode,
                StrVillage,
                StrTehsil,
                StrDistrict,
                StrAcres,
                StrSeedBrand,
                StrChemical
            )
            call.enqueue(object : Callback<signUpModel> {
                override fun onResponse(
                    call: Call<signUpModel>,
                    response: Response<signUpModel>
                ) {
                    var responseRequest: signUpModel? = response.body()
                    Toast.makeText(
                        this@Signup_Second,
                        "response:" + responseRequest.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                    if (responseRequest != null) {
                        val idStr: String = responseRequest.user._id.toString()
                        if (responseRequest.message == "success") {
                            Log.d("userData", responseRequest.toString())
                            val sharedPreferences =
                                getSharedPreferences("userMassage", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            Toast.makeText(this@Signup_Second, "id:$idStr", Toast.LENGTH_SHORT)
                                .show()
                            editor.putString("message", idStr)
                            editor.putString("check", responseRequest.message.toString())
                            editor.apply()
                            val intent = Intent(this@Signup_Second, MainActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(
                            this@Signup_Second,
                            "User number already exist",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<signUpModel>, t: Throwable) {
                    Log.d("result", t.toString())
                }
            })

        }


    }
}