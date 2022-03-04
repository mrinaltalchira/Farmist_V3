package com.android.farmist.model.upcommingAction

import com.google.gson.annotations.SerializedName

data class Upcomingrespo(


    @SerializedName("cropData" ) var cropData : ArrayList<CropData> = arrayListOf()
)
