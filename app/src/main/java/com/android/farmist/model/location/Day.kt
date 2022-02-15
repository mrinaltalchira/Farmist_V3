package com.android.farmist.model.location

import com.google.gson.annotations.SerializedName


data class Day (

  @SerializedName("condition"            ) var condition         : Condition? = Condition(),

)