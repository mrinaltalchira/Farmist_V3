package com.android.farmist.model.location

import com.google.gson.annotations.SerializedName


data class Report (

  @SerializedName("location" ) var location : Location? = Location(),
  @SerializedName("current"  ) var current  : Current?  = Current(),
  @SerializedName("forecast" ) var forecast : Forecast? = Forecast()

)