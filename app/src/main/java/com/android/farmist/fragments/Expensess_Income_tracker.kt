package com.android.farmist.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_Alerts_News
import com.android.farmist.adapter.Adapter_Exp_Income_Tracker
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.ExpenseTrackerBinding
import com.android.farmist.databinding.FragmentExpensessIncomeTrackerBinding
import com.android.farmist.databinding.FragmentHomeBinding
import com.android.farmist.model.ExpensesIncomeTrackerResponse.Data
import com.android.farmist.model.ExpensesIncomeTrackerResponse.GetExpensesIncomeTracker
import com.android.farmist.model.alertsResponse.New
import com.android.farmist.util.progressbars
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import android.view.MotionEvent

import android.view.View.OnTouchListener


class Expensess_Income_tracker : Fragment() {


    private lateinit var binding: FragmentExpensessIncomeTrackerBinding

    //    private val adapterexpIncomeTracker by lazy { Adapter_Exp_Income_Tracker() }
    private var createGroupList: ArrayList<String> = ArrayList()
    lateinit var adapterExpIncomeTracker: Adapter_Exp_Income_Tracker
    lateinit var preferences: SharedPreferences
    lateinit var userId: String
    lateinit var progressbarsDialog: progressbars


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_expensess__income_tracker,
            container,
            false
        )
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()
        progressbarsDialog = progressbars(requireActivity())
        progressbarsDialog.showDialog()

        GetExpensesincomeTracker()
        binding.tvArchived.setOnClickListener {
            Toast.makeText(requireActivity(), "archivie", Toast.LENGTH_SHORT).show()
            findNavController().navigate(
                R.id.action_expensess_Income_tracker_to_archived_crop,
                null
            )

        }
//        binding.back.setOnTouchListener(object : L(binding.back) {
//            fun onDrawableClick(): Boolean {
//
//                return true
//            }
//        })
//        bindUIViews()
//        binding.back.setOnTouchListener(OnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_UP) {
//                if (event.rawX <= binding.back.paddingLeft) {
//                    Toast.makeText(requireContext(), "hello", Toast.LENGTH_SHORT).show()
//                    // your action for drawable click event
//                    return@OnTouchListener true
//                }
//            }
//            false
//        })

    }


    private fun GetExpensesincomeTracker() {
        adapterExpIncomeTracker =
            activity?.let { Adapter_Exp_Income_Tracker(it.applicationContext, ArrayList<Data>()) }!!
        binding.rvExpIncometracker.setHasFixedSize(true)
        binding.rvExpIncometracker.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.rvExpIncometracker.adapter = adapterExpIncomeTracker

        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(getObservable().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response -> getObserver(response as GetExpensesIncomeTracker) },
                { t -> onFailure(t) }
            ))


    }

    private fun getObservable(): Observable<GetExpensesIncomeTracker> {
        return Api_Controller.apiInterface2.getExpensesIncomeTracker(userId)
    }

    private fun getObserver(getExpensesIncomeTracker: GetExpensesIncomeTracker) {
        if (getExpensesIncomeTracker != null) {
            val getExpensesIncomeTrackerList = getExpensesIncomeTracker.data


            progressbarsDialog.hidediloag()
            adapterExpIncomeTracker.setList(getExpensesIncomeTrackerList)
        }

    }

    private fun onFailure(t: Throwable) {
        progressbarsDialog.hidediloag()
        Log.d("Main", "OnFailure: " + t.message)
    }


}