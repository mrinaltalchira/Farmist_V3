package com.android.farmist.model.ExpensesIncomeTrackerResponse

data class Expense(
    val __v: Int,
    val _id: String,
    val amount: String,
    val cropId: String,
    val date: String,
    val expenseType: String,
    val userId: String
)