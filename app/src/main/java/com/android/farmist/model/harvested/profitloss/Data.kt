package com.android.farmist.model.harvested.profitloss

data class Data(


    val cropName: String,
    val dateSowed: String,
    val image: String,
    val incomeDate: List<String>,
    val incomeList: List<String>,
    val subAmount: List<String>,
    val subDate: List<String>,
    val subName: List<String>,
    val userExpense: String,
    val userIncome: String,
    val userLoss: String,
    val userProfit: String
)
