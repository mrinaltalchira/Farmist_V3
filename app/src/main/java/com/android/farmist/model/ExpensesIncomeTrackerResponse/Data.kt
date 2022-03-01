package com.android.farmist.model.ExpensesIncomeTrackerResponse

data class Data(
    val area: String,
    val areaType: String,
    var userExpense:String,
    val cropId: String,
    val date: String,
    val image: String,
    val name: String
)