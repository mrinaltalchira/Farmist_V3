package com.android.farmist.model.location

import com.google.gson.annotations.SerializedName


data class Forecastday (

  @SerializedName("date"       ) var date      : String?         = null,
  @SerializedName("day"        ) var day       : Day?            = Day(),
  @SerializedName("hour"       ) var hour      : ArrayList<Hour> = arrayListOf()

)