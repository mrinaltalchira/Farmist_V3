package com.android.farmist.model.location

import com.google.gson.annotations.SerializedName


data class Current (

  @SerializedName("last_updated_epoch" ) var lastUpdatedEpoch : Int?       = null,
  @SerializedName("last_updated"       ) var lastUpdated      : String?    = null,
  @SerializedName("temp_c"             ) var tempC            : Int?       = null,
  @SerializedName("temp_f"             ) var tempF            : Double?    = null,
  @SerializedName("is_day"             ) var isDay            : Int?       = null,
  @SerializedName("condition"          ) var condition        : Condition? = Condition()

)