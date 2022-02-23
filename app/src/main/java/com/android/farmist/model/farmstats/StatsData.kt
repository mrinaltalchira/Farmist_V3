package com.android.farmist.model.farmstats

import com.google.gson.annotations.SerializedName

data class StatsData(


    @SerializedName("numOfCrops"     ) var numOfCrops     : String? = null,
    @SerializedName("numOfLand"      ) var numOfLand      : String? = null,
    @SerializedName("areaSowed"      ) var areaSowed      : String? = null,
    @SerializedName("varities"       ) var varities       : String? = null,
    @SerializedName("numOfHarvested" ) var numOfHarvested : String? = null,
    @SerializedName("expenseTotal"   ) var expenseTotal   : String? = null,
    @SerializedName("quantityTotal"  ) var quantityTotal  : String? = null,
    @SerializedName("incomeTotal"    ) var incomeTotal    : String? = null,
    @SerializedName("areaType"       ) var areaType       : ArrayList<String> = arrayListOf()


)
