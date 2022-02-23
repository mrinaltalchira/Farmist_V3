package com.android.farmist.model.search

import com.google.gson.annotations.SerializedName

data class ResponceSearch(

    @SerializedName("fruits" ) var fruits : ArrayList<Search> = arrayListOf(),
    @SerializedName("vegs"   ) var vegs   : ArrayList<String> = arrayListOf()

)
