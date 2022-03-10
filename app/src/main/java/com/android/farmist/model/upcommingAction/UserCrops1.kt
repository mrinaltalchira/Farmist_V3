package com.android.farmist.model.upcommingAction

import com.google.gson.annotations.SerializedName

data class UserCrops1 (

    @SerializedName("name"          ) var name          : String? = null,
    @SerializedName("image"         ) var image         : String? = null,
    @SerializedName("cropId"        ) var cropId        : String? = null,
    @SerializedName("sowedDate"     ) var sowedDate     : String? = null,
    @SerializedName("harvestDate"   ) var harvestDate   : String? = null,
    @SerializedName("fertilizeDate" ) var fertilizeDate : String? = null
        )