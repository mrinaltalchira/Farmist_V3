package com.android.farmist.model.getSowedCrop

data class UserCrop(
    val __v: Int,
    val _id: String,
    val areaType: String,
    val cloudinary_id: String,
    val farmName: String,
    val cropName :String,
    val image: String,
    val sowedArea: String,
    val sowedDate: String,
    val userId: String
)