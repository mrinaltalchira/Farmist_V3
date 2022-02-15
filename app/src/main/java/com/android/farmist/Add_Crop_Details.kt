package com.android.farmist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.farmist.databinding.ActivityAddCropDetailsBinding


class Add_Crop_Details : AppCompatActivity() {

    lateinit var binding: ActivityAddCropDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddCropDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)






    }
}