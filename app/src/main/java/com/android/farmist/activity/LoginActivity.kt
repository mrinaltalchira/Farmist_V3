package com.android.farmist.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.farmist.Otp_Activity
import com.android.farmist.R
import com.android.farmist.Signup
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.ActivityLoginBinding
import com.android.farmist.model.signUp.signUpModel
import com.android.farmist.util.SweetAlert
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding


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
            loginUser()

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

                        val sharedPreferences =
                            getSharedPreferences("userMassage", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()

                        editor.putString("message", idStr)
                    if (loginData != null) {
                        editor.putString("check", loginData.message.toString())
                    }
                        editor.apply()


                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    SweetAlert.hidediloag()
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@LoginActivity , "Login sucessful", Toast.LENGTH_SHORT).show()
                }
                else{
                    SweetAlert.hidediloag()
                    Toast.makeText(this@LoginActivity , "Login Failed", Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: retrofit2.Call<signUpModel>, t: Throwable) {
                SweetAlert.hidediloag()
                Log.d("loginError", t.toString())

            }
        })


    }
}