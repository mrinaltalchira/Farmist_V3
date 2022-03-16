package com.android.farmist.activity

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.farmist.OTPactivityLogin
import com.android.farmist.Otp_Activity
import com.android.farmist.R
import com.android.farmist.Signup
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.ActivityLoginBinding
import com.android.farmist.model.OTP.SignupOTPRespo
import com.android.farmist.model.signUp.signUpModel
import com.android.farmist.util.SmsBroadcastReciver
import com.android.farmist.util.SweetAlert
import com.google.android.gms.auth.api.phone.SmsRetriever
import kotlinx.android.synthetic.main.activity_otp.*
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    var token:String = ""
    lateinit var userIdStr:String
    lateinit var message:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val sharedPreferences = getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        val message = sharedPreferences.getString("check", "")

        if (message == "success") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnloginnext.setOnClickListener(View.OnClickListener {
          var numstr=  binding.etMobilenumber.text
            if (numstr.length!=10)
            {
                binding.etMobilenumber.setError("Please Enter valid Number")
                return@OnClickListener
            }
            else {
                loginUser()
            }

        })

        binding.tvsignup.setOnClickListener(View.OnClickListener {
            val intent = Intent(this , Signup::class.java)
            startActivity(intent)
        })
    }

    private fun loginUser() {
        SweetAlert.showDialog(this)
        val numberUser=binding.etMobilenumber.text.toString()

        var call: retrofit2.Call<signUpModel>
        call=Api_Controller().getInstacne().loginUser(numberUser)
        call.enqueue(object:Callback<signUpModel>{
            override fun onResponse(
                call: retrofit2.Call<signUpModel>,
                response: Response<signUpModel>
            ) {
                val loginData=response.body()
                if (response.isSuccessful)
                {

                    val idStr: String = loginData?.user?._id.toString()
                    userIdStr=idStr


//                        val sharedPreferences =
//                            getSharedPreferences("userMassage", Context.MODE_PRIVATE)
//                        val editor = sharedPreferences.edit()
//
//                        editor.putString("message", idStr)
                    if (loginData != null) {
//                        editor.putString("check", loginData.message.toString())
                        message= loginData.message
                    }
//                        editor.apply()

                    sendOtp()
//                    val intent = Intent(this@LoginActivity, OTPactivityLogin::class.java)
//                    intent.putExtra("phoneNumber",numberUser)
                    SweetAlert.hidediloag()
//                    startActivity(intent)
//                    finish()
//                    Toast.makeText(this@LoginActivity , "Login sucessful", Toast.LENGTH_SHORT).show()
                }
                else{
                    SweetAlert.hidediloag()
                    SweetAlert.failedShowDialog(this@LoginActivity,"Login Failed! Please check number")
                    Toast.makeText(this@LoginActivity , "Login Failed", Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: retrofit2.Call<signUpModel>, t: Throwable) {
                SweetAlert.hidediloag()
                Log.d("loginError", t.toString())

            }
        })


    }

    private fun sendOtp() {
        if (userIdStr==null)
        {
            return
        }
       var  phoneNumber=binding.etMobilenumber.text.toString()
        var apiKey = "01ab6561-9f6e-11ec-a4c2-0200cd936042"
        var call: retrofit2.Call<SignupOTPRespo> = Api_Controller.OtpInterFace.GetOTP(apiKey,phoneNumber)
        call.enqueue(object:Callback<SignupOTPRespo>{
            override fun onResponse(
                call: retrofit2.Call<SignupOTPRespo>,
                response: Response<SignupOTPRespo>
            ) {
                token = response.body()?.Details.toString()
                val intent = Intent(this@LoginActivity, OTPactivityLogin::class.java)
                intent.putExtra("token",token)
                intent.putExtra("phoneNumber", phoneNumber)
                intent.putExtra("userId", userIdStr)
                intent.putExtra("message",message )
                startActivity(intent)
                finish()
            }
            override fun onFailure(call: retrofit2.Call<SignupOTPRespo>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Please enter correct number", Toast.LENGTH_SHORT).show()


            }
        })
    }

}