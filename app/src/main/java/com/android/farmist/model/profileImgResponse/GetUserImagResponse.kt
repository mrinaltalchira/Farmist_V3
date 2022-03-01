package com.android.farmist.model.profileImgResponse

data class GetUserImagResponse(
    val latestPic: List<LatestPic>,
    val message: String,
    val name: String
    )