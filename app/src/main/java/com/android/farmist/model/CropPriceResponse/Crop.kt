package com.android.farmist.model.CropPriceResponse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CropPrice")
data class Crop(
    @PrimaryKey (autoGenerate = true)@ColumnInfo(name = "idcrop") val idcrop: Int=12,
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "currentPrice") val currentPrice: String,
    @ColumnInfo(name = "title") val title: String
)