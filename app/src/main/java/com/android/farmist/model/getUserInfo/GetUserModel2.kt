package com.android.farmist.model.getUserInfo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personalInfo")
data class getUserModel2(
//    val __v: Int,
//    val _id: String,
//    val chemicalCompany: String,
//    val createdAt: String,
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") val idcrop: Int=12,
    @ColumnInfo(name = "district") val district: String,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "numOfAcres")val numOfAcers: String,
    @ColumnInfo(name = "phone")val phone: String,
    @ColumnInfo(name = "pincode")val pincode: String,
//    val role: String,
//    val seedBrand: String,

    @ColumnInfo(name = "tehsil")val tehsil: String,
    @ColumnInfo(name = "village") val village: String
)