package com.android.farmist.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.AddIncomeBinding
import com.android.farmist.databinding.ExpenseTrackerBinding
import com.android.farmist.databinding.FragmentAccountBinding
import com.android.farmist.model.ExpensesIncomeTrackerResponse.addSubsidy
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.adaapter_exp_income_tracker.*
import java.util.*


class Add_Income_Fragment : Fragment() {

    private lateinit var binding: AddIncomeBinding
    var onDateSetListener: DatePickerDialog.OnDateSetListener? = null
    lateinit var date: String
    var radioValue: String="Kg"
    var num = 0
    lateinit var preferences: SharedPreferences
    lateinit var userId: String
    lateinit var cropId: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.add_income, container, false)

        binding.decremntImg.setOnClickListener {

            val strValue = binding.etCounterValueAddCrop.text.toString()
            if (!strValue.isBlank()) {
                num = strValue.toInt()
            }
            if (num >= 2) {

                num--
                binding.etCounterValueAddCrop.setText(num.toString())
            } else {
                Toast.makeText(requireContext(), "Area = 1", Toast.LENGTH_SHORT).show()
            }
        }
        binding.incremtImg.setOnClickListener {
            val strValue = binding.etCounterValueAddCrop.text.toString()
            if (!strValue.isBlank()) {
                num = strValue.toInt()

            }
            num++
            binding.etCounterValueAddCrop.setText(num.toString())
        }

        binding.rgRadiogroupAddCrop.setOnCheckedChangeListener { group, i ->

            var radioButton: RadioButton? = view?.findViewById(i)
            when (radioButton) {
                binding.rBAcersAddCrop -> radioValue = radioButton.text.toString()
                binding.rBBhigasAddCrop -> radioValue = radioButton.text.toString()
                binding.rBGuntasAddCrop -> radioValue = radioButton.text.toString()
            }
        }

        binding.etDateAddCrop.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            val datePickerDialog =
                DatePickerDialog(requireContext(), onDateSetListener, year, month, day)
            datePickerDialog.show()

        }
        onDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->

                val monthOfYear = month + 1
                date = "$dayOfMonth-$monthOfYear-$year"
                binding.etDateAddCrop.setText(date)
            }

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()



        binding.BtnaddIncome.setOnClickListener {
            val incomeQty:String=binding.edtPerQuntity.text.toString()

            cropId = arguments?.getString("cropId").toString()
            if (binding.etCounterValueAddCrop.toString().equals("0")||incomeQty.isBlank()||date.isBlank()||incomeQty.toInt()<=0||radioValue.isBlank())
            {
                Toast.makeText(
                    activity?.applicationContext,
                    "Please Enter valid Field",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            addincome()
        }


    }

    private fun addincome() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> getObserver(response as addSubsidy) },
                    { t -> onFailure(t) })
        )
    }

    private fun getObservable(): Observable<addSubsidy> {
        return Api_Controller.apiInterface2.addincome(
            userId,
            cropId,
            binding.etCounterValueAddCrop.text.toString(),
            date,
            binding.edtPerQuntity.text.toString(),
            radioValue
        )
    }

    private fun getObserver(response: addSubsidy) {
        if (response != null) {
            val responseMessage = response.message
            Toast.makeText(requireActivity(), "$responseMessage", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_add_Income_Fragment_to_expensess_Income_tracker)


        }



    }

    private fun onFailure(t: Throwable) {
        Log.d("Main", "OnFailure: " + t.message)
    }


}