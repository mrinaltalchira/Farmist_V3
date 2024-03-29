package com.android.farmist.model.harvested

import com.google.gson.annotations.SerializedName


data class Data (
  @SerializedName("name"     ) var name     : String? = null,
  @SerializedName("image"     ) var image     : String? = null,
  @SerializedName("area"      ) var area      : String? = null,
  @SerializedName("areaType"  ) var areaType  : String? = null,
  @SerializedName("harvested" ) var harvested : String? = null,
  @SerializedName("cropId" ) var cropId : String? = null


)