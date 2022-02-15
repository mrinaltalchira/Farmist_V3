package com.android.farmist.model.alertsResponse

import androidx.room.Entity


data class GetNewsAlert(
    val message: String,
    val news: List<New>
)