package com.android.farmist.model.alertsResponse

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class New(
    val __v: Int,
    @PrimaryKey
    val _id: String,
    val cloudinary_id: String,
    val createdAt: String,
    val desc: String,
    val image: String,
    val title: String
)