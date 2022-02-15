package com.android.farmist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.android.farmist.activity.MainActivity
import com.android.farmist.databinding.ActivityOtpBinding

class Otp_Activity : AppCompatActivity() {

    private lateinit var binding : ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otp)

        binding.btnotplogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        })

    }
}