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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.view.doOnAttach
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_Select_category
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.AddExpensesBinding
import com.android.farmist.databinding.AddIncomeBinding
import com.android.farmist.databinding.ExpenseTrackerBinding
import com.android.farmist.databinding.FragmentAccountBinding
import com.android.farmist.model.ExpensesIncomeTrackerResponse.AddExpesesResponse
import com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpenesData
import com.android.farmist.model.selectCategoryResponse.Fruitlist
import com.android.farmist.model.selectCategoryResponse.GetFruitsList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*


class Add_Expenses_Fragment : Fragment() {

    private lateinit var binding: AddExpensesBinding
    var onDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var date: String = ""
    lateinit var preferences: SharedPreferences
    var userId: String = ""
    var cropId: String = ""
    lateinit var strExpenestype: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.add_expenses, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cropId = arguments?.getString("cropId").toString()

        preferences = requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()
        getExpenses()



        val expensesType = ArrayList<String>()
        expensesType.add("Seeds")
        expensesType.add("Fertilizer")
        expensesType.add("Labour")
        expensesType.add("Tractor")
        expensesType.add("Other")

        binding.expenseTypeSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            expensesType
        )

        binding.expenseTypeSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                strExpenestype = expensesType[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
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
        binding.btnAddExpenses.setOnClickListener {

            if (binding.edtExpesesAmount.toString()
                    .isBlank() || date.isBlank()
            ) {
                Toast.makeText(
                    activity?.applicationContext,
                    "Please Enter valid Field",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                setExpenses()


            }

        }

        //1. implement


    }

    private fun setExpenses() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> getObserver(response as AddExpesesResponse) },
                    { t -> onFailure(t) })
        )
    }


    private fun getObservable(): Observable<AddExpesesResponse> {
        return Api_Controller.apiInterface2.addExpenses(
            userId,
            cropId,
            strExpenestype,
            date,
            binding.edtExpesesAmount.text.toString()
        )
    }

    private fun getObserver(response: AddExpesesResponse) {
        if (response != null) {
            val responseMessage = response.message
            Toast.makeText(requireActivity(), "$responseMessage", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_add_Expenses_Fragment_to_expensess_Income_tracker)


        }
//        Toast.makeText(activity?.applicationContext, "data${priceData.toString()}", Toast.LENGTH_SHORT).show()


    }

    private fun onFailure(t: Throwable) {
        Log.d("Main", "OnFailure: " + t.message)
    }



    private fun getExpenses() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            getObservableExpenses().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> getObserverExpenes(response as ExpenesData) },
                    { t -> onFailureExpenes(t) })
        )
    }


    private fun getObservableExpenses(): Observable<ExpenesData> {
        return Api_Controller.apiInterface2.getExpenseData(
            cropId
        )
    }

    private fun getObserverExpenes(response: ExpenesData) {
        if (response != null) {
            val responseMessage = response.data
            binding.tvDateSowedExpense.setText(responseMessage.date)
            binding.tvExpenseAddExpenses.setText(responseMessage.expense)
            binding.tvFertilizerExpense.setText(responseMessage.totalFertilzer)
            binding.tvSeedsExpenses.setText(responseMessage.totalSeed)
            binding.tvLaborExpenses.setText(responseMessage.totalLabour)
            binding.tvTractorExpenes.setText(responseMessage.totalTractor)


        }


    }

    private fun onFailureExpenes(t: Throwable) {
        Log.d("Main", "OnFailure: " + t.message)
    }


}