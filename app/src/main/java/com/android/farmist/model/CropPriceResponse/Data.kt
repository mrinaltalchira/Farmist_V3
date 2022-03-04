package com.android.farmist.model.CropPriceResponse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyCropPrice")
data class Data(

    @PrimaryKey(autoGenerate = true) var userId: Int? = null,
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "title") val title: String
)