package com.android.farmist.model.alertsResponse

data class GetGovtScheme(
    val message: String,
    val schemes: List<Scheme>
)