package com.android.farmist.model.upcommingAction

import com.google.gson.annotations.SerializedName

data class UpcomingDates(
    @SerializedName("fertilizeDates"  ) var fertilizeDates  : ArrayList<String>     = arrayListOf(),
    @SerializedName("harvestingDates" ) var harvestingDates : ArrayList<String>     = arrayListOf(),
    @SerializedName("userCrops1"      ) var userCrops1      : ArrayList<UserCrops1> = arrayListOf()

)
