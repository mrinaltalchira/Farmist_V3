package com.android.farmist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.ActivitySignupBinding
import com.android.farmist.model.OTP.SignupOTPRespo
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    var token:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        binding.btnsignupnext.setOnClickListener(View.OnClickListener {


            var userName = binding.etName.text.toString()
            var phoneNumber = binding.etMobilenumber.text.toString()

            if (userName.isBlank() || phoneNumber.isBlank() || phoneNumber.length != 10) {
                if (userName.isBlank()) {
                    binding.etName.setError("Field cannot be empty")


                } else if (phoneNumber.length != 10 || phoneNumber.isBlank()) {
                    binding.etMobilenumber.setError("Please Enter valid number")
                }
                Toast.makeText(this, "Please Enter Valid Field", Toast.LENGTH_SHORT).show()
                return@OnClickListener

            } else {

                var apiKey = "01ab6561-9f6e-11ec-a4c2-0200cd936042"
                var call: Call<SignupOTPRespo> = Api_Controller.OtpInterFace.GetOTP(apiKey,phoneNumber)
                call.enqueue(object:Callback<SignupOTPRespo>{
                    override fun onResponse(
                        call: Call<SignupOTPRespo>,
                        response: Response<SignupOTPRespo>
                    ) {
                    token = response.body()?.Details.toString()
                val intent = Intent(this@Signup, Otp_Activity::class.java)
                intent.putExtra("token",token.toString())
                intent.putExtra("Username", userName.toString())
                intent.putExtra("phoneNumber", phoneNumber.toString())
                startActivity(intent)
                finish()
                    }
                    override fun onFailure(call: Call<SignupOTPRespo>, t: Throwable) {
                        Toast.makeText(this@Signup, "Please enter correct number", Toast.LENGTH_SHORT).show()


                    }
                })
            }

        })
    }
}