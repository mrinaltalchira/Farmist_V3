package com.android.farmist.fragments

import android.graphics.Color
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.api.ApiInterface
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.*
import com.android.farmist.model.FullExpenseLog.Data
import com.android.farmist.model.FullExpenseLog.Pie
import com.android.farmist.model.FullExpenseLog.Root
import com.android.farmist.model.FullExpenseLog.piechart.PieData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Full_Expenses_Log_Fragment : Fragment() {

    private lateinit var binding: FullExpenseLogBinding
    val pieData: MutableList<SliceValue> = ArrayList()
    var cropid = "620496f5f76f5aab9e18431d"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.full_expense_log, container, false)

        return binding.root
    }

    private fun setData() {

        val call: Call<Root> =
            Api_Controller().getInstacne().fullExpenceLogData(cropid)
        call.enqueue(object : Callback<Root> {
            override fun onResponse(call: Call<Root>, response: Response<Root>) {

                var respo = response.body()

                if (respo != null) {
                    Glide.with(requireActivity()).load(respo.data.image).into(binding.img)
                    binding.tvExpLogCropName.setText(response.body()?.data?.name.toString())
                    binding.tvExpLogArea.setText(response.body()?.data?.area.toString())
                    binding.tvExpLogAreatype.setText(response.body()?.data?.areaType.toString())
                    binding.tvExpLogDateSeeds.setText(response.body()?.data?.seedDate.toString())
                    binding.tvExpLogPriceSeeds.setText(response.body()?.data?.totalSeed.toString())
                    binding.tvExpLogDateFertilizer.setText(response.body()?.data?.ferDate.toString())
                    binding.tvExpLogPriceFerti.setText(response.body()?.data?.totalFertilzer.toString())
                    binding.tvExpLogDateLabout.setText(response.body()?.data?.labourDate.toString())
                    binding.tvExpLogPriceLabour.setText(response.body()?.data?.totalLabour.toString())
                    binding.tvExpLogDateTractor.setText(response.body()?.data?.tractorDate.toString())
                    binding.tvExpLogPriceTractor.setText(response.body()?.data?.totalTractor.toString())
//                    binding.tvExpLogDateSubsidy.setText(response.body()?.datesubsidy.toString())
                    binding.tvExpLogPricesubsidy.setText(response.body()?.data?.subsidyTotal.toString())
                    binding.tvExpLogDateIncome.setText(
                        response.body()?.data?.incomeDate.toString().toString()
                    )
                    binding.tvExpLogPriceIncome.setText(response.body()?.data?.incomeTotal.toString())
                    binding.tvExpLogTotalPrice.setText(response.body()?.data?.expenseTotal.toString())


                } else {
                    Toast.makeText(requireActivity(), "null", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Root>, t: Throwable) {
                Toast.makeText(requireActivity(), "error fetching data $t", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        getPieData()
    }

    fun getPieData() {


        var call: Call<Pie> = Api_Controller().getInstacne().getPie(cropid)
        call.enqueue(object : Callback<Pie> {
            override fun onResponse(call: Call<Pie>, response: Response<Pie>) {
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
                Toast.makeText(requireActivity(), "error :- $t", Toast.LENGTH_SHORT).show()
            }
        })

    }


}
