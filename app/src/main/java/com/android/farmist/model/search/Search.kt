package com.android.farmist.model.search

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("_id"           ) var Id            : String? = null,
    @SerializedName("image"         ) var image         : String? = null,
    @SerializedName("cloudinary_id" ) var cloudinaryId  : String? = null,
    @SerializedName("name"          ) var name          : String? = null,
    @SerializedName("fertilizerDay" ) var fertilizerDay : String? = null,
    @SerializedName("harvestingDay" ) var harvestingDay : String? = null,
    @SerializedName("__v"           ) var _v            : Int?    = null
)
