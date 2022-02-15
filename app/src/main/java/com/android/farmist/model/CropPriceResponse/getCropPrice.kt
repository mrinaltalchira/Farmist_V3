package com.android.farmist.model.CropPriceResponse

data class getCropPrice(
    val crops: List<Crop>,
    val message: String
)