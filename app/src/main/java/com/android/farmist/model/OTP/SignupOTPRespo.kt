package com.android.farmist.model.OTP

import com.google.gson.annotations.SerializedName

data class SignupOTPRespo(

    @SerializedName("Status"  ) var Status  : String? = null,
    @SerializedName("Details" ) var Details : String? = null

)
