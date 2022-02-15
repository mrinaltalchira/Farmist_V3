package com.android.farmist.model.FullExpenseLog.piechart

import com.google.gson.annotations.SerializedName

data class PieData(


    @SerializedName("userIncome"     ) var userIncome     : String? = null,
    @SerializedName("userSubsidy"    ) var userSubsidy    : String? = null,
    @SerializedName("userSeeds"      ) var userSeeds      : String? = null,
    @SerializedName("userTractor"    ) var userTractor    : String? = null,
    @SerializedName("userLabour"     ) var userLabour     : String? = null,
    @SerializedName("userFertilizer" ) var userFertilizer : String? = null

)

