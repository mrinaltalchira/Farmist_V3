package com.android.farmist.model.CropPriceResponse

data class CropX(
    val __v: Int,
    val _id: String,
    val cloudinary_id: String,
    val fertilizerDay: String,
    val image: String,
    val month: List<Any>,
    val price: List<Any>,
    val title: String
)