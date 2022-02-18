package com.android.farmist.model.addCropResponse

import com.google.gson.annotations.SerializedName

data class Crop(


    @SerializedName("userId"        ) var userId       : String?           = null,
    @SerializedName("cropName"      ) var cropName     : String?           = null,
    @SerializedName("image"         ) var image        : String?           = null,
    @SerializedName("cloudinary_id" ) var cloudinaryId : String?           = null,
    @SerializedName("farmName"      ) var farmName     : String?           = null,
    @SerializedName("sowedArea"     ) var sowedArea    : String?           = null,
    @SerializedName("areaType"      ) var areaType     : String?           = null,
    @SerializedName("sowedDate"     ) var sowedDate    : String?           = null,
    @SerializedName("expenses"      ) var expenses     : ArrayList<String> = arrayListOf(),
    @SerializedName("subsidies"     ) var subsidies    : ArrayList<String> = arrayListOf(),
    @SerializedName("incomes"       ) var incomes      : ArrayList<String> = arrayListOf(),
    @SerializedName("harvest"       ) var harvest      : String?           = null,
    @SerializedName("archieved"     ) var archieved    : String?           = null,
    @SerializedName("_id"           ) var Id           : String?           = null,
    @SerializedName("__v"           ) var _v           : Int?              = null


)