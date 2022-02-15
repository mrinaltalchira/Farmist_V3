package com.android.farmist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.farmist.R
import com.android.farmist.adapter.Adapter_Crop_Prices
import com.android.farmist.adapter.Adapter_Market_Crop_Prices
import com.android.farmist.adapter.Adapter_Other_Market_Crop_Prices
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentAlertsBinding
import com.android.farmist.databinding.FragmentPricesBinding
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.CropPriceResponse.getCropPrice
import com.android.farmist.model.alertsResponse.GetNewsAlert
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class Prices_Fragment : Fragment() {


    private lateinit var binding: FragmentPricesBinding
    lateinit var adapterMarketCropPrices: Adapter_Market_Crop_Prices
    lateinit var adapterOtherMarketCropPrices : Adapter_Other_Market_Crop_Prices
    private var createGroupList: ArrayList<String> = ArrayList()
    private var otherGroupList: ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_prices, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPriceCrop()

//        bindUIViews()
    }

    private fun getPriceCrop() {

        adapterOtherMarketCropPrices=activity?.let {
            Adapter_Other_Market_Crop_Prices(it.applicationContext, ArrayList<Crop>())
        }!!
        binding.rvothermarketcropprice.setHasFixedSize(true)
        binding.rvothermarketcropprice.layoutManager=GridLayoutManager(activity?.applicationContext,2)

        binding.rvothermarketcropprice.adapter=adapterOtherMarketCropPrices
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

            adapterOtherMarketCropPrices.setList(CropPriceList)
        }


    }

    private fun onFailure(t: Throwable) {
        Log.d("Main", "OnFailure: " + t.message)
    }

//    private fun bindUIViews() {
//        setData()
//        adapterMarketCropPrices.setList(createGroupList)
//        adapterOtherMarketCropPrices.setList(otherGroupList)
//
//        binding.rvmarketcropprice.adapter = adapterMarketCropPrices
//        binding.rvothermarketcropprice.adapter = adapterOtherMarketCropPrices
//
//
//    }

//    private fun setData(): ArrayList<String> {
//        for (i in 0 until 5) {
//
//            createGroupList.add("Group")
//            otherGroupList.add("Other")
//
//        }
//
//        return createGroupList
//        return otherGroupList
//
//    }


}