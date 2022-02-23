package com.android.farmist.model.harvested

import com.google.gson.annotations.SerializedName

data class GetHarvestedCrop(


    @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()


)
