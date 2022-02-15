package com.android.farmist.model.archive

data class UpdatedCrop(
    val __v: Int,
    val _id: String,
    val archieved: String,
    val areaType: String,
    val cloudinary_id: String,
    val expenses: List<Any>,
    val farmName: String,
    val harvest: String,
    val image: String,
    val incomes: List<Any>,
    val sowedArea: String,
    val sowedDate: String,
    val subsidies: List<Any>,
    val userId: String
)