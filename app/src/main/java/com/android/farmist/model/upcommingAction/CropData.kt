package com.android.farmist.model.upcommingAction

import com.google.gson.annotations.SerializedName

data class CropData(
    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("image"  ) var image  : String? = null,
    @SerializedName("cropId" ) var cropId : String? = null

)
