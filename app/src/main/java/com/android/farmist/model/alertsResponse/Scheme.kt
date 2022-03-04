package com.android.farmist.model.alertsResponse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GovScheme")
data class Scheme(
    @PrimaryKey(autoGenerate = true) var no:Int = 1,
  //  @ColumnInfo(name = "__v") val __v: Int,
    //@ColumnInfo(name = "_id") val _id: String,
    //@ColumnInfo(name = "cloudinary_id") val cloudinary_id: String,
    @ColumnInfo(name = "createdAt") val createdAt: String,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "title") val title: String
)