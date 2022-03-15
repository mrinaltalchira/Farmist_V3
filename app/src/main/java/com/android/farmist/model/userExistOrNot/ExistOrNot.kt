package com.android.farmist.model.userExistOrNot

import com.google.gson.annotations.SerializedName

data class ExistOrNot(
    @SerializedName("message" ) var message : String? = null
)
