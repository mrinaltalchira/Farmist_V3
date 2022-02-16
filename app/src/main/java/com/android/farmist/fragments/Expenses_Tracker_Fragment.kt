package com.android.farmist.fragments

import android.graphics.Color
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
import com.android.farmist.adapter.Adapter_Exp_Income_Tracker
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.ExpenseTrackerBinding
import com.android.farmist.databinding.FragmentHomeBinding
import com.android.farmist.databinding.LoansSubsidiesInfoBinding
import com.android.farmist.model.ExpensesIncomeTrackerResponse.Data
import com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpensesTrackerResponse.GetExpensesTracker
import com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpensesTrackerResponse.GetPiChartResponse
import com.android.farmist.model.ExpensesIncomeTrackerResponse.GetExpensesIncomeTracker
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.archive.SetArchiveResponse
import com.android.farmist.util.progressbars
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.model.PieChartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Expenses_Tracker_Fragment : Fragment() {

    private lateinit var binding : ExpenseTrackerBinding
    val pieData: MutableList<SliceValue> = ArrayList()
    lateinit var progressbarsDialog: progressbars
    lateinit var cropId:String
    lateinit var getExpensesTrackerList:com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpensesTrackerResponse.Data


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.expense_tracker,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressbarsDialog= progressbars(requireActivity())
        cropId = arguments?.getString("cropId").toString()
        Toast.makeText(requireContext(), "$cropId", Toast.LENGTH_SHORT).show()


        binding.tvviewfullog.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_expenses_Tracker_Fragment_to_full_Expenses_Log_Fragment)
        })

        binding.llArchived.setOnClickListener {
            val call: Call<SetArchiveResponse>
            call = Api_Controller().getInstacne().setArchieve(cropId)
            call.enqueue(object : Callback<SetArchiveResponse> {
                override fun onResponse(call: Call<SetArchiveResponse>, response: Response<SetArchiveResponse>) {
                    val responseList=response.body()?.updatedCrop?.archieved


                }

                override fun onFailure(call: Call<SetArchiveResponse>, t: Throwable) {
                    Log.d("getNewsError", t.toString())
                }
            })
        }



        GetExpensesTracker()
        GetpieChart()









    }

    private fun GetExpensesTracker() {


        val compositeDisposable= CompositeDisposable()
        compositeDisposable.add(getObservable().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ response->getObserver(response as GetExpensesTracker)},
                {t->onFailure(t)}
            ))


    }

    private fun getObservable(): Observable<GetExpensesTracker>
    {
        return Api_Controller.apiInterface2.getExpensesTracker(cropId)
    }

    private fun getObserver(getExpensesTracker: GetExpensesTracker)
    {
        if (getExpensesTracker!=null)
        {
            getExpensesTrackerList=getExpensesTracker.data


            binding.tvExpLogName.setText(getExpensesTrackerList.cropName)
//        binding.tvExpLogAcers.setText(getExpensesTrackerList.)
            binding.tvexpeses.setText(getExpensesTrackerList.userExpense)
            binding.Tvsowed.setText(getExpensesTrackerList.dateSowed)
            binding.tvProfit.setText("Rs."+getExpensesTrackerList.userProfit)
            binding.tvLoos.setText("Rs."+getExpensesTrackerList.userLoss)
            binding.tvIncome.setText(getExpensesTrackerList.userIncome)
            binding.TvincomeDate.setText(getExpensesTrackerList.incomeDate[0]+" :")
            binding.tvSubsidyName.setText(getExpensesTrackerList.subName[0])
            binding.tvSubsidyamount.setText(getExpensesTrackerList.subAmount[0])
            binding.tvSubsidydate.setText(getExpensesTrackerList.subDate[0])
            Glide.with(this).load(getExpensesTrackerList.image).into(binding.img)

            progressbarsDialog.hidediloag()
        }

    }
    private fun onFailure(t:Throwable)
    {
        progressbarsDialog.hidediloag()
        Log.d("Main", "OnFailure: "+ t.message)
    }

    //pi chart code
    private fun GetpieChart() {


        val compositeDisposable= CompositeDisposable()
        compositeDisposable.add(getObservableChart().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ response->getObserverChart(response as GetPiChartResponse)},
                {t->onFailureChart(t)}
            ))


    }

    private fun getObservableChart(): Observable<GetPiChartResponse>
    {
        return Api_Controller.apiInterface2.getchartData(cropId)
    }

    private fun getObserverChart(getPiChartResponse: GetPiChartResponse)
    {
        if (getPiChartResponse!=null)
        {
            val chartData=getPiChartResponse.data

            Toast.makeText(requireContext(), "$chartData", Toast.LENGTH_SHORT).show()

            if (pieData.isEmpty())
            {

            pieData.add(SliceValue(chartData.userSeeds.toFloat(), Color.BLUE).setLabel(chartData.userSeeds+" %"))
            pieData.add(SliceValue(chartData.userFertilizer.toFloat(), Color.GRAY).setLabel(chartData.userFertilizer+" %"))
            pieData.add(SliceValue(chartData.userLabour.toFloat(), Color.RED).setLabel(chartData.userLabour+" %"))
            pieData.add(SliceValue(chartData.userTractor.toFloat(), Color.CYAN).setLabel(chartData.userTractor+" %"))
            pieData.add(SliceValue(chartData.userIncome.toFloat(), Color.YELLOW).setLabel(chartData.userIncome+" %"))
            pieData.add(SliceValue(chartData.userSubsidy.toFloat(), Color.MAGENTA).setLabel(chartData.userSubsidy+" %"))
            }

            binding.llgraphseeds.setBackgroundColor(pieData[0].color)
            binding.llgraphFertilizer.setBackgroundColor(pieData[1].color)
            binding.llgraphLabour.setBackgroundColor(pieData[2].color)
            binding.llgraphTractor.setBackgroundColor(pieData[3].color)
            binding.llgraphIncome.setBackgroundColor(pieData[4].color)
            binding.llgraphSubsidy.setBackgroundColor(pieData[5].color)

            val pieChartData = PieChartData(pieData)
            pieChartData.setHasLabels(true).valueLabelTextSize = 10
            binding.Pichart.setPieChartData(pieChartData)
            progressbarsDialog.hidediloag()
        }

    }
    private fun onFailureChart(t:Throwable)
    {
        progressbarsDialog.hidediloag()
        Log.d("Main", "OnFailure: "+ t.message)
    }



}