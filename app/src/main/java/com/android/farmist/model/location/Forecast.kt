package com.android.farmist.model.location

import com.google.gson.annotations.SerializedName


data class Forecast (

  @SerializedName("forecastday" ) var forecastday : ArrayList<Forecastday> = arrayListOf()

)