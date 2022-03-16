package com.android.farmist

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.farmist.activity.MainActivity
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.ActivityLoginBinding
import com.android.farmist.databinding.ActivityOtpBinding
import com.android.farmist.databinding.ActivityOtploginBinding
import com.android.farmist.model.OTP.CheckOTPRespo
import com.android.farmist.util.SmsBroadcastReciver
import com.android.farmist.util.SweetAlert
import com.google.android.gms.auth.api.phone.SmsRetriever
import kotlinx.android.synthetic.main.activity_otp.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class OTPactivityLogin : AppCompatActivity() {
    private lateinit var binding: ActivityOtploginBinding

    private var smsBroadcastReciver: SmsBroadcastReciver? = null
    private val REQ_USER = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otplogin)

        var strphone: String? = intent.getStringExtra("phoneNumber")
        var strMessage: String? = intent.getStringExtra("message")
        var userId: String? = intent.getStringExtra("userId")
        Toast.makeText(this, "number:$userId", Toast.LENGTH_SHORT).show()
        startSmartUserConsent()

        binding.btnOtpLogin.setOnClickListener {
            SweetAlert.showDialog(this)

            var otp =
                et_Ot1.text.toString() + et_Ot2.text.toString() + et_Ot3.text.toString() + et_Ot4.text.toString() + et_Ot5.text.toString() + et_Ot6.text.toString()
            var apiKey = "01ab6561-9f6e-11ec-a4c2-0200cd936042"
            var sessionKey: String? = intent.getStringExtra("token")


            if (otp.length == 6) {
                var call = Api_Controller.OtpInterFace.checkOTP(apiKey, sessionKey.toString(), otp)
                call.enqueue(object : Callback<CheckOTPRespo> {
                    override fun onResponse(
                        call: Call<CheckOTPRespo>,
                        response: Response<CheckOTPRespo>
                    ) {
//                        Toast.makeText(this@Otp_Activity, "${response.body()}", Toast.LENGTH_SHORT)
//                            .show()
                        if (response.isSuccessful && response.body()?.Details.toString() == "OTP Matched") {
                            val sharedPreferences =
                            getSharedPreferences("userMassage", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("message", userId)
                        editor.putString("check", strMessage)
                        editor.apply()
                            SweetAlert.hidediloag()
                            var intent = Intent(this@OTPactivityLogin, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@OTPactivityLogin,
                                "OTP verification failed",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<CheckOTPRespo>, t: Throwable) {
                        Toast.makeText(this@OTPactivityLogin, "OTP Missmatch", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }

        }

    }

    private fun startSmartUserConsent() {
        val clint = SmsRetriever.getClient(this)
        clint.startSmsUserConsent(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQ_USER) {

            if (resultCode == RESULT_OK && data != null) {
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                getOtpFromMessage(message)
            }
        }

    }

    private fun getOtpFromMessage(message: String?) {
        val otpPatter = Pattern.compile("(|^)\\d{6}")
        val matcher = otpPatter.matcher(message)
        var code = ""
        while (matcher.find()) {
            code = matcher.group(0)
        }
        et_Ot1.setText(code[0].toString())
        et_Ot2.setText(code[1].toString())
        et_Ot3.setText(code[2].toString())
        et_Ot4.setText(code[3].toString())
        et_Ot5.setText(code[4].toString())
        et_Ot6.setText(code[5].toString())

        if (code.length == 6) {
            binding.btnOtpLogin.performClick()
        }

        Log.d("otp3", code)
    }
    fun registerBroadcastReciver() {
        smsBroadcastReciver = SmsBroadcastReciver()
        smsBroadcastReciver!!.smsBroadcastReciverListner =
            object : SmsBroadcastReciver.SmsBroadcastReciverListner {
                override fun onSuccedd(intent: Intent?) {
                    startActivityForResult(intent, REQ_USER)
                }

                override fun onFailure() {

                }


            }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReciver, intentFilter)
    }

    override fun onStart() {
        super.onStart()
        registerBroadcastReciver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsBroadcastReciver)
    }
}