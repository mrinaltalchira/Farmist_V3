package com.android.farmist.model.ExpensesIncomeTrackerResponse

data class GetExpensesData(
    val date: String,
    val expense: String,
    val totalFertilzer: String,
    val totalLabour: String,
    val totalSeed: String,
    val totalTractor: String
)