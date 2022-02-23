package com.android.farmist.model.MarketPriceResponse

data class Crop(
    val __v: Int,
    val _id: String,
    val cloudinary_id: String,
    val currentMonth: String,
    val currentPrice: String,
    val image: String,
    val month: List<String>,
    val price: List<String>,
    val title: String
)