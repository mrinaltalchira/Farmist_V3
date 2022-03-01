package com.android.farmist.model.getSowedCrop

data class ProgressTracker(


    var cropId: String? = null,
    var name: String? = null,
    var image: String? = null,
    var area: String? = null,
    var areaType: String? = null,
    var sowedDate: String? = null,
    var totalExpense: String? = null,
    var fertilizeAt: String? = null,
    var harvestAt: String? = null,
    var harvestDays: String? = null,
    var fertilizeDays: String? = null


)