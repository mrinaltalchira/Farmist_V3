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
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.AddSubsidyBinding
import com.android.farmist.databinding.ExpenseTrackerBinding
import com.android.farmist.databinding.FragmentAccountBinding
import com.android.farmist.model.ExpensesIncomeTrackerResponse.AddExpesesResponse
import com.android.farmist.model.ExpensesIncomeTrackerResponse.addSubsidy
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*


class Add_Subsidy_Fragment : Fragment() {

    private lateinit var binding: AddSubsidyBinding
    var onDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var date: String = ""
    var cropId: String = ""
    lateinit var preferences: SharedPreferences
    var userId: String = ""
    lateinit var radioValue: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.add_subsidy, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()

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
        binding.btnAddSubsidy.setOnClickListener {
            cropId = arguments?.getString("cropId").toString()
            if (binding.edtsubsidy.toString().isBlank() || binding.edtAmount.toString()
                    .isBlank() || date.isBlank()
            ) {
                Toast.makeText(
                    activity?.applicationContext,
                    "Please Enter valid Field",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                setAddSubsidy()

            }


        }


    }

    private fun setAddSubsidy() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> getObserver(response as addSubsidy) },
                    { t -> onFailure(t) })
        )
    }

    private fun getObservable(): Observable<addSubsidy> {
        return Api_Controller.apiInterface2.addsubsidy(
            userId,
            cropId,
            binding.edtsubsidy.text.toString(),
            date,
            binding.edtAmount.text.toString()
        )
    }

    private fun getObserver(response: addSubsidy) {
        if (response != null) {
            val responseMessage = response.message
            Toast.makeText(requireActivity(), "$cropId", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_add_Subsidy_Fragment_to_expensess_Income_tracker)


        }
//        Toast.makeText(activity?.applicationContext, "data${priceData.toString()}", Toast.LENGTH_SHORT).show()


    }

    private fun onFailure(t: Throwable) {
        Log.d("Main", "OnFailure: " + t.message)
    }


}