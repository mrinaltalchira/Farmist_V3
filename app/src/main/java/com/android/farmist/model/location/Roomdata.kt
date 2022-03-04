package com.android.farmist.model.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather")
data class Roomdata(

    @PrimaryKey(autoGenerate = true) var num : Int = 1,
    @ColumnInfo(name = "icon")var icon:String,
    @ColumnInfo(name = "icon1")var icon1:String,
    @ColumnInfo(name = "icon2")var icon2:String,
    @ColumnInfo(name = "icon3")var icon3:String,
    @ColumnInfo(name = "date")var date:String,
    @ColumnInfo(name = "date1")var date1:String,
    @ColumnInfo(name = "date2")var date2:String,
    @ColumnInfo(name = "tempC")var tempC:String,
    @ColumnInfo(name = "tempC1")var tempC1:String,
    @ColumnInfo(name = "tempC2")var tempC2:String,
    @ColumnInfo(name = "tempC3")var tempC3:String

)
