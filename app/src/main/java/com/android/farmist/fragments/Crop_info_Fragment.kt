package com.android.farmist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.databinding.FragmentCropInfoBinding


class Crop_info_Fragment : Fragment() {

lateinit var binding: FragmentCropInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_crop_info_,container,false)

         binding.backbtn.setOnClickListener {
             findNavController().navigate(R.id.action_crop_info_Fragment_to_crops_Fragment)
         }

    return  binding.root
    }


}