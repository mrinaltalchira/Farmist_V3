package com.android.farmist.model.FullExpenseLog

data class
Data(
    var image: String,
    var area: String,
    var areaType: String,
    var name: String,
    var expenseTotal: String,
    var totalSeed: String,
    var totalFertilzer: String,
    var totalTractor: String,
    var totalLabour: String,
    var ferDate: List<String>,
    var tractorDate: List<String>,
    var seedDate: List<String>,
    var labourDate: List<String>,
    var subsidyTotal: String,
    var subsidyDate:List<String>,
    var incomeTotal: String,
    var incomeDate: List<String>
)
