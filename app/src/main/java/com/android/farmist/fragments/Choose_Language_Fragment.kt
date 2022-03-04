package com.android.farmist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.databinding.*
import android.content.Intent
import java.util.Locale
import android.app.Activity
import android.content.Context
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics
import com.android.farmist.SplashActivity


class Choose_Language_Fragment : Fragment() {

    private lateinit var binding: ChooseLanguageBinding
    lateinit var radio: RadioButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.choose_language, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setradioBtn()
        radio = RadioButton(requireActivity())

//        setLocale("hi")
        binding.RgSelectLang.setOnCheckedChangeListener { radioGroup, i ->
            radio = view.findViewById(i)
            when (radio) {
                binding.RbHindi -> binding.btnSelectLang.setText("जारी रखें")
                binding.RbKannada -> binding.btnSelectLang.setText("ಮುಂದುವರಿಸಿ")
                binding.RbTelugu -> binding.btnSelectLang.setText("కొనసాగుతుంది")
                binding.RbEnglish -> binding.btnSelectLang.setText("Continue")

                else -> Toast.makeText(
                    context?.applicationContext,
                    "select language",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnSelectLang.setOnClickListener {
            when (radio) {
                binding.RbEnglish -> {
                    val locale = Locale("en")
                    Locale.setDefault(locale)
                    val config = Configuration()
                    config.locale = locale
                    requireActivity().getResources().updateConfiguration(
                        config,
                        requireActivity().getResources().getDisplayMetrics()
                    )
                    setLanguage("en")

                    Toast.makeText(requireActivity(), "Locale in English !", Toast.LENGTH_LONG)
                        .show()
                    val intet=Intent(activity,SplashActivity::class.java)
                    activity?.startActivity(intet)
                }
                binding.RbHindi -> {
                    val locale2 = Locale("hi")
                    Locale.setDefault(locale2)
                    val config2 = Configuration()
                    config2.locale = locale2

                    requireActivity().getResources().updateConfiguration(
                        config2,
                        requireActivity().getResources().getDisplayMetrics()
                    )
                    setLanguage("hi")
                    Toast.makeText(requireActivity(), "Locale in hindi !", Toast.LENGTH_LONG).show()
                    val intet=Intent(activity,SplashActivity::class.java)
                    activity?.startActivity(intet)
                }
                binding.RbTelugu -> {
                    val locale3 = Locale("te")
                    Locale.setDefault(locale3)
                    val config3 = Configuration()
                    config3.locale = locale3
                    requireActivity().getResources().updateConfiguration(
                        config3,
                        requireActivity().getResources().getDisplayMetrics()
                    )
                    setLanguage("te")
                    Toast.makeText(requireActivity(), "Locale in Telugu !", Toast.LENGTH_LONG).show()
                    val intet=Intent(activity,SplashActivity::class.java)
                    activity?.startActivity(intet)
                }
                binding.RbKannada -> {
                    val locale3 = Locale("kn")
                    Locale.setDefault(locale3)
                    val config3 = Configuration()
                    config3.locale = locale3
                    requireActivity().getResources().updateConfiguration(
                        config3,
                        requireActivity().getResources().getDisplayMetrics()
                    )
                    setLanguage("kn")
                    Toast.makeText(requireActivity(), "Locale in Kannada !", Toast.LENGTH_LONG).show()
                    val intet=Intent(activity,SplashActivity::class.java)
                    activity?.startActivity(intet)
                }
            }

        }


    }

    private fun setradioBtn() {
        val sharedPreferences = context?.getSharedPreferences("lang", Context.MODE_PRIVATE)
        val message = sharedPreferences?.getString("selectLang", "")

        when(message)
        {
            "en"-> binding.RbEnglish.isChecked=true
            "hi"-> binding.RbHindi.isChecked=true
            "kn"-> binding.RbKannada.isChecked=true
            "te"-> binding.RbTelugu.isChecked=true
            else->binding.RbEnglish.isChecked=true

        }
    }


    fun setLanguage(lang: String?) {
        val sharedPreferences =
            activity?.getSharedPreferences("lang", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        if (editor != null) {
            editor.putString("selectLang", lang)
            editor.apply()
        }
    }


}