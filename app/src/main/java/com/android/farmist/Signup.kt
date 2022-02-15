package com.android.farmist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.farmist.databinding.ActivitySignupBinding
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        binding.btnsignupnext.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, Signup_Second::class.java)
            var userName = binding.etName.text.toString()
            var phoneNumber = binding.etMobilenumber.text

            if (userName.isBlank() || phoneNumber.isBlank() || phoneNumber.length != 10) {
                if (userName.isBlank()) {
                    binding.etName.setError("Field cannot be empty")


                } else if (phoneNumber.length != 10 || phoneNumber.isBlank()) {
                    binding.etMobilenumber.setError("Please Enter valid number")
                }
                Toast.makeText(this, "Please Enter Valid Field", Toast.LENGTH_SHORT).show()
                return@OnClickListener

            } else {

                intent.putExtra("Username", userName.toString())
                intent.putExtra("phoneNumber", phoneNumber.toString())
                startActivity(intent)
            }

        })
    }
}