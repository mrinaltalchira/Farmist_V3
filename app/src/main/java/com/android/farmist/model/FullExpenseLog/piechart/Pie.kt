package com.android.farmist.model.FullExpenseLog

import com.android.farmist.model.FullExpenseLog.piechart.PieData
import com.google.gson.annotations.SerializedName


data class Pie (

  @SerializedName("data" ) var data : PieData

)