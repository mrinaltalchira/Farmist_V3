package com.android.farmist.model.getFarm

import com.google.gson.annotations.SerializedName

data class farmsccrop(
    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("image"  ) var image  : String? = null,
    @SerializedName("cropId" ) var cropId : String? = null
)
