package com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpensesTrackerResponse

import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("image"       ) var image       : String?           ,
    @SerializedName("cropName"    ) var cropName       : String?        ,
    @SerializedName("area"        ) var area        : String?           ,
    @SerializedName("areaType"    ) var areaType    : String?           ,
    @SerializedName("archieve"    ) var archieve    : String?           ,
    @SerializedName("userExpense" ) var userExpense : String?           ,
    @SerializedName("dateSowed"   ) var dateSowed   : String?           ,
    @SerializedName("subName"     ) var subName     : ArrayList<String> ,
    @SerializedName("subAmount"   ) var subAmount   : ArrayList<String> ,
    @SerializedName("subDate"     ) var subDate     : ArrayList<String>,
    @SerializedName("userIncome"  ) var userIncome  : String?          ,
    @SerializedName("incomeDate"  ) var incomeDate  : ArrayList<String>,
    @SerializedName("incomeList"  ) var incomeList  : ArrayList<String>,
    @SerializedName("userProfit"  ) var userProfit  : String,
    @SerializedName("userLoss"    ) var userLoss    : String





)