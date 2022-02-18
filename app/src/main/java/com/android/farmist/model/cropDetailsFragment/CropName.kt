package com.android.farmist.model.cropDetailsFragment

import com.google.gson.annotations.SerializedName


data class CropName (

    var cropId       : String? = null,
    var name         : String? = null,
    var image        : String? = null,
    var area         : String? = null,
    var areaType     : String? = null,
    var totalExpense : String? = null

)