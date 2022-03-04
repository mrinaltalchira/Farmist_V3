package com.android.farmist.fragments

import android.app.Activity
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
import androidx.recyclerview.widget.GridLayoutManager
import com.android.farmist.R
import com.android.farmist.RoomDatabase.appDatabase
import com.android.farmist.adapter.Adapter_Market_Crop_Prices
import com.android.farmist.adapter.Adapter_Other_Market_Crop_Prices
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentPricesBinding
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.CropPriceResponse.GetMyCropPrices
import com.android.farmist.model.CropPriceResponse.getCropPrice
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Prices_Fragment : Fragment() {


    private lateinit var binding: FragmentPricesBinding

    lateinit var adapterOtherMarketCropPrices : Adapter_Other_Market_Crop_Prices
    private var createGroupList: ArrayList<String> = ArrayList()
    private var otherGroupList: ArrayList<String> = ArrayList()
    lateinit var appDatabaseObj: appDatabase
    lateinit var userId: String
    lateinit var preferences: SharedPreferences
    val adapterMarketCropPrices=Adapter_Market_Crop_Prices()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()
        appDatabaseObj = appDatabase.getAppDBInstance(requireContext())
        val list2 = appDatabaseObj.getAppDao().gelAllPrice()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if (it != null){
                    adapterOtherMarketCropPrices.setList(it)
                    binding.rvothermarketcropprice.adapter = adapterOtherMarketCropPrices
                    adapterOtherMarketCropPrices.notifyDataSetChanged()
                }

            })

        val list1 = appDatabaseObj.getAppDao().getAllMyPrice()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if (it != null){
                    adapterMarketCropPrices.setList(it,requireActivity())
                    binding.rvmarketcropprice.adapter = adapterMarketCropPrices
                    adapterMarketCropPrices.notifyDataSetChanged()
                }

            })


        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_prices, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPriceCrop()
        getMyCrop()
    }

    private fun getMyCrop() {
        var call: Call<GetMyCropPrices> = Api_Controller().getInstacne().getMycropPrices(userId)
        call.enqueue(object : Callback<GetMyCropPrices> {
            override fun onResponse(call: Call<GetMyCropPrices>, response: Response<GetMyCropPrices>) {
                var respo = response.body()
                if (respo != null) {
                    val cropList=respo.data


                    binding.rvmarketcropprice.setHasFixedSize(true)
                    binding.rvmarketcropprice.adapter=adapterMarketCropPrices
                    val activity: Activity? = activity
                    if (activity != null) {

                        appDatabaseObj.getAppDao().deleteAllmyPrice()
                        appDatabaseObj.getAppDao().insertMyPrice(cropList)
                    adapterMarketCropPrices.setList(cropList,requireActivity())
                    }

                }
            }

            override fun onFailure(call: Call<GetMyCropPrices>, t: Throwable) {
                Toast.makeText(requireActivity(), "Error found :- $t", Toast.LENGTH_SHORT).show()
            }
        })

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

             appDatabaseObj.getAppDao().deleteAllPrice()
            appDatabaseObj.getAppDao().insertPrice(CropPriceList)

         adapterOtherMarketCropPrices.setList(CropPriceList)

        }


    }

    private fun onFailure(t: Throwable) {
        Log.d("Main", "OnFailure: " + t.message)
    }
}