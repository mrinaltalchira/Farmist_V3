package com.android.farmist.fragments

import android.graphics.Color
import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.farmist.R
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentMarketPriceBinding
import com.android.farmist.model.MarketPriceResponse.marketPriceResponse
import com.android.farmist.model.cropDetailsFragment.CropName
import com.android.farmist.util.progressbars
import com.bumptech.glide.Glide
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

import java.util.*
import lecho.lib.hellocharts.view.LineChartView

import lecho.lib.hellocharts.gesture.ZoomType
import lecho.lib.hellocharts.model.*

import lecho.lib.hellocharts.util.ChartUtils
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.ColumnChartData

import lecho.lib.hellocharts.model.SubcolumnValue
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.view.ColumnChartView
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Month


class MarketPriceFragment : Fragment() {
    private lateinit var binding: FragmentMarketPriceBinding
    lateinit var chartTop: LineChartView
    private var lineData: LineChartData? = null

    private var chartBottom: ColumnChartView? = null
    private var columnData: ColumnChartData? = null
    lateinit var cropPriceId: String
    lateinit var progressbars: progressbars
//    lateinit var priceList:List<String>
//    lateinit var monthList:List<String>
//    lateinit var monthList:List<String>

    val months = arrayOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
        "Sep", "Oct", "Nov", "Dec"
    )
    val days = arrayOf("Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cropPriceId = arguments?.getString("cropId").toString()
        progressbars = com.android.farmist.util.progressbars(requireActivity())

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_market_price,
            container,
            false
        )
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressbars.showDialog()


        chartTop = LineChartView(requireActivity())
        chartBottom = ColumnChartView(requireActivity())
        chartTop = binding.chart
        getCropPriceData()

        generateInitialLineData()
//        generateColumnData()


    }

    private fun getCropPriceData() {

    }


    fun generateInitialLineData() {

        var call: Call<marketPriceResponse> =
            Api_Controller().getInstacne().getCropMarketPrices(cropPriceId)
        call.enqueue(object : Callback<marketPriceResponse> {
            override fun onResponse(
                call: Call<marketPriceResponse>,
                response: Response<marketPriceResponse>
            ) {
                var respo = response.body()
                if (respo != null) {
                    binding.tvCropNamePrice.setText(respo.crop.title)
                    binding.tvPricecropKg.setText("Rs. " + respo.crop.currentPrice + "/Kg")
                    Glide.with(activity?.applicationContext!!).load(respo.crop.image)
                        .into(binding.ivdp)
//                    priceList=respo.crop.price


                    val axisValues: MutableList<AxisValue> = ArrayList()
                    val values: MutableList<PointValue> = ArrayList()
                    for (i in 0 until respo.crop.month.size) {

                        values.add(PointValue(i.toFloat(), respo.crop.price[i].toFloat()))
                        axisValues.add(AxisValue(i.toFloat()).setLabel(respo.crop.month[i].toString()))
                    }
                    val line = Line(values)
                    val max = respo.crop.price.maxOrNull()?.toFloat() ?: 0
                    val Min = respo.crop.price.minOrNull()?.toFloat() ?: 0
                    line.setColor(ChartUtils.COLOR_GREEN).isFilled = true

                    val lines: MutableList<Line> = ArrayList()
                    lines.add(line)

                    lineData = LineChartData(lines)
                    lineData!!.axisXBottom = Axis(axisValues).setHasLines(true)
                    lineData!!.axisYLeft = Axis().setHasLines(true).setMaxLabelChars(3)


                    chartTop.lineChartData = lineData
                    chartTop.isViewportCalculationEnabled = false
                    val maxDiv=max.toFloat()/10

                    val v = Viewport(0F, max.toFloat()+maxDiv, respo.crop.month.size.toFloat(), 0F)
                    chartTop.maximumViewport = v
                    chartTop.currentViewport = v

                    chartTop.zoomType = ZoomType.HORIZONTAL_AND_VERTICAL
                    progressbars.hidediloag()
                } else {
                    progressbars.hidediloag()

                }
            }

            override fun onFailure(call: Call<marketPriceResponse>, t: Throwable) {
                progressbars.hidediloag()
                Toast.makeText(requireActivity(), "Error found :- $t", Toast.LENGTH_SHORT).show()
            }
        })


    }


}
