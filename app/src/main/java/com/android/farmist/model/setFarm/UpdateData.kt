package com.android.farmist.model.setFarm

import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class UpdateData(

var name:String,
var area:String,
var areaType:String,
var tehsil:String,
var surveyNum:String,
var image:String


)

