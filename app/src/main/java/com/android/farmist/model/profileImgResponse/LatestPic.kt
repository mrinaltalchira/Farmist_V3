package com.android.farmist.model.profileImgResponse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user Account Info")
data class LatestPic(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") val idcrop: Int=12,
    @ColumnInfo(name = "image")  val image: String,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "name") val name: String=" "
)