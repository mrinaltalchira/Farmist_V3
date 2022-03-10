package com.android.farmist

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.android.farmist.RoomDatabase.NewsDao
import com.android.farmist.RoomDatabase.appDatabase
import com.android.farmist.adapter.Adapter_Crop_Prices
import com.android.farmist.adapter.Adapter_Crop_Prices_singleRow
import com.android.farmist.adapter.Adapter_Other_Market_Crop_Prices
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.CropPricesBinding
import com.android.farmist.databinding.ExpenseTrackerBinding
import com.android.farmist.databinding.FragmentCropInfoBinding
import com.android.farmist.databinding.FragmentCropPricesBinding
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.CropPriceResponse.getCropPrice
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class cropPrices : Fragment() {

    private lateinit var binding : FragmentCropPricesBinding
    lateinit var adapterCropPricesSinglerow: Adapter_Crop_Prices_singleRow
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_crop_prices,container,false)
        getPriceCrop()
        return binding.root
    }



    private fun getPriceCrop() {

        adapterCropPricesSinglerow=activity?.let {
            Adapter_Crop_Prices_singleRow(it.applicationContext, ArrayList<Crop>())
        }!!
        binding.rvothermarketcropprice.setHasFixedSize(true)
        binding.rvothermarketcropprice.layoutManager=
            GridLayoutManager(activity?.applicationContext,2)

        binding.rvothermarketcropprice.adapter=adapterCropPricesSinglerow
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> getObserver(response as getCropPrice) },
                    { t -> onFailure(t) })
        )
    }

    private fun getObservable(): Observable<getCropPrice> {
        return Api_Controller.apiInterface.getCropPrice()
    }

    private fun getObserver(priceData: getCropPrice) {
        if (priceData != null) {
            val CropPriceList = priceData.crops

            adapterCropPricesSinglerow.setList(CropPriceList)
        }


    }

    private fun onFailure(t: Throwable) {
        Log.d("Main", "OnFailure: " + t.message)
    }


}