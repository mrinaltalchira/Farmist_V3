package com.android.farmist.model.CropPriceResponse

data class Crop(
    val __v: Int,
    val id: String,
    val cloudinary_id: String,
    val image: String,
    val currentPrice: String,
    val title: String
)