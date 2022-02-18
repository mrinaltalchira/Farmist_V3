package com.android.farmist

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.farmist.adapter.Adapter_Archived_Crop
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentArchivedCropBinding
import com.android.farmist.databinding.FragmentExpensessIncomeTrackerBinding
import com.android.farmist.model.ExpensesIncomeTrackerResponse.Data
import com.android.farmist.model.ExpensesIncomeTrackerResponse.GetExpensesIncomeTracker
import com.android.farmist.model.archive.RemoveFromAchiv
import com.android.farmist.util.progressbars
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Archived_crop : Fragment() {
    private lateinit var binding: FragmentArchivedCropBinding
lateinit var cropId:String
    lateinit var adapterExpIncomeTracker: Adapter_Archived_Crop
    lateinit var preferences: SharedPreferences
    lateinit var userId: String
    lateinit var progressbarsDialog: progressbars

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        preferences = requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()
        progressbarsDialog = progressbars(requireActivity())
        progressbarsDialog.showDialog()
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_archived_crop,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        GetExpensesincomeTracker()

    }


    fun removeArchived(){

        val call:Call<RemoveFromAchiv> = Api_Controller().getInstacne().removeIt(cropId)
        call.enqueue(object :Callback<RemoveFromAchiv>{
            override fun onResponse(
                call: Call<RemoveFromAchiv>,
                response: Response<RemoveFromAchiv>
            ) {
               var respon = response.body().toString()
                Toast.makeText(requireActivity(), "$respon", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<RemoveFromAchiv>, t: Throwable) {
                Toast.makeText(requireActivity(), "$t", Toast.LENGTH_SHORT).show()
            }
        })


    }


    private fun GetExpensesincomeTracker() {
        adapterExpIncomeTracker =
            activity?.let { Adapter_Archived_Crop(it.applicationContext, ArrayList<Data>()) }!!
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
        return Api_Controller.apiInterface2.getArchiveCrop(userId)
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