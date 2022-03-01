package com.android.farmist.model.alertsResponse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News")
data class New(
    @PrimaryKey (autoGenerate = true)@ColumnInfo(name = "id") val idcrop: Int=12,
//    @ColumnInfo(name = "cloud_id")val cloudinary_id: String,
    @ColumnInfo(name = "date")  val createdAt: String,
    @ColumnInfo(name = "description")  val desc: String,
    @ColumnInfo(name = "image")  val image: String,
    @ColumnInfo(name = "name") val title: String
)