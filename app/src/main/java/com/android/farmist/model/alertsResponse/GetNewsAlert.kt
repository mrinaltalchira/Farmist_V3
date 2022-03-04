package com.android.farmist.model.alertsResponse

data class GetNewsAlert(
    val message: String,
    val news: List<New>
)