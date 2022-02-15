package com.android.farmist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.farmist.adapter.Adapter_Market_Crop_Prices
import com.android.farmist.adapter.Adapter_Other_Market_Crop_Prices
import com.android.farmist.adapter.Adapter_Select_category
import com.android.farmist.adapter.Adapter_Select_categoryVeg
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentCropsBinding
import com.android.farmist.databinding.FragmentPricesBinding
import com.android.farmist.databinding.FragmentSelectCropCategoryBinding
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.CropPriceResponse.getCropPrice
import com.android.farmist.model.selectCategoryResponse.Fruitlist
import com.android.farmist.model.selectCategoryResponse.GetFruitsList
import com.android.farmist.model.selectCategoryResponse.GetVagList
import com.android.farmist.model.selectCategoryResponse.Veg
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SelectCropCategory : Fragment(),Adapter_Select_category.SelectItem,Adapter_Select_categoryVeg.SelectItemVeg {
    lateinit var adapterMarketCropPrices: Adapter_Market_Crop_Prices
    lateinit var adapterSelectCategory: Adapter_Select_category
    lateinit var adapterSelectCategoryVeg: Adapter_Select_categoryVeg
    private var createGroupList: ArrayList<String> = ArrayList()
    private var otherGroupList: ArrayList<String> = ArrayList()
     var datachecker:String=""


    private lateinit var binding : FragmentSelectCropCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_select_crop_category, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getfruitCrop()
        getVegCrop()
        binding.nextBtnSelectCategory.setOnClickListener {


            if (datachecker.isBlank())
            {
                return@setOnClickListener
            }
            var bundle = bundleOf(
                "cropName" to datachecker
            )
            Toast.makeText(activity?.applicationContext, "$datachecker ", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_selectCropCategory_to_activity_Crop_Details_Fragment,bundle)


        }


    }



    private fun getfruitCrop() {
        adapterSelectCategory=activity?.let {
            Adapter_Select_category( ArrayList<Fruitlist>(),this,requireContext(),datachecker)
        }!!
        binding.fruitsRv.setHasFixedSize(true)
        binding.fruitsRv.layoutManager=
            GridLayoutManager(activity?.applicationContext,4)

        binding.fruitsRv.adapter=adapterSelectCategory
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> getObserver(response as GetFruitsList) },
                    { t -> onFailure(t) })
        )

    }

    private fun getObservable(): Observable<GetFruitsList> {
        return Api_Controller.apiInterface.getcategoryFriuts()
    }

    private fun getObserver(priceData: GetFruitsList) {
        if (priceData != null) {
            val CropPriceList = priceData.fruitlist

            adapterSelectCategory.setList(CropPriceList)
        }
//        Toast.makeText(activity?.applicationContext, "data${priceData.toString()}", Toast.LENGTH_SHORT).show()


    }

    private fun onFailure(t: Throwable) {
        Log.d("Main", "OnFailure: " + t.message)
    }



    //get veg list
    private fun getVegCrop() {

        adapterSelectCategoryVeg=activity?.let {
            Adapter_Select_categoryVeg(ArrayList<Veg>(),this,requireContext() )
        }!!
        binding.vegetableRv.setHasFixedSize(true)
        binding.vegetableRv.layoutManager=
            GridLayoutManager(activity?.applicationContext,4)

        binding.vegetableRv.adapter=adapterSelectCategoryVeg
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            getObservableVeg().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> getObserverVeg(response as GetVagList) },
                    { t -> onFailureVeg(t) })
        )

    }

    private fun getObservableVeg(): Observable<GetVagList> {
        return Api_Controller.apiInterface.getcategoryVeg()
    }

    private fun getObserverVeg(priceData: GetVagList) {
        if (priceData != null) {
            val CropPriceList = priceData.vegs

            adapterSelectCategoryVeg.setList(CropPriceList)
        }


    }

    private fun onFailureVeg(t: Throwable) {
        Log.d("Main", "OnFailure: " + t.message)
    }

    override fun onItemSelect(viewModel: Fruitlist) {
//        Toast.makeText(requireActivity(), "${viewModel.name}", Toast.LENGTH_SHORT).show()
        datachecker=viewModel.name
        adapterSelectCategoryVeg.notifyDataSetChanged()
//        adapterSelectCategory.notifyDataSetChanged()

    }

    override fun onItemSelect(viewModel: Veg) {
//        Toast.makeText(requireActivity(), "Veg"+viewModel.name, Toast.LENGTH_SHORT).show()
        adapterSelectCategory.notifyDataSetChanged()
//        adapterSelectCategoryVeg.notifyDataSetChanged()
        datachecker=viewModel.name
    }

}