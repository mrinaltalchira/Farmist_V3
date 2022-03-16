package com.android.farmist.model

import com.google.gson.annotations.SerializedName

data class ExistOrNot(
    @SerializedName("message" ) var message : String? = null
)