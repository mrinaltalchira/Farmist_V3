package com.android.farmist.fragments

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentCropInfoBinding
import com.android.farmist.model.FullExpenseLog.Pie
import com.android.farmist.model.cropDetailsFragment.CropName
import com.android.farmist.model.getSowedCrop.ProgressTracker
import com.android.farmist.model.harvested.MakeHarvest
import com.android.farmist.model.setFarm.DeleteFarmRespo
import com.android.farmist.util.progressbars
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.customedialoug.view.*
import kotlinx.android.synthetic.main.fragment_crop_info_.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Crop_info_Fragment : Fragment() {
    lateinit var cropId: String
    val pieData: MutableList<SliceValue> = ArrayList()
    lateinit var binding: FragmentCropInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_crop_info_, container, false)

        binding.backbtn.setOnClickListener {
            findNavController().navigate(R.id.action_crop_info_Fragment_to_crops_Fragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cropId = arguments?.getString("cropId").toString()
        getDetails()
        setChart()
        binding.tvViewFullLog.setOnClickListener {
            findNavController().navigate(
                R.id.action_crop_info_Fragment_to_full_Expenses_Log_Fragment,
                bundleOf("cropId" to cropId)
            )
        }
        binding.tvDelete.setOnClickListener { deleteCrop() }
        binding.tvHarvest.setOnClickListener { makeHarvested() }
        progressBar()
        tv_viewmoreOne.setOnClickListener {

            if (expandable.isExpanded) {
                expandable.collapse()
            } else {
                binding.expandable.expand()
            }

        }
    }


    fun progressBar() {

        val call: Call<ProgressTracker> = Api_Controller.apiInterface.getProgress(cropId)
        call.enqueue(object : Callback<ProgressTracker> {
            override fun onResponse(
                call: Call<ProgressTracker>,
                response: Response<ProgressTracker>
            ) {
                var respo = response.body()
                if (respo != null) {
                    binding.sowedCorpDate.setText("Sowed \n" + respo?.sowedDate.toString())


                    if (respo.fertilizeAt != "") {

                        val animation =
                            ObjectAnimator.ofInt(progressbarOne, "progress", 1000)
                        animation.duration = 32500
                        animation.interpolator = DecelerateInterpolator()
                        animation.start()
                        binding.addFertilizerData.setText("Add fertilizer \n" + respo.fertilizeAt.toString())


                        if (respo.harvestAt != "") {
                            val animation =
                                ObjectAnimator.ofInt(progressbarTwo, "progress", 1000)
                            animation.duration = 67500
                            animation.interpolator = DecelerateInterpolator()
                            animation.start()
                            binding.harvestCropDate.setText("Harvest  \n" + respo.harvestAt.toString())

                        }

                    }


                }

            }

            override fun onFailure(call: Call<ProgressTracker>, t: Throwable) {
                Toast.makeText(
                    requireActivity(),
                    "found progressBar error:- $t",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    fun makeHarvested() {


        val view = View.inflate(context, R.layout.customedial2, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.color.transe)

        view.yes.setOnClickListener {

            var progress = progressbars(requireActivity())
            dialog.dismiss()
            cropId = arguments?.getString("cropId").toString()
            progress.showDialog()

            var call: Call<MakeHarvest> = Api_Controller().getInstacne().makeHarvested(cropId)
            call.enqueue(object : Callback<MakeHarvest> {
                override fun onResponse(call: Call<MakeHarvest>, response: Response<MakeHarvest>) {
                    var respo = response.body()
                    if (respo != null) {
                        Toast.makeText(
                            requireActivity(),
                            "Crop Marked as Harvested",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_crop_info_Fragment_to_crops_Fragment2)
                        progress.hidediloag()

                    }
                }

                override fun onFailure(call: Call<MakeHarvest>, t: Throwable) {
                    Toast.makeText(requireContext(), "error found:- $t", Toast.LENGTH_SHORT).show()
                }
            })


        }
        view.no.setOnClickListener {

            dialog.dismiss()

            Toast.makeText(requireActivity(), "cancel request", Toast.LENGTH_SHORT).show()
        }


    }

    fun deleteCrop() {


        val view = View.inflate(context, R.layout.customedialoug, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.color.transe)

        view.yes.setOnClickListener {

            var progress = progressbars(requireActivity())
            dialog.dismiss()
            cropId = arguments?.getString("cropId").toString()
            progress.showDialog()
            Api_Controller().getInstacne().deleteCropExpence(cropId)
                .enqueue(object : Callback<DeleteFarmRespo> {
                    override fun onResponse(
                        call: Call<DeleteFarmRespo>,
                        response: Response<DeleteFarmRespo>
                    ) {
                        var respon = response.body()
                        if (respon != null) {
                            progress.hidediloag()
                            findNavController().navigate(R.id.action_crop_info_Fragment_to_crops_Fragment)


                        }
                    }

                    override fun onFailure(
                        call: Call<DeleteFarmRespo>,
                        t: Throwable
                    ) {
                        Toast.makeText(context, "not success $t", Toast.LENGTH_SHORT).show()
                        progress.hidediloag()
                        activity?.finish()
                    }
                })


        }
        view.no.setOnClickListener {

            dialog.dismiss()

            Toast.makeText(requireActivity(), "cancel request", Toast.LENGTH_SHORT).show()
        }

    }

    fun setChart() {

        var callpie: Call<Pie> = Api_Controller().getInstacne().getPie(cropId)
        callpie.enqueue(object : Callback<Pie> {
            override fun onResponse(call: Call<Pie>, response: Response<Pie>) {
                //pieData = response.body().toString()
                if (pieData.isEmpty()) {

                    Log.d("btao", "${response.body().toString()}")

                    pieData.add(
                        SliceValue(
                            response.body()!!.data.userSeeds!!.toFloat(),
                            Color.BLUE
                        ).setLabel(response.body()!!.data.userSeeds.toString() + " %")
                    )
                    pieData.add(
                        SliceValue(
                            response.body()!!.data.userFertilizer!!.toFloat(),
                            Color.GRAY
                        ).setLabel(response.body()!!.data.userFertilizer.toString() + " %")
                    )
                    pieData.add(
                        SliceValue(
                            response.body()!!.data.userLabour!!.toFloat(),
                            Color.RED
                        ).setLabel(response.body()!!.data.userLabour.toString() + " %")
                    )
                    pieData.add(
                        SliceValue(
                            response.body()!!.data.userTractor!!.toFloat(),
                            Color.CYAN
                        ).setLabel(response.body()!!.data.userTractor.toString() + " %")
                    )
                    pieData.add(
                        SliceValue(
                            response.body()!!.data.userIncome!!.toFloat(),
                            Color.YELLOW
                        ).setLabel(response.body()!!.data.userIncome.toString() + " %")
                    )
                    pieData.add(
                        SliceValue(
                            response.body()!!.data.userSubsidy!!.toFloat(),
                            Color.MAGENTA
                        ).setLabel(
                            response.body()!!.data.userSubsidy.toString()
                                    + " %"
                        )
                    )
                }

                binding.llgraphseeds.setBackgroundColor(pieData[0].color)
                binding.llgraphFertilizer.setBackgroundColor(pieData[1].color)
                binding.llgraphLabour.setBackgroundColor(pieData[2].color)
                binding.llgraphTractor.setBackgroundColor(pieData[3].color)
                binding.llgraphIncome.setBackgroundColor(pieData[4].color)
                binding.llgraphSubsidy.setBackgroundColor(pieData[5].color)

                val pieChartData = PieChartData(pieData)
                pieChartData.setHasLabels(true).valueLabelTextSize = 7
                binding.pieChart.setPieChartData(pieChartData)
            }

            override fun onFailure(call: Call<Pie>, t: Throwable) {
            }
        })

    }


    fun getDetails() {

        Toast.makeText(requireContext(), "$cropId", Toast.LENGTH_SHORT).show()

        var call: Call<CropName> = Api_Controller().getInstacne().getCropDetails(cropId)
        call.enqueue(object : Callback<CropName> {
            override fun onResponse(call: Call<CropName>, response: Response<CropName>) {
                var respo = response.body()
                if (respo != null) {
                    binding.tvCropName.setText(respo.name)
                    binding.tvArea.setText(respo.area)
                    binding.tvAreaType.setText(respo.areaType)
                    try {
                        Glide.with(requireActivity()).load(respo.image).into(binding.img)
                    } catch (e: Exception) {
                        Log.d("", "")
                    }
                    binding.tvTotalSum.setText(respo.totalExpense)
                }
            }

            override fun onFailure(call: Call<CropName>, t: Throwable) {
                Toast.makeText(requireActivity(), "Error found :- $t", Toast.LENGTH_SHORT).show()
            }
        })

    }


}


